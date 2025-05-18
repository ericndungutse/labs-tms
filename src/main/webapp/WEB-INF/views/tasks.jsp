<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>NovaTech Task Manager - Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800 font-sans">

<header class="bg-blue-600 text-white py-4 mb-6 shadow">
    <div class="max-w-6xl mx-auto px-4 flex justify-between items-center">
        <h1 class="text-xl font-semibold">NovaTech Task Manager</h1>
        <button class="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded text-sm">+ New Task</button>
    </div>
</header>

<div class="max-w-6xl mx-auto px-4">
    <c:if test="${not empty error}">
        <p class="text-red-500 mb-4">${error}</p>
    </c:if>

    <div class="bg-white shadow-md rounded-lg overflow-hidden">
        <table class="w-full table-auto">
            <thead class="bg-gray-50 text-left text-sm font-medium text-gray-700">
            <tr>
                <th class="px-4 py-3">Title</th>
                <th class="px-4 py-3">Description</th>
                <th class="px-4 py-3">Due Date</th>
                <th class="px-4 py-3">Status</th>
                <th class="px-4 py-3">Actions</th>
            </tr>
            </thead>
            <tbody class="text-sm divide-y divide-gray-200">
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td class="px-4 py-3">${task.title}</td>
                    <td class="px-4 py-3">${task.description}</td>
                    <td class="px-4 py-3">${task.dueDate}</td>
                    <td class="px-4 py-3">
                        <c:choose>
                            <c:when test="${task.status == 'Completed'}">
                                <span class="inline-block text-green-700 bg-green-100 px-2 py-1 rounded-full text-xs font-medium">Completed</span>
                            </c:when>
                            <c:otherwise>
                                <span class="inline-block text-yellow-700 bg-yellow-100 px-2 py-1 rounded-full text-xs font-medium">Pending</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="px-4 py-3 flex gap-2">
                        <form method="get" action="edit-task">
                            <input type="hidden" name="id" value="${task.id}" />
                            <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white text-xs px-3 py-1 rounded">Edit</button>
                        </form>
                        <form method="post" action="delete-task" onsubmit="return confirm('Are you sure you want to delete this task?');">
                            <input type="hidden" name="id" value="${task.id}" />
                            <button type="submit" class="bg-red-500 hover:bg-red-600 text-white text-xs px-3 py-1 rounded">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
