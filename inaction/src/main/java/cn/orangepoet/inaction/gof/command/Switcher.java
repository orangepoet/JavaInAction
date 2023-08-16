package cn.orangepoet.inaction.gof.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Switcher is an invoker, doesn't know about concrete command, only command
 * interface, and how to execute.
 * 
 * @author chengz
 *
 */
public class Switcher {

    // bookkeeping
    private List<Command> history = new ArrayList<Command>();

    public void storeAndExecute(Command command) {
        history.add(command);
        command.execute();
    }
}
