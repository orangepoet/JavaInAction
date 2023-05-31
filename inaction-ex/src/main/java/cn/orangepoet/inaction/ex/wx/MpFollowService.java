package cn.orangepoet.inaction.ex.wx;

import cn.orangepoet.inaction.ex.wx.model.FollowClause;
import cn.orangepoet.inaction.ex.wx.model.MpFollow;
import cn.orangepoet.inaction.ex.wx.model.MpFollowBean;
import cn.orangepoet.inaction.ex.wx.model.MpFollowList;
import cn.orangepoet.inaction.ex.wx.model.MpTag;
import cn.orangepoet.inaction.ex.wx.model.PageQueryResult;
import cn.orangepoet.inaction.ex.wx.repository.MpFollowRepository;
import cn.orangepoet.inaction.ex.wx.utils.ObjectMapper;
import cn.orangepoet.inaction.ex.wx.utils.SyncUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.GetblacklistResult;
import weixin.popular.bean.user.TagsCreatResult;
import weixin.popular.bean.user.TagsGetResult;
import weixin.popular.bean.user.User;
import weixin.popular.bean.user.UserInfoList;
import weixin.popular.bean.user.UserTagGetResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * 包装微信公众号粉丝管理的API
 *
 * @author orange.cheng
 * @date 2023/2/16
 */
@Service
@Slf4j
public class MpFollowService {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private MpFollowRepository repository;
    @Autowired
    private SyncUtils syncUtils;

    private static final ExecutorService SYNC_EXECUTOR = Executors.newFixedThreadPool(2, Executors.defaultThreadFactory());

    /**
     * 全量同步粉丝用户
     *
     * @param appId 公众号ID
     */
    public void followSync(String appId) {
        try {
            boolean tryLock = syncUtils.lockAppSync(appId);
            if (!tryLock) {
                throw new MpFollowException("follow_sync_lock_app_failed", "follow_sync_lock_app_failed, appid: " + appId);
            }

            String authorizerToken = tokenUtils.getAuthorizerToken(appId);

            SYNC_EXECUTOR.submit(() ->
                    followSyncTpl(authorizerToken, appId, nextOpenId -> {
                                // 一次拉取调用最多拉取10000个关注者的OpenID
                                FollowResult result = UserAPI.userGet(authorizerToken, nextOpenId);
                                if (result == null || !result.isSuccess()) {
                                    log.error("followSync|userGetError|result->{}", JSON.toJSONString(result));
                                    return OpenIdsFeeds.empty();
                                }
                                if (result.getData() == null) {
                                    return OpenIdsFeeds.empty();
                                }
                                return new OpenIdsFeeds(result.getData().getOpenid(), result.getNext_openid(), result.getTotal());
                            }
                    ));
        } finally {
            syncUtils.unlockAppSync(appId);
        }
    }

    /**
     * 按标签同步粉丝用户
     *
     * @param appId 公众号Id
     * @param tagId 标签Id
     */
    public void followSyncByTag(String appId,
                                Integer tagId) {
        try {
            boolean tryLock = syncUtils.lockAppSync(appId);
            if (!tryLock) {
                throw new MpFollowException("follow_sync_lock_app_failed", "follow_sync_lock_app_failed, appid: " + appId);
            }

            String authorizerToken = tokenUtils.getAuthorizerToken(appId);

            SYNC_EXECUTOR.submit(() ->
                    followSyncTpl(authorizerToken, appId, nextOpenId -> {
                                UserTagGetResult result = UserAPI.userTagGet(authorizerToken, tagId, nextOpenId);
                                if (result == null || !result.isSuccess()) {
                                    log.error("followSyncByTag|userTagGetError|result->{}", JSON.toJSON(result));
                                    return OpenIdsFeeds.empty();
                                }
                                if (result.getData() == null) {
                                    return OpenIdsFeeds.empty();
                                }
                                return new OpenIdsFeeds(result.getData().getOpenid(), result.getNext_openid(), result.getCount());
                            }
                    ));
        } finally {
            syncUtils.unlockAppSync(appId);

        }
    }

