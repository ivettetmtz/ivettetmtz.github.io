package test;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {
    private String id;
    private String name;
    private String description;
    private String IDLong;
    private String nameLong;
    private String descriptionLong;

    TaskTest() {
    }

    @BeforeEach
    void setUp() {
        this.id = "0000000001";
        this.name = "ListNumberOne";
        this.description = "Making a list.";
        this.IDLong = "111222333444555666777888999";
        this.nameLong = "This is representing a long task name";
        this.descriptionLong = "This description is too long, there should be a description with less than 50 characters and it should also not be null. This will give you an error.";
    }

    @Test
    void getTaskIdTest() {
        Task task = new Task(this.id);
        Assertions.assertEquals(this.id, task.getTaskId());
    }

    @Test
    void getNameTest() {
        Task task = new Task(this.id, this.name);
        Assertions.assertEquals(this.name, task.getName());
    }

    @Test
    void getDescriptionTest() {
        Task task = new Task(this.id, this.name, this.description);
        Assertions.assertEquals(this.description, task.getDescription());
    }

    @Test
    void setNameTest() {
        Task task = new Task();
        task.setName(this.name);
        Assertions.assertEquals(this.name, task.getName());
    }

    @Test
    void setDescriptionTest() {
        Task task = new Task();
        task.setDescription(this.description);
        Assertions.assertEquals(this.description, task.getDescription());
    }

    @Test
    void tooLongTaskIDTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(this.IDLong);
        });
    }

    @Test
    void tooLongNameTest() {
        Task task = new Task();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setName(this.nameLong);
        });
    }

    @Test
    void tooLongDescriptionTest() {
        Task task = new Task();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription(this.descriptionLong);
        });
    }

    @Test
    void TaskIdNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task((String)null);
        });
    }

    @Test
    void TaskNameNullTest() {
        Task task = new Task();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setName((String)null);
        });
    }

    @Test
    void TaskDescriptionNullTest() {
        Task task = new Task();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription((String)null);
        });
    }
}
