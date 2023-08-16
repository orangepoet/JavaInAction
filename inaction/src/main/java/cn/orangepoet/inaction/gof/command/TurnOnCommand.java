package cn.orangepoet.inaction.gof.command;

/**
 * TurnOnCommand is a concrete command, keep the action of turnning on light.
 * 
 * @author chengz
 *
 */
public class TurnOnCommand implements Command {

    private Light light;

    public TurnOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
}
