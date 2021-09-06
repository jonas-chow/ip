package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.TaskList;

/**
 * Command to add a deadline into the task list.
 */
public class DeadlineCommand extends Command {
    public DeadlineCommand() {
        setCommandString("deadline");
    }

    /**
     * Parses the user input for a name and a date.
     * Then creates the deadline and adds it into the taskList.
     *
     * @param input Full user input.
     * @param taskList The list of tasks.
     * @return The response telling the user that a deadline has been created.
     * @throws DukeException Any exception caught when executing this command.
     */
    @Override
    public String parse(String input, TaskList taskList) throws DukeException {
        assert input.substring(0, getCommandLength() - 1).equals(getCommandString()) :
                "Input should start with command";
        assert taskList != null : "taskList should not be null";

        // No space after the command
        if (input.length() <= getCommandLength()) {
            throw new DukeException("Please input the deadline's name and date!");
        }

        String[] inputs = input.substring(getCommandLength()).split("/by");

        if (inputs.length < 2) {
            // /by not specified
            throw new DukeException("Please input when the deadline is to be done by!");
        } else if (inputs.length > 2) {
            // more than one /by
            throw new DukeException("Please input only one deadline!");
        }

        String name = inputs[0].strip();
        if (name.equals("")) {
            throw new DukeException("Please input the deadline's name!");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(inputs[1].strip());
        } catch (DateTimeParseException e) {
            throw new DukeException("Please input your date in the format YYYY-MM-DD");
        }

        Deadline deadline = new Deadline(name, date);
        return taskList.addTask(deadline);
    }
}
