package com.ndungutse.tms.controller;

import com.ndungutse.tms.dot.TaskDTO;
import com.ndungutse.tms.repository.TaskRepository;
import com.ndungutse.tms.service.TaskService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet(name = "UpdateTaskServlet", urlPatterns = {"/edit-task"})
public class UpdateTaskServlet extends HttpServlet {
    private final TaskService taskService = new TaskService(new TaskRepository());
    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskServlet.class);
    @Override
    public void init() {
        logger.info("UpdateTaskServlet initialized");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {

                UUID taskId =  UUID.fromString(idParam);
                TaskDTO task = taskService.getTaskById(taskId);
                if (task != null) {
                    request.setAttribute("task", task);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/edit-task.jsp");
                    dispatcher.forward(request, response);
                } else {
                    logger.warn("Task not found for ID: {}", taskId);
                    request.setAttribute("error", "Task not found.");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
        } else {
            logger.warn("Missing task ID in request");
            request.setAttribute("error", "=Missing task ID.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
            String status = request.getParameter("status");

            TaskDTO updatedTask = new TaskDTO(id, title, description, dueDate, status);
            TaskDTO success = taskService.updateTask(id, updatedTask);

            if (success != null && success.getId() != null && success.getId().equals(id)) {
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                request.setAttribute("error", "Failed to update task");
                request.setAttribute("task", updatedTask);
                request.getRequestDispatcher("/WEB-INF/views/edit-task.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error updating task", e);
            request.setAttribute("error", "An error occurred while updating the task");
            request.getRequestDispatcher("/WEB-INF/views/edit-task.jsp").forward(request, response);
        }
    }
}
