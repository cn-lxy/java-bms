<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css">
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/icon/login.png">
</head>
<body>
<div class="box">
    <div class="card-login">
        <div class="login-title">
                <span class="icon">
                    <svg t="1649422507180" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2225" width="48" height="48"><path d="M506.075809 546.976206c-145.260076 0-263.436846-118.16774-263.436846-263.418785 0-145.260076 118.17677-263.436846 263.436846-263.436846 145.260076 0 263.436846 118.16774 263.436846 263.436846C769.512655 428.799436 651.335885 546.976206 506.075809 546.976206zM506.075809 76.996419c-113.896181 0-206.561002 92.664821-206.561002 206.561002S392.179628 490.100362 506.075809 490.100362c113.905212 0 206.561002-92.646759 206.561002-206.54294S619.981021 76.996419 506.075809 76.996419zM514.754388 621.191146c-250.902125 0-455.024817 174.88103-455.024817 389.840656l28.437922 0c0-199.607302 190.991939-361.411765 426.586895-361.411765s426.586895 161.804462 426.586895 361.411765l20.156698 0 8.281224 0C969.788235 796.072176 765.647482 621.191146 514.754388 621.191146zM514.754388 678.057959c219.547262 0 398.148973 149.360049 398.148973 332.964812l28.437922 0c0-199.607302-190.991939-361.411765-426.586895-361.411765S88.167493 811.4245 88.167493 1011.031802l28.437922 0C116.605415 827.427039 295.207126 678.057959 514.754388 678.057959z" p-id="2226" fill="#8aff80"></path></svg>
                </span>
            <span style="display: inline-block;padding-top: 4px;">
                    用户登录
                </span>
        </div>
        <form method="post" action="<c:url value="/user/api/login"/>" autocomplete="off">
            <div class="form-input">
                <label for="user">账号&nbsp;&nbsp;<input type="text" name="username" id="user" placeholder="请输入账号" autocomplete="off"></label>
                <label for="password">密码&nbsp;&nbsp;<input type="password" name="password" id="password" placeholder="请输入密码"></label>
            </div>
            <div class="func">
                <input type="submit" value="登录">
                <input type="reset" value="重置">
            </div>
            <div class="func">
                <span>没有账号?&nbsp;</span>
                <a href="<c:url value="/user/register.jsp"/>">去注册</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
