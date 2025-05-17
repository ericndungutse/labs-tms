package com.ndungutse.tms.service;

import com.ndungutse.tms.dot.TaskDTO;
import com.ndungutse.tms.repository.TaskRepository;

import java.util.List;

public class TaskService {
    private final TaskRepository taskRepository;

    // Opt dependency injection. Support unit testing via mocking and make it so service can work with any repository.
    // It allows you to pass a mock or fake version of TaskRepository when testing TaskService, without depending on the real database.
    // Implement OpenCLose Principle
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO createTask(TaskDTO taskDTO) throws Exception {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return taskRepository.save(taskDTO);
    }

    // Get All Tasks
    public List<TaskDTO> getAllTasks() throws Exception {
        return taskRepository.findAll();
    }
}
