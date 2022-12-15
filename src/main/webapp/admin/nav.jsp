<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="admin" class="com.lxy.webbms.models.Admin" />
<jsp:setProperty name="admin" property="id" />
<%
    if (admin.initAdminByID() != admin.INIT_SUCCESS) {
        response.sendRedirect("/admin/login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>导航</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/nav.css">
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/icon/admin.png">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="content">
            <h2>01</h2>
            <h3>图书管理</h3>
            <p></p>
            <a href="<c:url value="/admin/manager/book/index.jsp?id=1"/>">管理</a>
        </div>
    </div>
    <div class="card">
        <div class="content">
            <h2>02</h2>
            <h3>借阅管理</h3>
            <p></p>
            <a href="<c:url value="/admin/manager/borrow/index.jsp?id=1"/>">管理</a>
        </div>
    </div>
    <div class="card">
        <div class="content">
            <h2>03</h2>
            <h3>用户管理</h3>
            <p></p>
            <a href="<c:url value="/admin/manager/user/index.jsp?id=1"/>">管理</a>
        </div>
    </div>
</div>
</body>
</body>
</html>