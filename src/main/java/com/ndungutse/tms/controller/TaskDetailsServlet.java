package com.ndungutse.tms.controller;

import com.ndungutse.tms.dot.TaskDTO;
import com.ndungutse.tms.repository.TaskRepository;
import com.ndungutse.tms.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "TaskDetailsServlet", urlPatterns = {"/task-details"})
public class TaskDetailsServlet extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        taskService = new TaskService(new TaskRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException     {

        String taskIdParam = request.getParameter("id");

        if (taskIdParam == null || taskIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        try {
            UUID taskId = UUID.fromString(taskIdParam);
            TaskDTO task = taskService.getTaskById(taskId);

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
            } else {
                request.setAttribute("task", task);
                request.getRequestDispatcher("/WEB-INF/views/task-details.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/tasks");
        }
    }
}
