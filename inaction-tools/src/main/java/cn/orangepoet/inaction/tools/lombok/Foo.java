package cn.orangepoet.inaction.tools.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
//@AllArgsConstructor
public class Foo {
    private String name;
    private Integer count;

    public Foo(@NonNull String name,@NonNull Integer count) {
        this.name = name;
        this.count = count;
    }
}
