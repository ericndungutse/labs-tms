package com.ndungutse.tms.service;

import com.ndungutse.tms.dot.TaskDTO;
import com.ndungutse.tms.exception.InvalidTaskException;
import com.ndungutse.tms.exception.MissingParameterException;
import com.ndungutse.tms.exception.TaskNotFoundException;
import com.ndungutse.tms.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create Task
    public TaskDTO createTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isBlank()) {
            logger.warn("Attempted to create task with empty title");
            throw new InvalidTaskException("Title cannot be empty");
        }

        if(taskDTO.getDueDate() == null) {
            logger.warn("Attempted to create task with empty due date");
            throw new InvalidTaskException("Due date cannot be empty");
        }

        if(taskDTO.getStatus() == null || taskDTO.getStatus().isBlank() || !taskDTO.getStatus().equalsIgnoreCase("completed") && !taskDTO.getStatus().equalsIgnoreCase("pending")) {
            logger.warn("Attempted to create task with empty status");
            throw new InvalidTaskException("Status cannot be empty and can only be completed or pending");
        }

        if(taskDTO.getDescription() == null || taskDTO.getDescription().isBlank()) {
            logger.warn("Attempted to create task with empty description");
            throw new InvalidTaskException("Description cannot be empty");
        }

        TaskDTO created = taskRepository.save(taskDTO);
        logger.info("Task created with ID: {}", created.getId());
        return created;
    }

    // Get All Tasks
    public List<TaskDTO> getAllTasks(String status, String dueDateSortDirection) throws Exception {
        logger.debug("Fetching tasks with status={} and dueDateSortDirection={}", status, dueDateSortDirection);
        return taskRepository.findAll(status, dueDateSortDirection);
    }

    // Delete Task
    public boolean deleteTaskById(UUID id) throws Exception {
        logger.info("Deleting task with ID: {}", id);

        boolean deleted = taskRepository.deleteById(id);
        if (deleted) {
            logger.info("Task with ID {} deleted successfully", id);
        } else {
            logger.warn("No task found with ID {} to delete", id);
            throw new TaskNotFoundException("No task found with ID " + id + " to delete");
        }
        return true;
    }

    // TaskService.java
    public TaskDTO updateTask(UUID id, TaskDTO taskDTO) throws Exception {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isBlank()) {
            logger.warn("Attempted to update task with empty title");
            throw new InvalidTaskException("Title cannot be empty");
        }

        if(taskDTO.getDueDate() == null) {
            logger.warn("Attempted to update task with empty due date");
            throw new InvalidTaskException("Due date cannot be empty");
        }

        if(taskDTO.getStatus() == null || taskDTO.getStatus().isBlank() || !taskDTO.getStatus().equalsIgnoreCase("completed") && !taskDTO.getStatus().equalsIgnoreCase("pending")) {
            logger.warn("Attempted to update task with empty status");
            throw new InvalidTaskException("Status cannot be empty and can only be completed or pending");
        }

        if(taskDTO.getDescription() == null || taskDTO.getDescription().isBlank()) {
            logger.warn("Attempted to update task with empty description");
            throw new InvalidTaskException("Description cannot be empty");
        }

        TaskDTO existingTask = taskRepository.findById(id);
        if (existingTask == null) {
            logger.warn("Task not found for ID: {}", id);
            throw new TaskNotFoundException("Task not found for ID " + id);
        }
        return taskRepository.update(id, taskDTO);
    }


    public TaskDTO getTaskById(UUID taskId) {
        try {
            return taskRepository.findById(taskId);
        } catch (Exception e) {
            logger.error("Error fetching task by ID: {}", taskId, e);
            return null;
        }
    }
}
