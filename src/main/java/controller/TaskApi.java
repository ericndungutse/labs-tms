package com.ndungutse.task;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/api/tasks")
public class TaskApi extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World! again";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{ \"message\": \"Fetching all tasks\" }");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{ \"message\": \"Task created\" }");
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