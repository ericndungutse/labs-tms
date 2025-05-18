<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ndungutse.tms.dot.TaskDTO" %>
<%
    TaskDTO task = (TaskDTO) request.getAttribute("task");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title> Task Manager - Edit Task</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800 font-sans">

<header class="bg-blue-600 text-white py-4 mb-6 shadow">
    <div class="max-w-6xl mx-auto px-4 flex justify-between items-center">
        <h1 class="text-xl font-semibold"> Task Manager</h1>
        <a href="/" class="text-white text-sm hover:underline">&larr; Back to Tasks</a>
    </div>
</header>

<div class="max-w-2xl mx-auto bg-white shadow-md rounded-lg p-8">
    <h2 class="text-xl font-semibold text-gray-800 mb-6">Edit Task</h2>

    <form action="<%= request.getContextPath() %>/edit-task" method="post">
        <input type="hidden" name="id" value="<%= task.getId() %>">

        <div class="mb-4">
            <label for="title" class="block text-gray-700 font-medium mb-2">Task Title</label>
            <input type="text" id="title" name="title"
                   value="<%= task.getTitle() %>"
                   class="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400" required>
        </div>

        <div class="mb-4">
            <label for="description" class="block text-gray-700 font-medium mb-2">Description</label>
            <textarea id="description" name="description"
                      class="w-full border border-gray-300 rounded px-4 py-2 h-28 resize-y focus:outline-none focus:ring-2 focus:ring-blue-400" required><%= task.getDescription() %></textarea>
        </div>

        <div class="mb-4">
            <label for="dueDate" class="block text-gray-700 font-medium mb-2">Due Date</label>
            <input type="date" id="dueDate" name="dueDate"
                   value="<%= task.getDueDate() %>"
                   class="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400" required>
        </div>

        <div class="mb-6">
            <label for="status" class="block text-gray-700 font-medium mb-2">Status</label>
            <select id="status" name="status"
                    class="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400" required>
                <option value="Pending" <%= "Pending".equals(task.getStatus()) ? "selected" : "" %>>Pending</option>
                <option value="Completed" <%= "Completed".equals(task.getStatus()) ? "selected" : "" %>>Completed</option>
            </select>
        </div>

        <div class="flex justify-end gap-3">
            <a href="/" class="bg-gray-200 text-gray-700 px-4 py-2 rounded hover:bg-gray-300">Cancel</a>
            <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">Update Task</button>
        </div>
    </form>
</div>

</body>
</html>
