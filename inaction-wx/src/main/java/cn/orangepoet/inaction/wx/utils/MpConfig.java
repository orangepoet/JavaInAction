package cn.orangepoet.inaction.wx.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author orange.cheng
 * @date 2023/2/16
 */
@Configuration
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "wechat.platform")
public class MpConfig {
    /**
     * 平台ID
     */
    private String appId;
    /**
     * 密钥
     */
    private String appSecret;
    /**
     * 消息校验Token
     */
    private String msgToken;

    /**
     * 消息加解密Key
     */
    private String aesKey;
    /**
     * 授权链接
     */
    private String authorizeLink;
}
