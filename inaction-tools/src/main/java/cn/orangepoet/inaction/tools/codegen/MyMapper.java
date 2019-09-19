package cn.orangepoet.inaction.tools.codegen;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author chengzhi
 * @date 2019/09/19
 */
@Mapper
public interface MyMapper {
    MyMapper INSTANCE = Mappers.getMapper(MyMapper.class);

    Foo convert2Foo(Woo woo);
}
