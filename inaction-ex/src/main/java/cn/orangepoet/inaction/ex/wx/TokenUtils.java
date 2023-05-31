package cn.orangepoet.inaction.ex.wx;

import cn.orangepoet.inaction.ex.wx.utils.MpConfig;
import cn.orangepoet.inaction.ex.wx.utils.RedisClient;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import weixin.popular.api.ComponentAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.component.ApiQueryAuthResult;
import weixin.popular.bean.component.AuthorizerAccessToken;
import weixin.popular.bean.component.ComponentAccessToken;
import weixin.popular.bean.component.PreAuthCode;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * <a href="https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/Before_Develop/creat_token.html">Token生成说明参考</a>
 *
 * @author orange.cheng
 * @date 2023/2/17
 */
@Component
@Slf4j
public class TokenUtils {
    private final MpConfig config;
    private final Cache cache;

    public TokenUtils(@NonNull RedisClient redisClient, MpConfig config) {
        this.config = config;
        this.cache = new Cache(redisClient);
    }

    /**
     * component_verify_ticket
     *
     * @return
     */
    private String getTicket() {
        return cache.get(Cache.Key.ticket());
    }

    /**
     * 微信推送的票据, 需要失效 ComponentToken
     *
     * @param ticket
     * @param expire 单位：秒
     */
    public void storeTicket(String ticket, int expire) {
        cache.set(Cache.Key.ticket(), ticket, expire);
        cache.invalid(Cache.Key.componentAccessToken());
    }

    /**
     * 获取第三方平台接口的调用凭据
     * <p>
     * 参数（app_id, app_secret, ticket）
     *
     * @return component_access_token
     */
    public String getComponentAccessToken() {
        return cache.getToken(Cache.Key.componentAccessToken(), () -> {
            ComponentAccessToken componentAccessToken = ComponentAPI.api_component_token(config.getAppId(), config.getAppSecret(), getTicket());
            if (componentAccessToken == null || !componentAccessToken.isSuccess()) {
                String code = Optional.of(componentAccessToken).map(t -> t.getErrcode()).orElse("");
                String msg = Optional.of(componentAccessToken).map(t -> t.getErrmsg()).orElse("");
                throw new TokenException("component_token_error", "code: " + code + ", message:" + msg);
            }
            return new TokenResult(componentAccessToken.getComponent_access_token(), componentAccessToken.getExpires_in());
        });
    }

    /**
     * 获取 预授权码
     * <p>
     * 依赖: (component_access_token, app_id)
     *
     * @param componentAccessToken
     * @return
     */
    public String getPreAuthCode(String componentAccessToken) {
        return cache.getToken(Cache.Key.preAuthCode(), () -> {
            PreAuthCode preAuthCode = ComponentAPI.api_create_preauthcode(componentAccessToken, config.getAppId());
            if (preAuthCode != null && preAuthCode.isSuccess()) {
                return new TokenResult(preAuthCode.getPre_auth_code(), preAuthCode.getExpires_in());
            }
            throw new TokenException("pre_auth_code_error", String.format("api_create_preauthcode_error, result: %s", new ResultInfo(preAuthCode)));
        });
    }

    /**
     * 公众号给三方授权，更新 authorizer_refresh_token；所以除非授权码变化，否则长时间保存 authorizer_refresh_token
     * <p>
     * 注意：
     * 1. authorizer_refresh_token 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌。
     * <p>
     * 2. 用户重新授权后，之前的刷新令牌会失效。
     * <p>
     *
     * @param authorizationCode 授权码
     * @return
     */
    public boolean authorization(String authorizationCode) {
        log.info("authorization｜{}", authorizationCode);

        ApiQueryAuthResult apiQueryAuthResult = ComponentAPI.api_query_auth(getComponentAccessToken(), config.getAppId(), authorizationCode);
        if (apiQueryAuthResult != null
                && apiQueryAuthResult.isSuccess()
                && apiQueryAuthResult.getAuthorization_info() != null) {
            ApiQueryAuthResult.Authorization_info authorizationInfo = apiQueryAuthResult.getAuthorization_info();
            String authorizerAppId = authorizationInfo.getAuthorizer_appid();
            String authorizerRefreshToken = authorizationInfo.getAuthorizer_refresh_token();
            cache.set(Cache.Key.authorizerRefreshToken(authorizerAppId), authorizerRefreshToken, 365 * 24 * 3600);
            return true;
        }
        log.error("refreshAuthorizerRefreshToken|Result_Error|apiQueryAuthResult->{}", JSON.toJSONString(apiQueryAuthResult));
        return false;
    }

