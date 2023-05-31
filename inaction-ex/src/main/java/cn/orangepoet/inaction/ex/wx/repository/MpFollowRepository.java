package cn.orangepoet.inaction.ex.wx.repository;

import cn.orangepoet.inaction.ex.wx.model.FollowClause;
import cn.orangepoet.inaction.ex.wx.model.MpFollow;
import cn.orangepoet.inaction.ex.wx.model.MpFollowDoc;
import cn.orangepoet.inaction.ex.wx.model.PageQueryResult;
import cn.orangepoet.inaction.ex.wx.utils.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.NonNull;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * @author orange.cheng
 * @date 2023/2/16
 */
@Repository
public class MpFollowRepository {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * ES查询Builder
     *
     * @param clause
     * @return
     */
    private static NativeSearchQueryBuilder nativeQueryBuilder(@NonNull FollowClause clause) {
        Preconditions.checkNotNull(clause, "clause is null");
        Preconditions.checkNotNull(clause.getAppId(), "clause.appid is null");

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        filter.must(QueryBuilders.termsQuery("appId", clause.getAppId()));
        // userIds
        if (StringUtils.isNotBlank(clause.getUserIds())) {
            List<String> userIds = Splitter.on(",").trimResults().splitToList(clause.getUserIds());
            filter.must(QueryBuilders.termsQuery("userId", userIds));
        }
        // mobiles
        if (StringUtils.isNotBlank(clause.getMobiles())) {
            List<String> mobiles = Splitter.on(",").trimResults().splitToList(clause.getMobiles());
            filter.must(QueryBuilders.termsQuery("mobiles", mobiles));
        }
        // tags
        //   tags in range
        if (StringUtils.isNotBlank(clause.getOrTags())) {
            List<String> tagsRange = Splitter.on(",").trimResults().splitToList(clause.getOrTags());
            filter.must(QueryBuilders.termsQuery("tagIds", tagsRange));
        }
        //  contains tags
        if (StringUtils.isNotBlank(clause.getAndTags())) {
            List<String> withTags = Splitter.on(",").trimResults().splitToList(clause.getAndTags());
            withTags.stream().map(each -> QueryBuilders.termsQuery("tagIds", each)).forEach(filter::must);
        }

        // sources
        if (StringUtils.isNotBlank(clause.getSources())) {
            List<String> sources = Splitter.on(",").trimResults().splitToList(clause.getSources());
            filter.must(QueryBuilders.termsQuery("source", sources));
        }
        // associateNio
        if (clause.getAssociateNio() != null) {
            if (clause.getAssociateNio()) filter.must(QueryBuilders.existsQuery("userId"));
            else filter.mustNot(QueryBuilders.existsQuery("userId"));
        }
        // subscribe
        if (clause.getSubscribe() != null) {
            List<Integer> subscribes = new ArrayList<>();
            subscribes.add(clause.getSubscribe());
            filter.must(QueryBuilders.termsQuery("subscribe", subscribes));
        }
        // subscribeScenes
        if (clause.getSubscribeScenes() != null) {
            List<String> subscribeScenes = Splitter.on(",").trimResults().splitToList(clause.getSubscribeScenes());
            filter.must(QueryBuilders.termsQuery("subscribe", subscribeScenes));
        }
        // interactLastTime
        if (clause.getInteractLastTime() != null) {
            filter.must(QueryBuilders.rangeQuery("interactLastTime").from(clause.getInteractLastTime().getStart()).to(clause.getInteractLastTime().getEnd()));
        }
        // subscribeTime
        if (clause.getSubscribeTime() != null) {
            filter.must(QueryBuilders.rangeQuery("subscribeTime").from(clause.getSubscribeTime().getStart()).to(clause.getSubscribeTime().getEnd()));
        }

        // ubSubscribeTime
        if (clause.getUnSubscribeTime() != null) {
            filter.must(QueryBuilders.rangeQuery("unSubscribeTime").from(clause.getUnSubscribeTime().getStart()).to(clause.getUnSubscribeTime().getEnd()));
        }
        // interactTimes
        if (clause.getInteractTimes() != null) {
            filter.must(QueryBuilders.termQuery("interactTimes", clause.getInteractTimes()));
        }

        // 过滤条件
        queryBuilder.withFilter(filter);

        return queryBuilder;
    }