    /**
     * 同步模版方法
     *
     * @param authorizerToken
     * @param appId
     * @param feedsFetcher
     */
    private void followSyncTpl(@NonNull String authorizerToken,
                               @NonNull String appId,
                               @NonNull Function<String, OpenIdsFeeds> feedsFetcher) {
        String nextOpenId = "";
        final AtomicInteger acc = new AtomicInteger();
        int max = 0;
        for (int i = 0; i < 1000; i++) {
            OpenIdsFeeds feeds = feedsFetcher.apply(nextOpenId);
            log.info("followSyncTpl|loop|{}|{}|{}|{}", i, feeds.getOpenIds().size(), feeds.getTotal(), nextOpenId);
            if (feeds.isEmpty()) {
                log.info("followSyncTpl|break|{}", i);
                break;
            }

            max = feeds.getTotal();

            List<String> openIds = feeds.getOpenIds();
            ListUtils.partition(openIds, 100)
                    .parallelStream()
                    .forEach(partition -> {
                        try {
                            followSync0(authorizerToken, appId, partition);

                            int curr = acc.addAndGet(partition.size());
                            syncUtils.appendAppSyncProgress(appId, curr, feeds.getTotal());
                        } catch (Exception e) {
                            log.error("followSyncTpl|Error", e);
                        }
                    });
            nextOpenId = feeds.getNextOpenId();
        }
        if (acc.get() < max) {
            syncUtils.appendAppSyncProgress(appId, max, max);
        }

        log.info("followSyncTpl|end");
    }

    /**
     * 粉丝批量同步
     *
     * @param authorizerToken authorizerToken
     * @param appId           公众号ID
     * @param openIds         粉丝 openId list
     */
    private void followSync0(@NonNull String authorizerToken,
                             @NonNull String appId,
                             List<String> openIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(openIds), "openIds is empty");

        UserInfoList userInfoList = UserAPI.userInfoBatchget(authorizerToken, "zh-CN", openIds);
        List<User> userList = Optional.ofNullable(userInfoList).map(UserInfoList::getUser_info_list).orElse(Collections.emptyList());
        if (CollectionUtils.isEmpty(userList)) {
            throw new MpFollowException("user_batch_get_empty", "user_batch_get_empty, appid: " + appId);
        }

