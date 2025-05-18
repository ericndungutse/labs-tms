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
import java.time.LocalDate;

@WebServlet(name = "AddTaskServlet", urlPatterns = {"/tasks/new"})
public class AddTaskServlet extends HttpServlet {
    private final TaskService taskService = new TaskService(new TaskRepository());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the JSP form
        request.getRequestDispatcher("/WEB-INF/views/add-task.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        String status = request.getParameter("status");

        TaskDTO task = new TaskDTO();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(LocalDate.parse(dueDate));
        task.setStatus(status);

        try {
            taskService.createTask(task);

            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to save task: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/add-task.jsp").forward(request, response);
        }
    }

}