    /**
     * 批量添加粉丝
     *
     * @param records
     */
    public synchronized void batchInsertOrUpdate(List<MpFollow> records) {
//        if (CollectionUtils.isEmpty(records)) {
//            return;
//        }
        // myMpFollowMapper.batchInsertOrUpdate(records);
        throw new UnsupportedOperationException();
    }

    /**
     * 分页查询粉丝
     *
     * @param appId
     * @param offset
     * @param count
     * @return
     */
    @NonNull
    public List<MpFollow> getFollows(String appId, int offset, int count) {
        // todo: mybatis.getFollows
        throw new UnsupportedOperationException();
    }

    /**
     * 批量获取粉丝
     *
     * @param idList
     * @return
     */
    public List<MpFollow> findByIds(List<Long> idList) {
//        if (CollectionUtils.isEmpty(idList)) {
//            return Collections.emptyList();
//        }
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andIdIn(idList);
//        return mpFollowMapper.selectByExample(example);
        throw new UnsupportedOperationException();
    }

    public List<MpFollow> findByUnionId(@NonNull String unionId) {
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andUnionIdEqualTo(unionId);
//        return mpFollowMapper.selectByExample(example);
        throw new UnsupportedOperationException();
    }

    public MpFollow findByUnionId(@NonNull String unionId, @NonNull String appId) {
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andUnionIdEqualTo(unionId)
//                .andAppIdEqualTo(appId);
//        List<MpFollow> follows = mpFollowMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(follows)) {
//            return null;
//        }
//        return follows.get(0);
        throw new UnsupportedOperationException();
    }

    public MpFollow getByMobile(String mobile, String appId) {
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andMobileEqualTo(mobile)
//                .andAppIdEqualTo(appId);
//        List<MpFollow> follows = mpFollowMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(follows)) {
//            return null;
//        }
//        return follows.get(0);
        throw new UnsupportedOperationException();
    }

    /**
     * 批量获取粉丝
     *
     * @param appId
     * @param openIds
     * @return
     */
    @NonNull
    public List<MpFollow> getFollows(String appId, List<String> openIds) {
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andAppIdEqualTo(appId)
//                .andOpenIdIn(openIds);
//        List<MpFollow> mpFollows = mpFollowMapper.selectByExample(example);
//        return CollectionUtils.isEmpty(mpFollows) ? Collections.emptyList() : mpFollows;
        throw new UnsupportedOperationException();
    }

    /**
     * 查询粉丝
     *
     * @param appId
     * @param openId
     * @return
     */
    public MpFollow getFollow(String appId, String openId) {
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andAppIdEqualTo(appId)
//                .andOpenIdEqualTo(openId);
//        List<MpFollow> mpFollows = mpFollowMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(mpFollows)) {
//            return null;
//        }
//        return mpFollows.get(0);
        throw new UnsupportedOperationException();
    }

    /**
     * 更新粉丝
     *
     * @param mpFollow
     * @return
     */
    public boolean updateFollow(MpFollow mpFollow) {
//        boolean success = mpFollowMapper.updateByPrimaryKeySelective(mpFollow) > 0;
//        if (success) {
//            writeEs(mpFollow);
//        }
//        return success;
        throw new UnsupportedOperationException();
    }

    /**
     * 粉丝数
     *
     * @param appId
     * @return
     */
    public Long countFollows(String appId) {
//        MpFollowExample example = new MpFollowExample();
//        example.createCriteria()
//                .andAppIdEqualTo(appId);
//        return mpFollowMapper.countByExample(example);
        throw new UnsupportedOperationException();
    }

    /**
     * 添加tags
     *
     * @param tagId     标签ID
     * @param followIds 粉丝ID 集合
     */
    public void addTagMembers(@NonNull Integer tagId, List<Long> followIds) {
//        if (CollectionUtils.isEmpty(followIds)) {
//            return;
//        }
//        List<MpFollowTags> mpUserTags = followIds.stream().map(followId -> {
//            MpFollowTags tags = new MpFollowTags();
//            tags.setFollowId(followId);
//            tags.setTagId(tagId);
//            return tags;
//        }).collect(toList());
//        myMpFollowTagsMapper.batchInsert(mpUserTags);
        throw new UnsupportedOperationException();
    }

