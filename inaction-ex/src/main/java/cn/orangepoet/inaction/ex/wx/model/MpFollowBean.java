package cn.orangepoet.inaction.ex.wx.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author orange.cheng
 * @date 2023/2/16
 */
@Data
@Builder
public class MpFollowBean {
    /**
     * 公众号ID
     */
    private String appId;
    /**
     * openId
     */
    private String openId;
    /**
     * 多个公众号之间用户帐号互通UnionID机制
     */
    private String unionId;
    /**
     * 分组ID
     */
    private Integer groupId;
    /**
     * nickName
     */
    private String nickName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关注来源
     */
    private String subscribeScene;
    /**
     * 关注时间
     */
    private Date subscribeTime;
    /**
     * 关注状态
     */
    private Integer subscribe;
    /**
     * 互动次数
     */
    private Integer interactTimes;
    /**
     * 最后一次互动时间
     */
    private Date interactLastTime;
    /**
     * [NIO]用户ID
     */
    private Long userId;
    /**
     * [NIO]手机号
     */
    private String mobile;
    /**
     * 标签ID组
     */
    private List<Integer> tagIds;

}
