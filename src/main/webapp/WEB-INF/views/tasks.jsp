<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Tasks</title>
</head>
<body>
<h1>Task List</h1>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<table border="1">
    <thead>
    <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Due Date</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${task.dueDate}</td>
            <td>${task.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
