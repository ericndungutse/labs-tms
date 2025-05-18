package com.ndungutse.tms.repository;

import com.ndungutse.tms.Utils.DBUtil;
import com.ndungutse.tms.dot.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    // Add New Task
    public TaskDTO save(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        logger.info("Saving new task: title='{}', dueDate='{}', status='{}'",
                taskDTO.getTitle(), taskDTO.getDueDate(), taskDTO.getStatus());

        String sql = "INSERT INTO tasks (title, description, dueDate, status) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection(); // auto-commit is true by default
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, taskDTO.getTitle());
            stmt.setString(2, taskDTO.getDescription());
            stmt.setDate(3, Date.valueOf(taskDTO.getDueDate()));
            stmt.setString(4, taskDTO.getStatus());

            int affected = stmt.executeUpdate();

            if (affected == 0) {
                logger.error("Insert failed: no rows affected for task '{}'", taskDTO.getTitle());
                throw new SQLException("Insert failed, no rows affected.");
            }

            UUID generatedId = null;
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getObject(1, UUID.class);
                    logger.info("Task saved successfully with generated id: {}", generatedId);
                } else {
                    logger.warn("No generated key returned after inserting task '{}'", taskDTO.getTitle());
                }
            }

            return new TaskDTO(generatedId, taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate(), taskDTO.getStatus());
        }
    }

    // Get All Tasks
    public List<TaskDTO> findAll(String status, String dueDateSortDirection) throws SQLException, ClassNotFoundException {
        logger.info("Fetching all tasks with status filter '{}' and sort direction '{}'", status, dueDateSortDirection);

        List<TaskDTO> taskDTOS = new ArrayList<>();
        boolean hasFilter = status != null && !status.trim().isEmpty();

        String sql = "SELECT id, title, description, dueDate, status FROM tasks";

        if (hasFilter) {
            sql += " WHERE LOWER(status) = LOWER(?)";
        }

        String direction = null;

        if(dueDateSortDirection != null){
            if ("ASC".equalsIgnoreCase(dueDateSortDirection)) direction = "ASC";
            if ("DESC".equalsIgnoreCase(dueDateSortDirection)) direction = "DESC";
            sql += " ORDER BY dueDate " + direction;
        }

        logger.debug("Executing SQL: {}", sql);
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            if (hasFilter) {
                stmt.setString(1, status);
                logger.debug("Set status parameter: {}", status);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setTitle(rs.getString("title"));
                    taskDTO.setId(rs.getObject("id", UUID.class));
                    taskDTO.setDescription(rs.getString("description"));
                    taskDTO.setDueDate(rs.getDate("dueDate").toLocalDate());
                    taskDTO.setStatus(rs.getString("status"));
                    taskDTOS.add(taskDTO);
                }
            }
            logger.info("Fetched {} tasks", taskDTOS.size());
        } catch (SQLException e) {
            logger.error("Error fetching tasks: {}", e.getMessage());
            throw e;
        }
        return taskDTOS;
    }

    // Delete Task
    public boolean deleteById(UUID id) throws SQLException, ClassNotFoundException {
        logger.info("Deleting task with id: {}", id);

        String sql = "DELETE FROM tasks WHERE id = ?";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setObject(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Task with id '{}' deleted successfully", id);
                return true;
            } else {
                logger.warn("No task found with id '{}', nothing deleted", id);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error deleting task with id '{}': {}", id, e.getMessage());
            throw e;
        }
    }

    // TaskRepository.java
    public TaskDTO update(UUID id, TaskDTO taskDTO) throws Exception {
        String sql = "UPDATE tasks SET title = ?, description = ?, dueDate = ?, status = ? WHERE id = ? RETURNING *";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskDTO.getTitle());
            stmt.setString(2, taskDTO.getDescription());
            stmt.setObject(3, taskDTO.getDueDate());
            stmt.setString(4, taskDTO.getStatus());
            stmt.setObject(5, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TaskDTO(
                            (UUID) rs.getObject("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("dueDate").toLocalDate(),
                            rs.getString("status")
                    );
                } else {
                    throw new Exception("Task not found");
                }
            }
        }
    }

    public TaskDTO findById(UUID taskId )throws Exception, SQLException, ClassNotFoundException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks WHERE id = ?")) {
            stmt.setObject(1, taskId, java.sql.Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TaskDTO(
                            (UUID) rs.getObject("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("dueDate").toLocalDate(),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }
}
