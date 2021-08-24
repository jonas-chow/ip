package duke.task;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    @Test
    void addTask() throws DukeException {
        TaskList taskList = new TaskList();
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
    }

    @Test
    void doTask() throws DukeException {
        TaskList taskList = new TaskList();
        Task toDo = new ToDo("name");
        taskList.getList().add(toDo);

        assertFalse(toDo.done);
        taskList.doTask(1);
        assertTrue(toDo.done);
    }

    @Test
    void deleteTask() throws DukeException {
        TaskList taskList = new TaskList();
        Task toDo = new ToDo("name");
        taskList.getList().add(toDo);
        taskList.deleteTask(1);
        assertEquals(taskList.getList(), new ArrayList<Task>());
    }

    @Test
    void deleteDone() throws DukeException {
        TaskList taskList = new TaskList();
        Task toDo1 = new ToDo("name");
        Task toDo2 = new ToDo("name");
        taskList.getList().add(toDo1);
        taskList.getList().add(toDo2);

        toDo2.done = true;
        taskList.deleteDone();
        ArrayList<Task> expected = new ArrayList<>();
        expected.add(toDo1);
        assertEquals(taskList.getList(), expected);
    }

    @Test
    void deleteExpired() throws DukeException {
        TaskList taskList = new TaskList();
        Task deadline1 = new Deadline("name", LocalDate.now());
        Task deadline2 = new Deadline("name", LocalDate.parse("2010-01-01"));
        taskList.getList().add(deadline1);
        taskList.getList().add(deadline2);

        taskList.deleteExpired();
        ArrayList<Task> expected = new ArrayList<>();
        expected.add(deadline1);
        assertEquals(taskList.getList(), expected);
    }
}