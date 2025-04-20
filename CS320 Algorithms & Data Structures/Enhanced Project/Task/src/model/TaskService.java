package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskService {
    private final List<Task> taskList = new ArrayList();

    public TaskService() {
    }

    private String newUniqueId() {
        return UUID.randomUUID().toString().substring(0, Math.min(this.toString().length(), 10));
    }

    private Task searchForTask(String id) throws Exception {
        for(int index = 0; index < this.taskList.size(); ++index) {
            if (id.equals(((Task)this.taskList.get(index)).getTaskId())) {
                return (Task)this.taskList.get(index);
            }
        }

        throw new Exception("The Task does not exist!");
    }

    public void newTask() {
        Task task = new Task(this.newUniqueId());
        this.taskList.add(task);
    }

    public void newTask(String name) {
        Task task = new Task(this.newUniqueId(), name);
        this.taskList.add(task);
    }

    public void newTask(String name, String description) {
        Task task = new Task(this.newUniqueId(), name, description);
        this.taskList.add(task);
    }

    public void deleteTask(String id) throws Exception {
        this.taskList.remove(this.searchForTask(id));
    }

    public void updateName(String id, String name) throws Exception {
        this.searchForTask(id).setName(name);
    }

    public void updateDescription(String id, String description) throws Exception {
        this.searchForTask(id).setDescription(description);
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }
}
