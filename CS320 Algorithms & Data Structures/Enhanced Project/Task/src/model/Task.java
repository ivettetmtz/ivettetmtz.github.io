package model;

public class Task {
    private String taskID;
    private String name;
    private String description;

    public Task() {
        this.taskID = "INITIAL";
        this.name = "INITIAL";
        this.description = "INITIAL DESCRIPTION";
    }

    public Task(String taskId) {
        this.checkTaskId(taskId);
        this.name = "INITIAL";
        this.description = "INITIAL DESCRIPTION";
    }

    public Task(String taskId, String name) {
        this.checkTaskId(taskId);
        this.setName(name);
        this.description = "INITIAL DESCRIPTION";
    }

    public Task(String taskId, String name, String description) {
        this.checkTaskId(taskId);
        this.setName(name);
        this.setDescription(description);
    }

    public final String getTaskId() {
        return this.taskID;
    }

    public final String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name != null && name.length() <= 20) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Task name needs to be shorter than 20 characters and not null.");
        }
    }

    public final String getDescription() {
        return this.description;
    }

    public void setDescription(String taskDescription) {
        if (taskDescription != null && taskDescription.length() <= 50) {
            this.description = taskDescription;
        } else {
            throw new IllegalArgumentException("Task ID needs to be shorter than 50 characters and not null.");
        }
    }

    public void checkTaskId(String taskId) {
        if (taskId != null && taskId.length() <= 10) {
            this.taskID = taskId;
        } else {
            throw new IllegalArgumentException("Task ID was null or longer than 10 characters");
        }
    }
}