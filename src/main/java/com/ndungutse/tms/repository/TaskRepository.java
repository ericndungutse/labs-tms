package com.ndungutse.tms.repository;

import com.ndungutse.tms.Utils.DBUtil;
import com.ndungutse.tms.dot.TaskDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {
    // Add New Task
    public TaskDTO save(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tasks (title, description, dueDate, status) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            conn.setAutoCommit(false);
            stmt.setString(1, taskDTO.getTitle());
            stmt.setString(2, taskDTO.getDescription());
            stmt.setDate(3, Date.valueOf(taskDTO.getDueDate()));
            stmt.setString(4, taskDTO.getStatus());

            int affected = stmt.executeUpdate();

            if (affected == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            UUID generatedId = null;
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getObject(1, UUID.class);
                }
            }

            conn.commit();
            return new TaskDTO(generatedId, taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate(), taskDTO.getStatus());
        }
    }

    // Get All Tasks
    public List<TaskDTO> findAll(String status) throws SQLException, ClassNotFoundException {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        boolean hasFilter = status != null && !status.isEmpty();

        String sql = "SELECT id, title, description, dueDate, status FROM tasks";

        if (status != null && !status.isEmpty()) {
            sql += " WHERE LOWER(status) = LOWER(?)";
        }

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            if (hasFilter) {
                stmt.setString(1, status);
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
        }
        return taskDTOS;
    }
}
