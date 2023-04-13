package cn.orangepoet.inaction.wx.model;

import lombok.Builder;
import lombok.Data;

/**
 * 公众号信息
 *
 * @author orange.cheng
 * @date 2023/2/16
 */
@Data
@Builder
public class MpInfo {
    private String appId;

    private String name;

    private String nickName;

    private Long count;

    private String headImg;
}
