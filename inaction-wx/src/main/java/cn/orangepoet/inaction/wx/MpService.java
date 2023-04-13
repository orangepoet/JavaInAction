package cn.orangepoet.inaction.wx;

import cn.orangepoet.inaction.wx.model.MpInfo;
import cn.orangepoet.inaction.wx.model.PageQueryResult;
import cn.orangepoet.inaction.wx.repository.MpFollowRepository;
import cn.orangepoet.inaction.wx.utils.MpConfig;
import cn.orangepoet.inaction.wx.utils.SyncUtils;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.popular.api.ComponentAPI;
import weixin.popular.bean.component.ApiGetAuthorizerInfoResult;
import weixin.popular.bean.component.ApiGetAuthorizerListResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 包装微信公众号管理的API
 *
 * @author orange.cheng
 * @date 2023/2/16
 */
@Service
@Slf4j
public class MpService {
    @Resource
    private MpConfig config;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private MpFollowRepository mpFollowRepository;
    @Autowired
    private SyncUtils syncUtils;

    /**
     * 获取授权公众号列表
     *
     * @param offset
     * @param count
     * @return
     */
    public PageQueryResult<MpInfo> getMpList(int offset, int count) {
        String token = tokenUtils.getComponentAccessToken();
        ApiGetAuthorizerListResult listResult = ComponentAPI.api_get_authorizer_list(token, config.getAppId(), String.valueOf(offset), String.valueOf(count));
        if (listResult == null || listResult.getList() == null) {
            return PageQueryResult.empty();
        }
        List<MpInfo> list = listResult.getList().stream().parallel()
                .map(x -> getMpInfo(token, x.getAuthorizer_appid()))
                .collect(Collectors.toList());
        return PageQueryResult.<MpInfo>builder()
                .total(listResult.getTotal_count().longValue())
                .list(list)
                .build();
    }


    /**
     * 获取授权公众号信息
     *
     * @param token
     * @param authorizerAppId
     * @return
     */
    private MpInfo getMpInfo(String token, String authorizerAppId) {
        ApiGetAuthorizerInfoResult infoResult = ComponentAPI.api_get_authorizer_info(token, config.getAppId(), authorizerAppId);

        Long count = mpFollowRepository.countFollows(authorizerAppId);
        return MpInfo.builder()
                .appId(authorizerAppId)
                .name(infoResult.getAuthorizer_info().getUser_name())
                .nickName(infoResult.getAuthorizer_info().getNick_name())
                .headImg(infoResult.getAuthorizer_info().getHead_img())
                .count(count)
                .build();

    }

    /**
     * 获取授权链接
     *
     * @return
     */
    public String getAuthorizeLink() {
        String componentAccessToken = tokenUtils.getComponentAccessToken();
        String preAuthCode = tokenUtils.getPreAuthCode(componentAccessToken);
        ImmutableMap<String, String> params = ImmutableMap.of(
                "component_appid", config.getAppId(),
                "pre_auth_code", preAuthCode);

        return StrSubstitutor.replace(config.getAuthorizeLink(), params);
    }

    /**
     * 公众号给三方授权
     *
     * @param authCode
     * @return
     */
    public boolean authorization(String authCode) {
        return tokenUtils.authorization(authCode);
    }

    /**
     * 更新ticket
     */
    public void updateTicket(String ticket) {
        tokenUtils.storeTicket(ticket, 3600 * 2);
    }


    /**
     * 获取同步进度
     *
     * @param appId
     * @return
     */
    public Future<Double> getSyncProgress(String appId) {
        return CompletableFuture.supplyAsync(() -> syncUtils.getAppSyncProgress(appId));
    }
}
