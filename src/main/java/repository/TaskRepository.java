package repository;

import Utils.DBUtil;
import model.Task;

import java.sql.*;
import java.util.UUID;

public class TaskRepository {
    public Task save(Task task) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tasks (title, description, due_date, status) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            conn.setAutoCommit(false);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getStatus());

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
            return new Task(generatedId, task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus());
        }
    }
}
