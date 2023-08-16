package cn.orangepoet.inaction.gof.command;

/**
 * TurnOffCommand is a concrete command, keep the action of turnning off light.
 * 
 * @author chengz
 *
 */
public class TurnOffCommand implements Command {
    private Light light;

    public TurnOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
}
