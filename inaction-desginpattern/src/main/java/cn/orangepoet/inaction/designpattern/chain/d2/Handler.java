package cn.orangepoet.inaction.designpattern.chain.d2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chengzhi
 * @date 2019/03/08
 */

public abstract class Handler {

    @Getter
    @Setter
    private Handler next;

    /**
     * 执行动作
     */
    public abstract void handle();

    /**
     * 执行完后动作
     */
    void fireHandle() {
        if (this.next != null) {
            this.next.handle();
        }
    }

    @Builder
    @Getter
    @Setter
    public static class Foo {
        private String p1;
        private String p2;
    }

}
