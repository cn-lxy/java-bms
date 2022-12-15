<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h3>似乎出了点问题? <%= request.getAttribute("msg") %></h3>
</body>
</html>
