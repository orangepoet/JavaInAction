package cn.orangepoet.inaction.wx;

import cn.orangepoet.inaction.wx.model.MpFollow;
import cn.orangepoet.inaction.wx.repository.MpFollowRepository;
import cn.orangepoet.inaction.wx.utils.MpConfig;
import cn.orangepoet.inaction.wx.utils.RedisClient;
import com.google.common.base.Joiner;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.message.Message;
import weixin.popular.bean.message.message.TextMessage;
import weixin.popular.util.XMLConverUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 包装微信消息&事件推送的API
 *
 * @author orange.cheng
 * @date 2023/2/27
 */
@Component
@Slf4j
public class MessageService {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private MpFollowService mpFollowService;
    @Autowired
    private MpFollowRepository followRepository;


    /**
     * 授权事件接收
     *
     * @param msgSignature
     * @param timestamp
     * @param nonce
     * @param postData
     * @return
     */
    public String receiveAuth(String msgSignature,
                              String timestamp,
                              String nonce,
                              String postData) {
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(mpConfig.getMsgToken(), mpConfig.getAesKey(), mpConfig.getAppId());

            String decryptMsg = wxBizMsgCrypt.decryptMsg(msgSignature, timestamp, nonce, postData);
            Map<String, String> postDataMap = XMLConverUtil.convertToMap(decryptMsg);
            String infoType = MapUtils.getString(postDataMap, "InfoType");

            log.info("receiveAuth|{}|postDataMap->{}", infoType, postDataMap);

            switch (infoType) {
                case "component_verify_ticket":
                    String componentVerifyTicket = MapUtils.getString(postDataMap, "componentVerifyTicket");
                    if (StringUtils.isNotBlank(componentVerifyTicket)) {
                        tokenUtils.storeTicket(componentVerifyTicket, 120 * 60);
                    }
                    break;
                case "authorized":
                case "updateauthorized":
                    String authorizationCode = MapUtils.getString(postDataMap, "AuthorizationCode");
                    tokenUtils.authorization(authorizationCode);
                    break;
                default:
                    log.info("receiveAuth|defaultIgnore");
                    break;
            }
            return "success";
        } catch (Exception e) {
            log.error("receiveAuth|Error|{}", ExceptionUtils.getRootCauseMessage(e), e);
        } finally {
            log.info("receiveAuth|signature->{}|timestamp->{}|nonce->{}|postData->{}", msgSignature, timestamp, nonce, postData);
        }
        return "fail";
    }

    /**
     * 消息与事件接收
     *
     * @param appId
     * @param postData
     * @return
     * @throws UnsupportedEncodingException
     * @throws AesException
     */
    public String receiveMessage(String appId,
                                 String msgSignature,
                                 String timestamp,
                                 String nonce,
                                 String postData) {
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(mpConfig.getMsgToken(), mpConfig.getAesKey(), mpConfig.getAppId());

            String decryptMsg = wxBizMsgCrypt.decryptMsg(msgSignature, timestamp, nonce, postData);
            Map<String, String> postDataMap = XMLConverUtil.convertToMap(decryptMsg);
            String msgType = MapUtils.getString(postDataMap, "MsgType");

            log.info("receiveMessage|{}|postDataMap->{}", msgType, postDataMap);

            switch (msgType.toLowerCase()) {
                case "event":
                    return handleEvent(appId, postDataMap);
                case "text":
                    return handleText(appId, postDataMap);
                default:
                    log.info("receiveMessage|unhandle|{}", msgType);
                    return "";

            }
        } catch (Exception e) {
            log.error("receiveMessage|Error", e);
            return "error";
        }
    }

    /**
     * 消息排重
     *
     * @param appId
     * @param idempId
     * @return
     */
    private boolean checkIdempotent(String appId, String idempId) {
        String key = String.format("mp.message.receive.%s.%s", appId, idempId);
        return redisClient.setIfAbsent(key, "1", 1, TimeUnit.DAYS);
    }

    /**
     * 事件消息处理
     *
     * @param appId       公众号AppID
     * @param postDataMap 消息体Map
     */
    private String handleEvent(String appId, Map<String, String> postDataMap) {
        String fromUserName = MapUtils.getString(postDataMap, "FromUserName");
        String event = MapUtils.getString(postDataMap, "Event");
        String createTime = MapUtils.getString(postDataMap, "CreateTime");

        String idempId = Joiner.on("_").join(fromUserName, createTime);
        if (!checkIdempotent(appId, idempId)) {
            log.info("handleEvent|checkIdempotent=false");
            return "";
        }

        log.info("handleEvent|fromUserName->{}, event->{}", fromUserName, event);

        switch (event) {
            // 用户未关注时，进行关注后的事件推送
            case "subscribe":
                mpFollowService.subscribe(appId, fromUserName);
                break;
            // 用户取注时事件推送
            case "unsubscribe":
                mpFollowService.unsubscribe(appId, fromUserName);
                break;
            // 点击菜单拉取消息时的事件推送
            case "CLICK":
            case "VIEW":
                mpFollowService.interact(appId, fromUserName);
                break;
            default:
                log.info("handleEvent｜ignore｜{}", event);
                break;
        }

        return "success";
    }

    /**
     * 文本消息处理
     *
     * @param appId
     * @param postDataMap
     */
    private String handleText(String appId, Map<String, String> postDataMap) {
        String msgId = MapUtils.getString(postDataMap, "MsgId");
        String content = MapUtils.getString(postDataMap, "Content");
        String fromUserName = MapUtils.getString(postDataMap, "FromUserName");

        log.info("handleText|fromUserName->{}|content->{}", fromUserName, content);
        if (!checkIdempotent(appId, msgId)) {
            log.info("handleText|checkIdempotent=false");
            return "";
        }
        mpFollowService.interact(appId, fromUserName);

        return "success";
    }

    /**
     * 推送消息 - 按 unionId
     *
     * @param appId
     * @param mobile
     * @param content
     */
    public void sendMessage(String appId, String mobile, String content) {

        MpFollow follow = followRepository.getByMobile(mobile, appId);
        if (follow == null) {
            throw new MessageException("follow_not_found", "follow_not_found");
        }

        String authorizerToken = tokenUtils.getAuthorizerToken(appId);
        Message message = new TextMessage(follow.getOpenId(), content);
        BaseResult baseResult = MessageAPI.messageCustomSend(authorizerToken, message);
        if (baseResult == null) {
            throw new MessageException("send_result_null", "send_result_null");
        }
        if (!baseResult.isSuccess()) {
            throw new MessageException("send_failed", "code: " + baseResult.getErrcode() + ", message: " + baseResult.getErrmsg());
        }
    }

    public static class MessageException extends RuntimeException {
        private final String code;

        public MessageException(String code, String message) {
            super(message);
            this.code = code;
        }
    }
}
