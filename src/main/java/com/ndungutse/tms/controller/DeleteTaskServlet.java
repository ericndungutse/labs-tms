package com.ndungutse.tms.controller;

import com.ndungutse.tms.repository.TaskRepository;
import com.ndungutse.tms.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "DeleteTaskServlet", urlPatterns = {"/delete-task"})
public class DeleteTaskServlet extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        taskService = new TaskService(new TaskRepository());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "Missing task ID.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        try {
            UUID id = UUID.fromString(idParam);
            boolean success = taskService.deleteTaskById(id);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                request.setAttribute("error", "Task not found.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid UUID format.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