        try {
            List<String> unionIds = userList.stream().map(User::getUnionid).filter(Objects::nonNull).collect(toList());

            List<MpFollow> mpFollows = userList.stream()
                    .map(user -> buildMpFollow(appId, user))
                    .collect(toList());
            repository.batchInsertOrUpdate(mpFollows);

            List<MpFollow> storedFollows = repository.getFollows(appId, openIds);

            Map<String, Long> openIdFollowIdMap = storedFollows.stream().collect(toMap(MpFollow::getOpenId, MpFollow::getId));

            Map<Integer, List<String>> tagOpenIdsMap = groupingByTag(userList);

            for (Map.Entry<Integer, List<String>> entry : tagOpenIdsMap.entrySet()) {
                Integer tagId = entry.getKey();
                List<String> openIds0 = entry.getValue();
                List<Long> followIds = openIds0.stream().map(openIdFollowIdMap::get).filter(Objects::nonNull).collect(toList());
                repository.addTagMembers(tagId, followIds);
            }
            Map<Long, Set<Integer>> followIdTagsMap = userList.stream().collect(
                    toMap(
                            x -> openIdFollowIdMap.get(x.getOpenid()),
                            x -> Arrays.stream(x.getTagid_list()).collect(toSet())
                    ));
            repository.writeEs(storedFollows, followIdTagsMap);
        } catch (Exception e) {
            throw new MpFollowException("follow_sync_error", "follow_sync_error, cause: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 映射标签成员
     *
     * @param userList
     * @return
     */
    private static Map<Integer, List<String>> groupingByTag(List<User> userList) {
        Map<Integer, List<String>> tagUsers = new HashMap<>();

        userList.stream()
                .filter(u -> ArrayUtils.isNotEmpty(u.getTagid_list()))
                .forEach(user -> {
                    final String openId = user.getOpenid();
                    Arrays.stream(user.getTagid_list()).forEach(tagId -> tagUsers.compute(tagId, (k, v) -> {
                        if (v == null) {
                            v = new ArrayList<>();
                        }
                        v.add(openId);
                        return v;
                    }));
                });
        return tagUsers;
    }


    /**
     * 获取粉丝列表
     *
     * @param appId
     * @param offset
     * @param count
     * @return
     */
    public PageQueryResult<MpFollowBean> getFollowList(String appId,
                                                       int offset,
                                                       int count) {
        Long total = repository.countFollows(appId);
        List<MpFollow> follows = repository.getFollows(appId, offset, count);
        List<Long> followIds = follows.stream().map(MpFollow::getId).collect(toList());
        Map<Long, Set<Integer>> userTagsMap = repository.getFollowTagsMap(followIds);
        List<MpFollowBean> list = follows.stream()
                .map(f -> ObjectMapper.INSTANCE.map(f, userTagsMap.get(f.getId())))
                .collect(toList());
        return PageQueryResult.<MpFollowBean>builder()
                .total(total)
                .list(list)
                .build();
    }

    /**
     * 筛选粉丝（分页）
     *
     * @param clause
     * @param offset
     * @param count
     * @return
     */
    public PageQueryResult<MpFollowBean> queryFollowList(FollowClause clause, int offset, int count) {
        int page = offset / count;
        PageQueryResult<Long> pageQueryResult = repository.pageEsSearch(clause, page, count);

        List<Long> followIds = pageQueryResult.getList();
        List<MpFollow> mpFollows = repository.findByIds(followIds);

        Map<Long, Set<Integer>> userTagsMap = repository.getFollowTagsMap(followIds);

        List<MpFollowBean> list = mpFollows.stream().map(follow -> ObjectMapper.INSTANCE.map(follow, userTagsMap.get(follow.getId()))).collect(toList());

        return PageQueryResult.<MpFollowBean>builder()
                .total(pageQueryResult.getTotal())
                .list(list)
                .build();

    }

    /**
     * 修改备注
     */
    public boolean updateRemark(String appId,
                                String openId,
                                String remark) {
        String authorizerToken = tokenUtils.getAuthorizerToken(appId);
        BaseResult baseResult = UserAPI.userInfoUpdateremark(authorizerToken, openId, remark);
        if (baseResult != null && baseResult.isSuccess()) {
            MpFollow follow = repository.getFollow(appId, openId);
            follow.setRemark(remark);
            repository.updateFollow(follow);
            return true;
        }
        return false;
    }

    /**
     * 获取标签列表
     *
     * @param appId
     * @return
     */
    public List<MpTag> getTagList(String appId) {
        String authorizerAccessToken = tokenUtils.getAuthorizerToken(appId);

        TagsGetResult tagsGet = UserAPI.tagsGet(authorizerAccessToken);
        if (tagsGet == null || CollectionUtils.isEmpty(tagsGet.getTags())) {
            return new ArrayList<>();
        }
        return tagsGet.getTags().stream()
                .map(ObjectMapper.INSTANCE::map)
                .collect(toList());
    }

    /**
     * 创建标签
     *
     * @param appId
     * @param name
     * @return
     */
    public MpTag tagCreate(String appId, String name) {
        String authorizerAccessToken = tokenUtils.getAuthorizerToken(appId);
        TagsCreatResult createResult = UserAPI.tagsCreate(authorizerAccessToken, name);
        return Optional.ofNullable(createResult)
                .map(TagsCreatResult::getTag)
                .map(ObjectMapper.INSTANCE::map)
                .orElse(null);
    }

    /**
     * 删除标签
     *
     * @param appId
     * @param tagId
     * @return
     */
    public boolean tagDelete(String appId, Integer tagId) {
        BaseResult baseResult = UserAPI.tagsDelete(tokenUtils.getAuthorizerToken(appId), tagId);
        return baseResult != null && baseResult.isSuccess();
    }

    /**
     * 批量打标 (指定openIds)
     *
     * @param appId
     * @param tagId
     * @param openIds
     * @return
     */
    public boolean batchTag(@NonNull String appId, @NonNull Integer tagId, List<String> openIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(openIds), "openIds is empty");

        String authorizerToken = tokenUtils.getAuthorizerToken(appId);
        BaseResult baseResult = UserAPI.tagsMembersBatchtagging(authorizerToken, tagId, openIds.toArray(new String[openIds.size()]));

        if (baseResult != null && baseResult.isSuccess()) {
            List<MpFollow> follows = repository.getFollows(appId, openIds);
            List<Long> followIds = follows.stream().map(MpFollow::getId).collect(toList());
            repository.addTagMembers(tagId, followIds);
            repository.writeEs(follows);
            return true;
        }
        return false;
    }

    /**
     * 批量取标
     *
     * @param appId
     * @param tagId
     * @param openIds
     * @return
     */
    public boolean batchUnTag(@NonNull String appId, @NonNull Integer tagId, List<String> openIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(openIds), "openIds is empty");

        String authorizerToken = tokenUtils.getAuthorizerToken(appId);
        BaseResult baseResult = UserAPI.tagsMembersBatchuntagging(authorizerToken, tagId, openIds.toArray(new String[openIds.size()]));

        if (baseResult != null && baseResult.isSuccess()) {
            List<MpFollow> follows = repository.getFollows(appId, openIds);
            List<Long> followIds = follows.stream().map(MpFollow::getId).collect(toList());
            repository.removeTagMembers(tagId, followIds);
            repository.writeEs(follows);
            return true;
        }
        return false;
    }

