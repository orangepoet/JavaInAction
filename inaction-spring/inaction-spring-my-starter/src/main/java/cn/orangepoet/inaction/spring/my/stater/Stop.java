package cn.orangepoet.inaction.spring.my.stater;

/**
 * @author chengzhi
 * @date 2021/12/20
 */
public class Stop implements Action {
    @Override
    public void execute() {
        System.out.println("stop");
    }
}