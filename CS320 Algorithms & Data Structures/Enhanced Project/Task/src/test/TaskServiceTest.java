package test;

import model.Task;
import model.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class TaskServiceTest {
    private String id;
    private String name;
    private String description;
    private String nameLong;
    private String descriptionLong;

    TaskServiceTest() {
    }

    @BeforeEach
    void setUp() {
        this.id = "0000000001";
        this.name = "ListNumberOne";
        this.description = "Making a list.";
        this.nameLong = "This is representing a long task name";
        this.descriptionLong = "This description is too long, there should be a description with less than 50 characters and it should also not be null. This will give you an error.";
    }

    @Test
    void newTaskTest() {
        TaskService service = new TaskService();
        service.newTask();
        Assertions.assertNotNull(((Task)service.getTaskList().get(0)).getTaskId());
        Assertions.assertNotEquals("INITIAL", ((Task)service.getTaskList().get(0)).getTaskId());
    }

    @Test
    void newTaskNameTest() {
        TaskService service = new TaskService();
        service.newTask(this.name);
        Assertions.assertNotNull(((Task)service.getTaskList().get(0)).getName());
        Assertions.assertEquals(this.name, ((Task)service.getTaskList().get(0)).getName());
    }

    @Test
    void newTaskDescriptionTest() {
        TaskService service = new TaskService();
        service.newTask(this.name, this.description);
        Assertions.assertNotNull(((Task)service.getTaskList().get(0)).getDescription());
        Assertions.assertEquals(this.description, ((Task)service.getTaskList().get(0)).getDescription());
    }

    @Test
    void tooLongNameTest() {
        TaskService service = new TaskService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.newTask(this.nameLong);
        });
    }

    @Test
    void tooLongDescriptionTest() {
        TaskService service = new TaskService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.newTask(this.name, this.descriptionLong);
        });
    }

    @Test
    void nameNullTest() {
        TaskService service = new TaskService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.newTask((String)null);
        });
    }

    @Test
    void descriptionNullTest() {
        TaskService service = new TaskService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.newTask(this.name, (String)null);
        });
    }

    @Test
    void deleteTaskTest() throws Exception {
        TaskService service = new TaskService();
        service.newTask();
        Assertions.assertEquals(1, service.getTaskList().size());
        service.deleteTask(((Task)service.getTaskList().get(0)).getTaskId());
        Assertions.assertEquals(0, service.getTaskList().size());
    }

    @Test
    void deleteTaskNotFoundTest() throws Exception {
        TaskService service = new TaskService();
        service.newTask();
        Assertions.assertEquals(1, service.getTaskList().size());
        Assertions.assertThrows(Exception.class, () -> {
            service.deleteTask(this.id);
        });
        Assertions.assertEquals(1, service.getTaskList().size());
    }

    @Test
    void updateNameTest() throws Exception {
        TaskService service = new TaskService();
        service.newTask();
        service.updateName(((Task)service.getTaskList().get(0)).getTaskId(), this.name);
        Assertions.assertEquals(this.name, ((Task)service.getTaskList().get(0)).getName());
    }

    @Test
    void updateDescriptionTest() throws Exception {
        TaskService service = new TaskService();
        service.newTask();
        service.updateDescription(((Task)service.getTaskList().get(0)).getTaskId(), this.description);
        Assertions.assertEquals(this.description, ((Task)service.getTaskList().get(0)).getDescription());
    }

    @Test
    void updateNameNotFoundTest() throws Exception {
        TaskService service = new TaskService();
        service.newTask();
        Assertions.assertThrows(Exception.class, () -> {
            service.updateName(this.id, this.name);
        });
    }

    @Test
    void updateDescriptionNotFoundTest() throws Exception {
        TaskService service = new TaskService();
        service.newTask();
        Assertions.assertThrows(Exception.class, () -> {
            service.updateDescription(this.id, this.description);
        });
    }

    @RepeatedTest(4)
    void UuidTest() {
        TaskService service = new TaskService();
        service.newTask();
        service.newTask();
        service.newTask();
        Assertions.assertEquals(3, service.getTaskList().size());
        Assertions.assertNotEquals(((Task)service.getTaskList().get(0)).getTaskId(), ((Task)service.getTaskList().get(1)).getTaskId());
        Assertions.assertNotEquals(((Task)service.getTaskList().get(0)).getTaskId(), ((Task)service.getTaskList().get(2)).getTaskId());
        Assertions.assertNotEquals(((Task)service.getTaskList().get(1)).getTaskId(), ((Task)service.getTaskList().get(2)).getTaskId());
    }
}