    /**
     * 获取黑名单列表
     *
     * @param beginOpenid
     * @return
     */
    public MpFollowList getBlackList(String appId, String beginOpenid) {
        String authorizerAccessToken = tokenUtils.getAuthorizerToken(appId);
        GetblacklistResult blacklistResult = UserAPI.tagsMembersGetblacklist(authorizerAccessToken, beginOpenid);
        if (blacklistResult != null && blacklistResult.getData() != null && blacklistResult.getData().getOpenid() != null) {
            List<String> openIds = Arrays.asList(blacklistResult.getData().getOpenid());

            List<MpFollow> follows = repository.getFollows(appId, openIds);
            List<Long> followIds = follows.stream().map(MpFollow::getId).collect(toList());
            Map<Long, Set<Integer>> followTagsMap = repository.getFollowTagsMap(followIds);
            List<MpFollowBean> userList = follows.stream().map(f -> ObjectMapper.INSTANCE.map(f, followTagsMap.get(f.getId()))).collect(toList());
            return MpFollowList.builder().list(userList).nextOpenId(blacklistResult.getNext_openid()).build();
        }
        return MpFollowList.builder().build();
    }

    /**
     * 加入到黑名单
     *
     * @param appId
     * @param openIdList
     * @return
     */
    public boolean addToBlackList(String appId, List<String> openIdList) {
        String authorizerAccessToken = tokenUtils.getAuthorizerToken(appId);
        BaseResult baseResult = UserAPI.tagsMembersBatchblacklist(authorizerAccessToken, openIdList.toArray(new String[openIdList.size()]));

        return baseResult != null && baseResult.isSuccess();
    }

    /**
     * 移除黑名单
     *
     * @param appId
     * @param openIdList
     * @return
     */
    public boolean removeFromBlackList(String appId, List<String> openIdList) {
        String authorizerAccessToken = tokenUtils.getAuthorizerToken(appId);
        BaseResult baseResult = UserAPI.tagsMembersBatchunblacklist(authorizerAccessToken, openIdList.toArray(new String[openIdList.size()]));

        return baseResult != null && baseResult.isSuccess();
    }

