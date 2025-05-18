package com.ndungutse.tms.service;

import com.ndungutse.tms.dot.TaskDTO;
import com.ndungutse.tms.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO createTask(TaskDTO taskDTO) throws Exception {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isBlank()) {
            logger.warn("Attempted to create task with empty title");
            throw new IllegalArgumentException("Title cannot be empty");
        }
        TaskDTO created = taskRepository.save(taskDTO);
        logger.info("Task created with ID: {}", created.getId());
        return created;
    }

    public List<TaskDTO> getAllTasks(String status, String dueDateSortDirection) throws Exception {
        logger.debug("Fetching tasks with status={} and dueDateSortDirection={}", status, dueDateSortDirection);
        return taskRepository.findAll(status, dueDateSortDirection);
    }

    public boolean deleteTaskById(UUID id) throws Exception {
        logger.info("Deleting task with ID: {}", id);
        boolean deleted = taskRepository.deleteById(id);
        if (deleted) {
            logger.info("Task with ID {} deleted successfully", id);
        } else {
            logger.warn("No task found with ID {} to delete", id);
        }
        return deleted;
    }

    // TaskService.java
    public TaskDTO updateTask(UUID id, TaskDTO taskDTO) throws Exception {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return taskRepository.update(id, taskDTO);
    }


}
