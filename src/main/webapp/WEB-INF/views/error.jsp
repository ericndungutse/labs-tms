<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title> Task Manager - Error</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 text-gray-800 font-sans">

<header class="bg-blue-600 text-white py-4 mb-6 shadow">
    <div class="max-w-6xl mx-auto px-4 flex justify-between items-center">
        <h1 class="text-xl font-semibold"> Task Manager</h1>
        <a href="${pageContext.request.contextPath}/"
           class="bg-white text-blue-600 hover:bg-gray-100 border border-blue-600 py-2 px-4 rounded text-sm">
            Back to /
        </a>
    </div>
</header>

<div class="max-w-3xl mx-auto px-4">
    <div class="bg-white shadow-md rounded-lg p-6 text-center">
        <h2 class="text-2xl font-bold text-red-600 mb-4">Oops! Something went wrong.</h2>
        <p class="text-red-500 mb-6 text-lg font-medium">${error}</p>
        <a href="${pageContext.request.contextPath}/"
           class="inline-block bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded text-sm">
            Return to Task List
        </a>
    </div>
</div>

</body>
</html>