    /**
     * 构建新follow
     *
     * @param appId
     * @param user
     * @return
     */
    private MpFollow buildMpFollow(@NonNull String appId, @NonNull User user) {
        MpFollow mpFollow = MpFollow.builder()
                .appId(appId)
                .openId(user.getOpenid())
                .unionId(user.getUnionid())
                .groupId(Optional.ofNullable(user.getGroupid()).orElse(0))
                .remark(Optional.ofNullable(user.getRemark()).orElse(""))
                .nickName(StringUtils.defaultString(user.getNickname(), ""))
                .subscribe(Optional.ofNullable(user.getSubscribe()).orElse(0))
                .subscribeScene(Optional.ofNullable(user.getSubscribe_scene()).orElse(""))
                .subscribeTime(user.getSubscribe_time() != null ? new Date(user.getSubscribe_time() * 1000L) : null)
                .interactTimes(0)
                .interactLastTime(null)
                .build();
        return mpFollow;
    }

    /**
     * 更新粉丝手机号
     *
     * @param appId
     * @param openId
     * @param mobile
     */
    public boolean updateMobile(@NonNull String appId, @NonNull String openId, @NonNull String mobile) {
        MpFollow mpFollow = repository.getFollow(appId, openId);
        if (mpFollow == null) {
            throw new IllegalArgumentException("follow_not_found");
        }
        if (mobile.equalsIgnoreCase(mpFollow.getMobile())) {
            return true;
        }
        mpFollow.setMobile(mobile);
        return repository.updateFollow(mpFollow);
    }

    /**
     * 用户关注，更新关注状态
     *
     * @param appId
     * @param openId
     */
    public void subscribe(String appId, String openId) {
        MpFollow follow = repository.getFollow(appId, openId);
        if (follow == null) {
            String authorizerToken = tokenUtils.getAuthorizerToken(appId);
            followSync0(authorizerToken, appId, Collections.singletonList(openId));
        } else {
            follow.setSubscribe(1);
            repository.updateFollow(follow);
        }
    }

    /**
     * 用户取关
     *
     * @param appId
     * @param openId
     */
    public void unsubscribe(String appId, String openId) {
        MpFollow follow = repository.getFollow(appId, openId);
        if (follow == null) {
            String authorizerToken = tokenUtils.getAuthorizerToken(appId);
            followSync0(authorizerToken, appId, Collections.singletonList(openId));
            follow = repository.getFollow(appId, openId);

            if (follow == null) {
                throw new MpFollowException("follow_not_found", "follow_not_found, appId: " + appId + ", openId: " + openId);
            }
        }
        follow.setSubscribe(0);
        follow.setUnsubscribeTime(new Date());
        repository.updateFollow(follow);

    }

    /**
     * 用户互动，eg: 点击菜单, 发送消息
     *
     * @param appId
     * @param openId
     */
    public void interact(String appId, String openId) {
        MpFollow follow = repository.getFollow(appId, openId);
        follow.setInteractTimes(follow.getInteractTimes() + 1);
        follow.setInteractLastTime(new Date());
        repository.updateFollow(follow);
    }

    @Getter
    static class OpenIdsFeeds {
        @NotNull
        private final List<String> openIds;
        private final String nextOpenId;
        private final int total;

        public OpenIdsFeeds(String[] openIds, String nextOpenId, int total) {
            this.openIds = openIds == null ? Collections.emptyList() : Arrays.asList(openIds);
            this.nextOpenId = nextOpenId;
            this.total = total;
        }

        public static OpenIdsFeeds empty() {
            return new OpenIdsFeeds(null, null, 0);
        }

        public boolean isEmpty() {
            return openIds.isEmpty();
        }
    }

    public static class MpFollowException extends RuntimeException {
        private String code;

        public MpFollowException(String code, String message) {
            super(message);
        }
    }
}