    /**
     * authorizer_refresh_token，用于authorizer_access_token过期后请求新的。
     *
     * <p>
     *
     * @param authorizerAppId 公众号ID
     * @return
     */
    private String getAuthorizerRefreshToken(String authorizerAppId) {
        return cache.get(Cache.Key.authorizerRefreshToken(authorizerAppId));
    }

    /**
     * 获取授权帐号调用令牌
     * <p>
     * 依赖：（component_access_token, app_id, authorizer_app_id, authorizer_refresh_token）
     *
     * @param authorizerAppId
     * @return authorizer_access_token
     */
    public String getAuthorizerToken(String authorizerAppId) {
        return cache.getToken(Cache.Key.authorizerToken(authorizerAppId), () -> {
            String componentAccessToken = getComponentAccessToken();

            AuthorizerAccessToken authorizerAccessToken = ComponentAPI.api_authorizer_token(
                    componentAccessToken,
                    config.getAppId(),
                    authorizerAppId,
                    getAuthorizerRefreshToken(authorizerAppId)
            );
            if (authorizerAccessToken != null && authorizerAccessToken.isSuccess()) {
                return new TokenResult(authorizerAccessToken.getAuthorizer_access_token(), authorizerAccessToken.getExpires_in());
            }
            throw new TokenException("get_authorizer_token_error", String.format("api_authorizer_token_error, result: %s", new ResultInfo(authorizerAccessToken)));
        });
    }


    static class Cache {
        private final RedisClient redisClient;

        public Cache(RedisClient redisClient) {
            this.redisClient = redisClient;
        }

        public String get(String key) {
            return redisClient.get(key);
        }

        public void set(String key, String value, long expireSeconds) {
            this.redisClient.set(key, value, expireSeconds);
        }

        public void invalid(String key) {
            redisClient.delete(key);
        }

        public String getToken(String key, Supplier<TokenResult> supplier) {
            String value = redisClient.get(key);
            if (StringUtils.isNotBlank(value)) {
                return value;
            }
            TokenResult tokenResult = supplier.get();
            if (tokenResult != null) {
                redisClient.set(key, tokenResult.getValue(), tokenResult.getExpire());
            }
            return tokenResult != null ? tokenResult.getValue() : null;
        }

        static class Key {
            public static String ticket() {
                return "flotage.wechat.component.ticket";
            }

            public static String componentAccessToken() {
                return "flotage.mp.component_access_token";
            }

            public static String preAuthCode() {
                return "flotage.mp.pre_auth_code";
            }

            public static String authorizerRefreshToken(String authorizerAppId) {
                return Joiner.on(".").join("flotage.mp.authorizer_refresh_token", authorizerAppId);
            }

            public static String authorizerToken(String authorizerAppId) {
                return Joiner.on(".").join("flotage.mp.authorizer_token", authorizerAppId);
            }
        }
    }


    @ToString
    static class ResultInfo {
        private final String code;
        private final String message;

        public ResultInfo(BaseResult result) {
            if (result != null) {
                this.code = result.getErrcode();
                this.message = result.getErrmsg();
            } else {
                this.code = "null_result";
                this.message = "null_result";
            }
        }
    }

    @Getter
    @AllArgsConstructor
    public static class TokenResult {
        private String value;
        // 过期时间
        private long expire;
    }

    public static class TokenException extends RuntimeException {
        private final String code;

        public TokenException(String code, String message) {
            super(message);
            this.code = code;
        }
    }
}