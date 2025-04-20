package main.java.model;

public class Task {

	private String taskID;
	private String name;
	private String description;

	  public Task() {
	    taskID = "INITIAL";
	    name = "INITIAL";
	    description = "INITIAL DESCRIPTION";
	  }

	  public Task(String taskId) {
	    checkTaskId(taskId);
	    name = "INITIAL";
	    description = "INITIAL DESCRIPTION";
	  }

	  public Task(String taskId, String name) {
	    checkTaskId(taskId);
	    setName(name);
	    description = "INITIAL DESCRIPTION";
	  }

	  public Task(String taskId, String name, String description) {
	    checkTaskId(taskId);
	    setName(name);
	    setDescription(description);
	  }

	  public final String getTaskId() { return taskID; }

	  public final String getName() { return name; }

	  public void setName(String name) {
	    if (name == null || name.length() > 20) {
	      throw new IllegalArgumentException(
	          "Task name needs to be shorter than 20 characters and not null.");
	    } else {
	      this.name = name;
	    }
	  }

	  public final String getDescription() { return description; }

	  public void setDescription(String taskDescription) {
	    if (taskDescription == null || taskDescription.length() > 50) {
	      throw new IllegalArgumentException(
	          "Task ID needs to be shorter than 50 characters and not null.");
	    } else {
	      this.description = taskDescription;
	    }
	  }

	  public void checkTaskId(String taskId) {
	    if (taskId == null || taskId.length() > 10) {
	      throw new IllegalArgumentException(
	          "Task ID was null or longer than 10 characters");
	    } else {
	      this.taskID = taskId;
	    }
	}
}
