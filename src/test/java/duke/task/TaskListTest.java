package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import duke.io.TaskStorage;
import org.junit.jupiter.api.Test;

import duke.exception.DukeException;

class TaskListTest {
    private TaskList taskList = new TaskList();
    // temporarily store the tasks in the list
    private ArrayList<Task> savedTasks = new ArrayList<>(taskList.getList());

    @Test
    void addTask() throws DukeException {
        taskList.getList().clear();

        Task todo = new ToDo("name");
        Task deadline = new Deadline("name", LocalDate.now());
        Task event = new Event("name", LocalDate.now());

        taskList.addTask(todo);
        taskList.addTask(deadline);
        taskList.addTask(event);

        ArrayList<Task> expected = new ArrayList<>();
        expected.add(todo);
        expected.add(deadline);
        expected.add(event);

        assertEquals(taskList.getList(), expected);

        // overwrite anything saved during the test
        TaskStorage.save(savedTasks);
    }

    @Test
    void doTask() throws DukeException {
        taskList.getList().clear();

        Task toDo = new ToDo("name");
        taskList.getList().add(toDo);

        assertFalse(toDo.isDone);
        taskList.doTask(1);
        assertTrue(toDo.isDone);

        // overwrite anything saved during the test
        TaskStorage.save(savedTasks);
    }

    @Test
    void deleteTask() throws DukeException {
        taskList.getList().clear();

        Task toDo = new ToDo("name");
        taskList.getList().add(toDo);
        taskList.deleteTask(1);
        assertEquals(taskList.getList(), new ArrayList<Task>());

        // overwrite anything saved during the test
        TaskStorage.save(savedTasks);
    }

    @Test
    void deleteDone() throws DukeException {
        taskList.getList().clear();

        Task toDo1 = new ToDo("name");
        Task toDo2 = new ToDo("name");
        taskList.getList().add(toDo1);
        taskList.getList().add(toDo2);

        toDo2.isDone = true;
        taskList.deleteDone();
        ArrayList<Task> expected = new ArrayList<>();
        expected.add(toDo1);
        assertEquals(taskList.getList(), expected);

        // overwrite anything saved during the test
        TaskStorage.save(savedTasks);
    }

    @Test
    void deleteExpired() throws DukeException {
        taskList.getList().clear();

        Task deadline1 = new Deadline("name", LocalDate.now());
        Task deadline2 = new Deadline("name", LocalDate.parse("2010-01-01"));
        taskList.getList().add(deadline1);
        taskList.getList().add(deadline2);

        taskList.deleteExpired();
        ArrayList<Task> expected = new ArrayList<>();
        expected.add(deadline1);
        assertEquals(taskList.getList(), expected);

        // overwrite anything saved during the test
        TaskStorage.save(savedTasks);
    }
}
