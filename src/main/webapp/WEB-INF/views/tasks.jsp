<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Task Manager</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800 font-sans">

<header class="bg-blue-600 text-white py-4 mb-6 shadow">
    <div class="max-w-6xl mx-auto px-4 flex justify-between items-center">
        <h1 class="text-xl font-semibold">Task Manager</h1>
        <a href="${pageContext.request.contextPath}/tasks/new"
           class="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded text-sm">+ New Task</a>
    </div>
</header>

<div class="max-w-6xl mx-auto px-4">

    <!-- Filters -->
    <div class="mb-4 flex gap-4 items-center">
        <label for="statusFilter" class="font-medium">Status:</label>
        <select id="statusFilter" name="status"
                class="border border-gray-300 rounded px-2 py-1"
                onchange="applyFilters()">
            <option value="" ${empty param.status ? 'selected' : ''}>All</option>
            <option value="pending" ${param.status == 'pending' ? 'selected' : ''}>Pending</option>
            <option value="completed" ${param.status == 'completed' ? 'selected' : ''}>Completed</option>
        </select>

        <label for="dueDateSort" class="font-medium">Sort by Due Date:</label>
        <select id="dueDateSort" name="dueDateSortDirection"
                class="border border-gray-300 rounded px-2 py-1"
                onchange="applyFilters()">
            <option value="" ${empty param.dueDateSortDirection ? 'selected' : ''}>None</option>
            <option value="ASC" ${param.dueDateSortDirection == 'ASC' ? 'selected' : ''}>Ascending</option>
            <option value="DESC" ${param.dueDateSortDirection == 'DESC' ? 'selected' : ''}>Descending</option>
        </select>
    </div>

    <!-- Tasks Table -->
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
                    <td class="px-4 py-3 max-w-[200px] truncate overflow-hidden whitespace-nowrap">${task.description}</td>
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
                    <td class="px-4 py-3 flex gap-2 flex-wrap">
                        <a href="${pageContext.request.contextPath}/task-details?id=${task.id}" class="bg-gray-500 hover:bg-gray-600 text-white text-xs px-3 py-1 rounded">Details</a>
                        <a href="${pageContext.request.contextPath}/edit-task?id=${task.id}" class="bg-blue-500 hover:bg-blue-600 text-white text-xs px-3 py-1 rounded">Edit</a>
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

<script>
    function applyFilters() {
        const status = document.getElementById('statusFilter').value;
        const dueDateSortDirection = document.getElementById('dueDateSort').value;

        const query = new URLSearchParams(window.location.search);

        if(status)
            query.set('status', status);
        else query.delete('status');

        if(dueDateSortDirection)
            query.set('dueDateSortDirection', dueDateSortDirection);
        else query.delete('dueDateSortDirection');

        window.location.href =  '?' + query.toString();
    }
</script>

</body>
</html>
