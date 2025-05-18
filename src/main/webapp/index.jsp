<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello here Servlet</a>

<c:forEach var="task" items="${tasks}">
    <tr>
        <td>${task.title}</td>
        <td>${task.description}</td>
        <td>${task.dueDate}</td>
        <td>${task.status}</td>
    </tr>
</c:forEach>
</body>
</html>