    /**
     * 移除tags
     *
     * @param tagId     标签ID
     * @param followIds 粉丝Ids
     */
    public void removeTagMembers(@NonNull Integer tagId, List<Long> followIds) {
//        if (CollectionUtils.isEmpty(followIds)) {
//            return;
//        }
//        MpFollowTagsExample example = new MpFollowTagsExample();
//        example.createCriteria()
//                .andTagIdEqualTo(tagId)
//                .andFollowIdIn(followIds);
//        mpFollowTagsMapper.deleteByExample(example);
        throw new UnsupportedOperationException();
    }

    /**
     * 获取用户tags
     *
     * @param followId
     * @return
     */
    public Set<Integer> getFollowTags(Long followId) {
        Map<Long, Set<Integer>> followTagsMap = getFollowTagsMap(Collections.singletonList(followId));
        if (MapUtils.isEmpty(followTagsMap) || !followTagsMap.containsKey(followId)) {
            return new HashSet<>();
        }
        return followTagsMap.get(followId);
    }

    /**
     * 批量获取用户tags
     *
     * @param followIds 粉丝ID 集合
     * @return
     */
    public Map<Long, Set<Integer>> getFollowTagsMap(List<Long> followIds) {
//        if (CollectionUtils.isEmpty(followIds)) {
//            return Collections.emptyMap();
//        }
//        MpFollowTagsExample example = new MpFollowTagsExample();
//        example.createCriteria()
//                .andFollowIdIn(followIds);
//        List<MpFollowTags> followTags = mpFollowTagsMapper.selectByExample(example);
//        if (followTags == null) {
//            return Collections.emptyMap();
//        }
//        Map<Long, Set<Integer>> result = new HashMap<>();
//        followTags.forEach(t ->
//                result.compute(t.getFollowId(), (k, v) -> {
//                    if (v == null) {
//                        v = new HashSet<>();
//                    }
//                    v.add(t.getTagId());
//                    return v;
//                }));
//        return result;
        throw new UnsupportedOperationException();
    }

    /**
     * 保存ES
     *
     * @param mpFollow
     */
    public void writeEs(MpFollow mpFollow) {
        Set<Integer> tags = getFollowTags(mpFollow.getId());
        elasticsearchRestTemplate.save(ObjectMapper.INSTANCE.mapDoc(mpFollow, tags));
    }

    /**
     * 保存ES
     *
     * @param mpFollows
     */
    public void writeEs(List<MpFollow> mpFollows) {
        List<Long> followIds = mpFollows.stream().map(MpFollow::getId).collect(toList());
        Map<Long, Set<Integer>> followTagsMap = getFollowTagsMap(followIds);

        writeEs(mpFollows, followTagsMap);
    }

    /**
     * 保存ES
     *
     * @param mpFollows
     * @param followTagsMap
     */
    public void writeEs(List<MpFollow> mpFollows, Map<Long, Set<Integer>> followTagsMap) {
        List<MpFollowDoc> mpFollowDocs = mpFollows.stream()
                .map(f -> ObjectMapper.INSTANCE.mapDoc(f, followTagsMap.get(f.getId()))
                ).collect(toList());
        elasticsearchRestTemplate.save(mpFollowDocs);
    }

    /**
     * ES分页查询
     *
     * @param clause
     * @param page
     * @param size
     * @return
     */
    public PageQueryResult<Long> pageEsSearch(FollowClause clause, int page, int size) {
        Preconditions.checkArgument(StringUtils.isNotBlank(clause.getAppId()), "appId is blank");

        NativeSearchQueryBuilder queryBuilder = nativeQueryBuilder(clause);
        // 分页
        queryBuilder.withPageable(PageRequest.of(page, size));

        NativeSearchQuery searchQuery = queryBuilder.build();
        // 只返回id列
        searchQuery.addFields("id");
        searchQuery.setTrackTotalHits(true);

        SearchHits<MpFollowDoc> searchHits = elasticsearchRestTemplate.search(searchQuery, MpFollowDoc.class);

        // 对象转换
        List<Long> list = searchHits.stream().map(SearchHit::getContent).map(MpFollowDoc::getId).collect(toList());
        return PageQueryResult.<Long>builder()
                .list(list)
                .total(searchHits.getTotalHits())
                .build();
    }

    /**
     * 统计查询数量
     *
     * @param clause
     * @return
     */
    public long countEsSearch(FollowClause clause) {
        NativeSearchQueryBuilder queryBuilder = nativeQueryBuilder(clause);

        NativeSearchQuery searchQuery = queryBuilder.build();
        return elasticsearchRestTemplate.count(searchQuery, MpFollowDoc.class);
    }
}
