package cn.orangepoet.inaction.ex.wx.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class MpFollow {
    private Long id;

    private String appId;

    private String openId;

    private String unionId;

    private Integer groupId;

    private String nickName;

    private String remark;

    private String subscribeScene;

    private Date subscribeTime;

    private Date unsubscribeTime;

    private Integer subscribe;

    private Integer interactTimes;

    private Date interactLastTime;

//    private Long userId;

    private String mobile;

    private String ext;

    private Date createTime;

    private Date updateTime;
}