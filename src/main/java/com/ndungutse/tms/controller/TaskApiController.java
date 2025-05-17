package com.ndungutse.tms.controller;

import java.io.*;
import java.util.List;

import com.ndungutse.tms.Utils.TaskJsonMapper;
import com.ndungutse.tms.dot.AllTasksResponse;
import com.ndungutse.tms.dot.TaskDTO;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.ndungutse.tms.repository.TaskRepository;
import com.ndungutse.tms.service.TaskService;

@WebServlet(name = "TaskApi", value = "/api/v1/tasks")
public class TaskApiController extends HttpServlet {
 private final TaskService taskService = new TaskService(new TaskRepository());

    public void init() {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Get optional status parameter from query string
            String status = request.getParameter("status") != null ? request.getParameter("status") : null;
            List<TaskDTO> taskDTOS = taskService.getAllTasks(status);

            AllTasksResponse responseObj = new AllTasksResponse(new AllTasksResponse.Data(taskDTOS), "success");
            String jsonResponse = TaskJsonMapper.toJsonFromResponse(responseObj);

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
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
            TaskDTO newTaskDTO = taskService.createTask(TaskJsonMapper.fromJson(json));

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(TaskJsonMapper.toJson(newTaskDTO));

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