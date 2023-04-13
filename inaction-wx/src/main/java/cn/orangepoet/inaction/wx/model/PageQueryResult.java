package cn.orangepoet.inaction.wx.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;

@Data
@SuperBuilder
public class PageQueryResult<T> {

    private Long total;
    private List<T> list;

    public static <T> PageQueryResult<T> empty() {
        return PageQueryResult.<T>builder().total(0L).list(Collections.emptyList()).build();
    }

    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }
}