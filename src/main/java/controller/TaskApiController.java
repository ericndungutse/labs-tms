package controller;

import java.io.*;
import java.sql.*;
import java.util.UUID;

import Utils.DBUtil;
import Utils.TaskJsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Task;
import repository.TaskRepository;
import service.TaskService;

@WebServlet(name = "TaskApi", value = "/api/v1/tasks")
public class TaskApiController extends HttpServlet {
 private final TaskService taskService = new TaskService(new TaskRepository());

    public void init() {}

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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        try(BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            Task newTask = taskService.createTask(TaskJsonMapper.fromJson(json));

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(TaskJsonMapper.toJson(newTask));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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