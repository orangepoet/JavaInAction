package cn.orangepoet.inaction.ex.wx.utils;

import cn.orangepoet.inaction.ex.wx.model.MpFollow;
import cn.orangepoet.inaction.ex.wx.model.MpFollowBean;
import cn.orangepoet.inaction.ex.wx.model.MpFollowDoc;
import cn.orangepoet.inaction.ex.wx.model.MpTag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;


/**
 * @author orange.cheng
 * @date 2023/2/21
 */
@Mapper
public interface ObjectMapper {
    ObjectMapper INSTANCE = Mappers.getMapper(ObjectMapper.class);

    MpTag map(weixin.popular.bean.user.Tag tag);

    MpFollowBean map(MpFollow mpFollow, Set<Integer> tagIds);

    MpFollowDoc mapDoc(MpFollow follow, Set<Integer> tagIds);
}
