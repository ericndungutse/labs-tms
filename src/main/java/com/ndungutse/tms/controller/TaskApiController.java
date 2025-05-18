package com.ndungutse.tms.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.ndungutse.tms.Utils.TaskJsonMapper;
import com.ndungutse.tms.dot.AllTasksResponse;
import com.ndungutse.tms.dot.TaskDTO;
import com.ndungutse.tms.exception.InvalidTaskException;
import com.ndungutse.tms.exception.MissingParameterException;
import com.ndungutse.tms.exception.TaskNotFoundException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.ndungutse.tms.repository.TaskRepository;
import com.ndungutse.tms.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "TaskApi", value = "/api/v1/tasks")
public class TaskApiController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TaskApiController.class);
    private final TaskService taskService = new TaskService(new TaskRepository());

    public void init() {
        logger.info("TaskApiController initialized");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("status");
        String dueDateSortDirection = request.getParameter("dueDateSortDirection");
        logger.info("Received GET request with status={} and dueDateSortDirection={}", status, dueDateSortDirection);

        try {
            List<TaskDTO> taskDTOS = taskService.getAllTasks(status, dueDateSortDirection);
            AllTasksResponse responseObj = new AllTasksResponse(new AllTasksResponse.Data(taskDTOS), "success");
            String jsonResponse = TaskJsonMapper.toJsonFromResponse(responseObj);

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
            logger.info("GET request processed successfully, returned {} tasks", taskDTOS.size());
        } catch (Exception e) {
            response.setContentType("application/json");
            logger.error("Error processing GET request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": Internal server error. Please try again later. \"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            logger.info("Received POST request with body: {}", json);

            TaskDTO newTaskDTO = taskService.createTask(TaskJsonMapper.fromJson(json));

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(TaskJsonMapper.toJson(newTaskDTO));
            logger.info("Task created successfully with ID: {}", newTaskDTO.getId());

        }catch (InvalidTaskException e) {
            response.setContentType("application/json");
            logger.warn("Invalid task", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (SQLException | ClassNotFoundException e) {
            response.setContentType("application/json");
            logger.error("Error processing POST request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error. Please try again later.\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String idParam = request.getParameter("id");

        // Extract ID from path info: /api/tasks?id=
        if (idParam == null || idParam.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Task ID is required in the URL\"}");
            return;
        }

        UUID id;
        try {
            id = UUID.fromString(idParam);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid UUID format\"}");
            return;
        }

        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();
            TaskDTO dtoFromBody = TaskJsonMapper.fromJson(json);

            TaskDTO updatedTask = taskService.updateTask(id, dtoFromBody);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(TaskJsonMapper.toJson(updatedTask));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        logger.info("Received DELETE request for id={}", idParam);

        if (idParam == null || idParam.isEmpty()) {
            response.setContentType("application/json");
            logger.warn("Id of task is required in the URL");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\" Task ID is required in the URL\"}");
            return;
        }

        try {
            UUID taskId = UUID.fromString(idParam);
            boolean deleted = taskService.deleteTaskById(taskId);

            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Task with ID {} deleted successfully", taskId);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Task not found\"}");
                logger.warn("Task with ID {} not found for deletion", taskId);
            }
        }catch(TaskNotFoundException e){
            response.setContentType("application/json");
            logger.info("Task with ID {} not found for deletion", idParam);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\":\""+ e.getMessage() +"\"}");

        }catch (Exception e) {
            response.setContentType("application/json");
            logger.error("Error processing DELETE request for id: " + idParam, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\" Internal server error. Please try again later.\"}");
        }
    }

    public void destroy() {
        logger.info("TaskApiController destroyed");
    }
}
