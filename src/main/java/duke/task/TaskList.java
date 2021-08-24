package duke.task;

import java.util.ArrayList;
import duke.exception.DukeException;
import duke.Duke;
import duke.io.TextColor;

public class TaskList {
    private final ArrayList<Task> tasks;

    // used when loading from csv
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // used when new user
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds the task into the taskList
     * Also saves the taskList and queues a message to be printed
     *
     * @param task The task to be added
     * @throws DukeException I/O exception from saving the taskList state
     */
    public void addTask(Task task) throws DukeException {
        tasks.add(task);
        save();

        Duke.ui.addMessage("Got it. I've added this task:\n  "
                + task.toString() + "\n" + taskLengthReport(), TextColor.DEFAULT);
    }

    /**
     * Marks the specified task as done
     * Also saves the taskList and queues a message to be printed
     *
     * @param taskNum The number of the task to be marked as done (1-indexed)
     * @throws DukeException Exception from out of range index, or from saving the list
     */
    public void doTask(int taskNum) throws DukeException {
        int idx = getTaskIndexFromTaskNum(taskNum);
        Task task = tasks.get(idx);
        task.doTask();
        save();

        Duke.ui.addMessage("Nice! I've marked this task as done:\n  "
                + task.toString(), TextColor.DEFAULT);
    }

    /**
     * Deletes the specified task
     * Also saves the taskList and queues a message to be printed
     *
     * @param taskNum The number of the task to be deleted (1-indexed)
     * @throws DukeException Exception from out of range index, or from saving the list
     */
    public void deleteTask(int taskNum) throws DukeException {
        int idx = getTaskIndexFromTaskNum(taskNum);

        Task task = tasks.get(idx);
        tasks.remove(idx);
        save();

        Duke.ui.addMessage("Noted! I've removed this task:\n  "
                + task.toString() + "\n" + taskLengthReport(), TextColor.DEFAULT);
    }

    /**
     * Deletes all tasks that have been marked as done
     * Also saves the taskList and queues a message to be printed
     *
     * @throws DukeException I/O exception from saving the taskList state
     */
    public void deleteDone() throws DukeException {
        tasks.removeIf(Task::isDone);
        save();
        Duke.ui.addMessage("Noted! I've removed all completed tasks.\n"
                + taskLengthReport(), TextColor.DEFAULT);
    }

    /**
     * Deletes all tasks that are expired
     * Also saves the taskList and queues a message to be printed
     *
     * @throws DukeException I/O exception from saving the taskList state
     */
    public void deleteExpired() throws DukeException {
        tasks.removeIf(Task::isExpired);
        save();
        Duke.ui.addMessage("Noted! I've removed all expired tasks.\n"
                + taskLengthReport(), TextColor.DEFAULT);
    }

    /**
     * Queues a message listing all the tasks to be printed
     */
    public void list() {
        int size = tasks.size();
        if (size == 0) {
            Duke.ui.addMessage("No tasks yet!", TextColor.DEFAULT);
        } else {
            for (int i = 0; i < size; i++) {
                Task task = tasks.get(i);
                Duke.ui.addMessage((i + 1) + ". " + task.toString()
                        + (i == size - 1 ? "" : "\n"), task.getListColor());
            }
        }
    }

    /**
     * Returns the 0-indexed index of the task from it's 1-indexed index
     *
     * @param taskNum 1-indexed index of the task
     * @return 0-indexed index of the task
     * @throws DukeException Exception thrown if the index is out of range
     */
    private int getTaskIndexFromTaskNum(int taskNum) throws DukeException {
        if (taskNum <= 0 || taskNum > tasks.size()) {
            throw new DukeException("Please input the ID of a task!");
        }

        // tasks are 1-indexed to the user, but 0-indexed by implementation
        return taskNum - 1;
    }

    /**
     * Returns a string telling the user how many tasks are in the list
     *
     * @return string telling the user how many tasks are in the list
     */
    private String taskLengthReport() {
        return "Now you have " + tasks.size()
                + (tasks.size() != 1 ? " tasks" : " task") + " in the list.";
    }

    /**
     * Saves the state of the taskList
     *
     * @throws DukeException I/O exception from saving
     */
    private void save() throws DukeException {
        Duke.storage.save(convertToSaveString());
    }

    /**
     * Returns a string representing the tasks compliant to the save format
     *
     * @return a string representing the tasks compliant to the save format
     */
    private String convertToSaveString() {
        StringBuilder sb = new StringBuilder();

        if (tasks.size() == 0) {
            return "";
        }

        for (Task task : tasks) {
            sb.append(task.getSaveString());
            sb.append('\n');
        }

        // remove the last newline character
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * Returns the taskList to operate on without any side effects
     *
     * @return the taskList
     */
    protected ArrayList<Task> getList() {
        return tasks;
    }
}
