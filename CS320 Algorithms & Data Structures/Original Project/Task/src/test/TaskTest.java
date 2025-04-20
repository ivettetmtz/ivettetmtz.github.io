package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.model.Task;

class TaskTest {

	private String id, name, description;
	private String IDLong, nameLong, descriptionLong;

	@BeforeEach
	void setUp() {
		  id = "0000000001";
			name = "ListNumberOne";
			description = "Making a list.";
			IDLong = "111222333444555666777888999";
			nameLong = "This is representing a long task name";
			descriptionLong =
	      "This description is too long, there should be a description with less than 50 characters and it should also not be null. This will give you an error.";
	}

	@Test
	void getTaskIdTest() {
	    Task task = new Task(id);
	    Assertions.assertEquals(id, task.getTaskId());
	}

	@Test
	void getNameTest() {
	    Task task = new Task(id, name);
	    Assertions.assertEquals(name, task.getName());
	}

	@Test
	void getDescriptionTest() {
	    Task task = new Task(id, name, description);
	    Assertions.assertEquals(description, task.getDescription());
	}

	@Test
	void setNameTest() {
	    Task task = new Task();
	    task.setName(name);
	    Assertions.assertEquals(name, task.getName());
	}

	@Test
	void setDescriptionTest() {
	    Task task = new Task();
	    task.setDescription(description);
	    Assertions.assertEquals(description, task.getDescription());
	}

	@Test
	void tooLongTaskIDTest() {
    	Assertions.assertThrows(IllegalArgumentException.class,
                            () -> new Task(IDLong));
	}

	@Test
	void tooLongNameTest() {
	    Task task = new Task();
	    Assertions.assertThrows(IllegalArgumentException.class,
                            () -> task.setName(nameLong));
	}

	@Test
	void tooLongDescriptionTest() {
	    Task task = new Task();
	    Assertions.assertThrows(IllegalArgumentException.class,
                            () -> task.setDescription(descriptionLong));
	}

	@Test
	void TaskIdNullTest() {
	    Assertions.assertThrows(IllegalArgumentException.class,
	                            () -> new Task(null));
	}

	@Test
	void TaskNameNullTest() {
	    Task task = new Task();
	    Assertions.assertThrows(IllegalArgumentException.class,
                            () -> task.setName(null));
	}

	@Test
	void TaskDescriptionNullTest() {
	    Task task = new Task();
	    Assertions.assertThrows(IllegalArgumentException.class,
                            () -> task.setDescription(null));
	}
}