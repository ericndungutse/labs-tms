package controller;

import java.io.*;
import java.sql.*;
import java.util.UUID;

import Utils.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Task;

@WebServlet(name = "TaskApi", value = "/api/v1/tasks")
public class TaskApi extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World! again";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try(
                Connection dbConection = DBUtil.getConnection();
                ) {

            System.out.println("************************************" + request.getMethod());

        response.getWriter().write("{ \"message\": \"Fetching all tasks\" }");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Task taskJsonToTaskMapper(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(json, Task.class);
    }

    private String taskObjToJsonMapper(Task task) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(task);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String insertTaskSQL = "INSERT INTO tasks (title, description, due_date, status) VALUES (?, ?, ?, ?)";
        try (BufferedReader reader = request.getReader();
             Connection dbConnection = DBUtil.getConnection();
             PreparedStatement stm = dbConnection.prepareStatement(insertTaskSQL, Statement.RETURN_GENERATED_KEYS);
        ) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();

            Task task = taskJsonToTaskMapper(json);

            dbConnection.setAutoCommit(false);

            stm.setString(1, task.getTitle());
            stm.setString(2, task.getDescription());
            stm.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
            stm.setString(4, task.getStatus());
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }

            // Get the id of newly created task
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    UUID id = generatedKeys.getObject(1, UUID.class);
                    task.setId(UUID.fromString(id.toString()));
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }

            // Commit Transaction
            dbConnection.commit();

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(taskObjToJsonMapper(task));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{ \"message\": \"Task updated\" }");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{ \"message\": \"Task deleted\" }");
    }

    public void destroy() {
    }
}