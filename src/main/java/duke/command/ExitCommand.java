package duke.command;

import duke.task.TaskList;

/**
 * Command to close the application.
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        setCommandString("bye");
    }

    /**
     * Exits the application.
     *
     * @param input Full user input.
     * @param taskList The list of tasks.
     * @return Does not actually return any value since the application exits.
     */
    @Override
    public String parse(String input, TaskList taskList) {
        assert input.substring(0, getCommandLength() - 1).equals(getCommandString())
                : "Input should start with command";

        System.exit(0);
        return "Goodbye, hope to see you again!";
    }
}
