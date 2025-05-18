<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Task Details - NovaTech Task Manager</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800 font-sans">

<header class="bg-blue-600 text-white py-4 mb-6 shadow">
    <div class="max-w-4xl mx-auto px-4 flex justify-between items-center">
        <h1 class="text-xl font-semibold">NovaTech Task Manager</h1>
        <a href="${pageContext.request.contextPath}/tasks" class="text-sm text-white hover:underline">← Back to Tasks</a>
    </div>
</header>

<div class="max-w-4xl mx-auto px-4">
    <div class="bg-white rounded-lg shadow-md p-6">
        <div class="flex justify-between items-start mb-6 border-b pb-4">
            <div>
                <h2 class="text-2xl font-semibold text-gray-900 mb-2">${task.title}</h2>
            </div>
            <c:choose>
                <c:when test="${task.status == 'Completed'}">
                    <span class="inline-block bg-green-100 text-green-700 text-xs font-medium px-3 py-1 rounded-full">Completed</span>
                </c:when>
                <c:otherwise>
                    <span class="inline-block bg-yellow-100 text-yellow-700 text-xs font-medium px-3 py-1 rounded-full">Pending</span>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="mb-6">
            <h3 class="text-sm font-semibold text-gray-500 mb-1">Description</h3>
            <p class="text-base text-gray-700 leading-relaxed">${task.description}</p>
        </div>

        <div>
            <h3 class="text-sm font-semibold text-gray-500 mb-1">Due Date</h3>
            <p class="text-base text-gray-700">${task.dueDate}</p>
        </div>

        <div class="flex justify-end gap-2 mt-6 pt-4 border-t">
            <a href="${pageContext.request.contextPath}/tasks" class="bg-gray-200 text-gray-700 text-sm px-4 py-2 rounded hover:bg-gray-300">Back</a>

            <form method="post" action="${pageContext.request.contextPath}/delete-task" onsubmit="return confirm('Are you sure you want to delete this task?');">
                <input type="hidden" name="id" value="${task.id}" />
                <button type="submit" class="bg-red-500 hover:bg-red-600 text-white text-sm px-4 py-2 rounded">Delete</button>
            </form>

            <a href="${pageContext.request.contextPath}/edit-task?id=${task.id}" class="bg-blue-500 hover:bg-blue-600 text-white text-sm px-4 py-2 rounded">Edit</a>
        </div>
    </div>
</div>

</body>
</html>
