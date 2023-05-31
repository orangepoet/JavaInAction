package cn.orangepoet.inaction.ex.wx.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author orange.cheng
 * @date 2023/3/10
 */
@Data
@Builder
public class MpFollowList {
    private List<MpFollowBean> list;
    private String nextOpenId;
}
