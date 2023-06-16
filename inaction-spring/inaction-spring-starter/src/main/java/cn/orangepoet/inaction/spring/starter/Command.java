package cn.orangepoet.inaction.spring.starter;

/**
 * @author chengzhi
 * @date 2021/12/20
 */
public class Command {
    private final Action action;

    public Command(Action action) {
        this.action = action;
    }

    public void doAction() {
        this.action.execute();
    }
}
