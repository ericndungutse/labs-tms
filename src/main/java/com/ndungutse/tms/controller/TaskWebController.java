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
import java.util.List;

@WebServlet(name = "TaskWeb", urlPatterns = {"/"})
public class TaskWebController extends HttpServlet {
    private final TaskService taskService = new TaskService(new TaskRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<TaskDTO> tasks = taskService.getAllTasks(null, null);
            System.out.println(tasks);
            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Unable to load tasks: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
