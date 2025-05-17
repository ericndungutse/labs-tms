package service;

import model.Task;
import repository.TaskRepository;

public class TaskService {
    private final TaskRepository taskRepository;

    // Opt dependency injection. Support unit testing via mocking and make it so service can work with any repository.
    // It allows you to pass a mock or fake version of TaskRepository when testing TaskService, without depending on the real database.
    // Implement OpenCLose Principle
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) throws Exception {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        return taskRepository.save(task);
    }
}
