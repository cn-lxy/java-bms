<%@ page import="java.util.List" %>
<%@ page import="com.lxy.webbms.models.Borrow" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="mb" class="com.lxy.webbms.models.ManagerBorrow" />
<jsp:useBean id="admin" class="com.lxy.webbms.models.Admin" />
<jsp:setProperty name="admin" property="id" />
<%
    if (admin.initAdminByID() != admin.INIT_SUCCESS)
        response.sendRedirect("/admin/login.jsp");
    // 分页处理
    int pg = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
    List<Borrow> borrowList;
    String searchType = request.getParameter("searchType");
    String data = request.getParameter("data");
    if (searchType != null) {
        switch (searchType) {
            case "back":
                switch (data) {
                    case "no":
                        borrowList = mb.filterBorrows(pg, mb.NO_BACK);
                        break;
                    case "yet":
                        borrowList = mb.filterBorrows(pg, mb.YET_BACK);
                        break;
                    default:
                        borrowList = mb.getAllBorrows(pg);
                }
                break;
            case "username":
                borrowList = mb.filterBorrows(pg, mb.USER_NAME, data);
                break;
            case "bookname":
                borrowList = mb.filterBorrows(pg, mb.BOOK_NAME, data);
                break;
            default:
                borrowList = mb.getAllBorrows(pg);
        }
    } else borrowList = mb.getAllBorrows(pg);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>借阅管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home.css">
    <style>
        .mypaeg {
            display: flex;
            justify-content: center;
        }
        .mypaeg > button {
            margin-right: 10px;
            margin-left: 10px;
            background-color: #22212c;
            border: 2px solid #2c2536;
            color: #7d8bd0;
            border-radius: 8px;
        }
        .mypaeg > button:hover {
            background-color: #7d8bd0;
            color: #f2f2f2;
        }
    </style>
</head>
<body>
<header>
    <div class="navbar">
        <div class="navbar-container">
            <a href="<c:url value="/admin/manager/borrow?id=${admin.getId()}"/>">
                <span class="nav-logo"></span>
                <span class="nav-info">借阅管理页面</span>
            </a>
            <span>欢迎您!&nbsp;&nbsp;<a href="#"><%=admin.getAccount()%></a></span>
        </div>
    </div>
</header>
<div>
    <main>
        <div class="main-container">
            <div class="row">
                <%--TODO 左侧board：显示其他两个入口 --%>
                <div class="col item-1">
                    <div class="card"><a href="../user/index.jsp?id=<%= admin.getId() %>">用户管理</a></div>
                    <div class="card"><a href="../book/index.jsp?id=<%= admin.getId() %>">图书管理</a></div>
                </div>
                <div class="col item-2">
                    <div class="card">
                        <table class="table" width="60%">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">借阅用户</th>
                                <th scope="col">借阅书籍</th>
                                <th scope="col">借阅天数</th>
                                <th scope="col">借阅时间</th>
                                <th scope="col">归还时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%! int i = 1; %>
                            <% i = 1; %>

                            <c:forEach items="<%= borrowList %>" var="borrow">
                                <tr>
                                    <th scope="row"><%= i %></th>
                                    <td>${borrow.getUserName()}</td>
                                    <td>${borrow.getBookName()}</td>
                                    <td>${borrow.getDays()}</td>
                                    <td>${borrow.getBorrowDate()}</td>
                                    <td>${borrow.getBackDate() == null ? "未归还" : borrow.getBackDate()}</td>
                                    <td><a href="#" onclick="completedBorrow(${borrow.getId()})">${borrow.getBackDate() != null ? "" : "完成借阅"}</a></td>
                                </tr>
                                <% i ++; %>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="mypaeg">
                        <button id="upPage">上一页</button>
                        <button id="downPage">下一页</button>
                    </div>
                </div>
                <div class="col item-3">
                    <div class="card">
                        <div style="margin-left: 0; margin-bottom: 5px">过滤</div>
                        <div class="input-group input-group-sm">
                            <input type="text" class="form-control" placeholder="name or isbn" id="search-field">
                            <button class="btn btn-outline-secondary" type="button" id="search-btn" onclick="searchBook(<%=admin.getId()%>)">Filter</button>
                        </div>
                        <div class="search-type">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="by-default" checked>
                                <label class="form-check-label" for="by-user-name">
                                    默认&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="by-user-name" >
                                <label class="form-check-label" for="by-user-name">
                                    姓名&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="by-book-name">
                                <label class="form-check-label" for="by-book-name">
                                    书名&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="by-no-back">
                                <label class="form-check-label" for="by-no-back">
                                    未归还&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="radio" name="flexRadioDefault" id="by-yet-back">
                                <label class="form-check-label" for="by-yet-back">
                                    已归还&nbsp;&nbsp;
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
<%--页脚--%>
<footer class="foot">
    <div class="foot-body">
        <ul class="foot-ul">
            <li>
                <a href="${pageContext.request.contextPath}/about.jsp" class="foot-link">
                    <svg t="1649389973388" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="12368" width="16" height="16"><path d="M1023.575729 476.987041c0-12.868082-1.023985-25.531367-2.252768-38.39945v-0.819188a338.051688 338.051688 0 0 0-2.696495-35.225095 787.88844 787.88844 0 0 0-9.864392-62.360707 1110.47795 1110.47795 0 0 0-19.728783-82.08949 405.293391 405.293391 0 0 0-41.539672-96.357019 233.127325 233.127325 0 0 0-63.555355-71.030448c-19.114393-14.096865-39.867162-25.155906-61.43912-35.020298a516.191001 516.191001 0 0 0-78.983401-27.750002 719.247291 719.247291 0 0 0-120.72787-21.947419C598.963148 3.803423 574.899493 0.970397 550.835838 0.356006a782.154122 782.154122 0 0 0-37.648527-0.204798 746.72423 746.72423 0 0 0-30.036903 0c-9.830259 0.204797-19.933581 0.819188-29.797973 1.638377-13.755536 0.580258-27.545205 1.809041-41.300741 3.208487-6.382842 0.614391-12.970481 1.399447-19.353323 2.218635-6.997233 0.785055-14.199263 1.604244-21.162363 2.798893-5.154059 0.614391-10.103322 1.433579-15.223248 2.218635-33.518453 4.846864-66.422515 12.663285-98.712186 22.527677-33.518453 10.478783-66.01292 23.551662-96.25462 41.64207-23.005537 13.892068-44.202033 30.378231-61.268455 51.131001a309.99449 309.99449 0 0 0-40.481553 65.569193 569.369972 569.369972 0 0 0-31.060888 86.936354C15.769374 326.290534 8.601477 373.76932 3.857011 421.452903c-2.252768 23.551662-3.071956 47.103325-3.686347 70.825652a315.14855 315.14855 0 0 0 0 19.728784c0 6.655905-0.23893 13.277676 0 19.728783 0.614391 23.551662 1.433579 47.273989 3.686347 70.825652 4.505535 47.683583 11.946495 94.957572 24.678047 141.412373 8.191883 29.797973 18.295204 58.776758 31.470482 86.936354 11.093174 23.346865 24.234319 45.464948 40.481553 65.569193 17.066422 20.752769 38.058121 37.2048 61.302588 51.131001 30.207567 18.090407 62.497238 31.163287 96.25462 41.64207 32.255538 9.864392 65.159599 17.88561 98.64392 22.527677 5.154059 0.785055 10.103322 1.399447 15.257381 2.218635 6.9631 1.19465 13.994466 2.013838 21.162363 2.798893 6.382842 0.819188 12.970481 1.604244 19.31919 2.218635 13.789669 1.399447 27.579338 2.389299 41.334874 3.208487 9.898525 0.819188 19.967714 1.228782 29.832106 1.638377 10.069189 0.170664 19.933581 0.170664 30.036903 0 12.526754 0.170664 25.053508 0.170664 37.614394-0.204797 24.063655-0.614391 47.922513-3.618081 71.952036-5.63192a723.036036 723.036036 0 0 0 120.727869-21.947418c26.930814-7.031366 53.452034-16.07657 78.949268-27.750003a354.571984 354.571984 0 0 0 61.473253-35.020298 233.127325 233.127325 0 0 0 63.555355-70.996315c17.88561-30.617161 31.880076-62.599636 41.539672-96.391152 7.611624-26.964947 13.994466-54.544285 19.728784-82.08949 4.334871-20.513839 6.997233-41.437273 9.898524-62.360706 1.638377-11.673433 2.252768-23.551662 2.662362-35.225096v-0.819188c1.228782-12.868082 2.252768-25.531367 2.252768-38.399449 0-11.673433 0-23.346865-0.204797-35.020298-0.204797-11.87823-0.204797-23.346865-0.204797-35.020298z m-346.687298-53.622699c38.843177 0 70.518456 30.412364 70.518456 68.060892 0 37.614394-31.675279 68.060891-70.518456 68.060891-39.082106 0-70.552589-30.446497-70.552588-68.060891 0-37.648527 31.470482-68.060891 70.552588-68.060892zM419.833982 246.726874c58.367163 0 105.675285 45.635613 105.675286 101.988938 0 56.319193-47.308122 101.988938-105.709418 101.988939-58.367163 0-105.675285-45.635613-105.675286-101.988939-0.204797-56.319193 47.273989-101.988938 105.675286-101.988938z m207.049833 531.107054H208.654076c-0.204797-5.119927-0.409594-10.239853-0.409594-15.598709 0-156.089496 93.762923-282.75648 209.575663-282.756481 115.710341 0 209.507397 126.462187 209.507397 282.756481 0 5.358857-0.204797 10.478783-0.409594 15.632842z m188.140236 0h-127.076578c0.204797-5.119927 0.409594-10.239853 0.409594-15.598709 0-66.832109-15.83764-128.510158-42.324727-179.095033a108.201116 108.201116 0 0 1 29.354246-4.334872c77.140228 0 139.842262 84.512922 139.842262 188.754628 0 3.310886 0 6.826569-0.204797 10.308119z" p-id="12369" fill="#8aff80"></path></svg>
                    about
                </a>
            </li>
        </ul>
        <small class="copyright">
            © 2022.4.28 -- latest
        </small>
    </div>
</footer>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.6.0.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function () {
        // 上一页点击事件
        $('#upPage').click(function () {
            location.search = processQueryArgs((page) => {
                return ((page <= 1 ? 1 : page - 1));
            });
        });
        // 下一页点击事件
        $('#downPage').click(function () {
            location.search = processQueryArgs((page) => {
                return (page === 0 ? page*1 + 2 : page*1 + 1);
            });
        });
    });

    // 获取搜索参数
    function getQueryArgs() {
        let qs = (location.search.length > 0 ? location.search.substring(1) : ""),
            args = {};
        for (let item of qs.split("&").map(kv => kv.split("="))) {
            let key = decodeURIComponent(item[0]),
                value = decodeURIComponent(item[1]);
            if (key.length > 0) {
                args[key] = value;
            }
        }
        return args;
    }

    // 处理搜索参数字符串
    function processQueryArgs(fn) {
        let args = getQueryArgs();
        let page = args['page'] === undefined ? 0 : getQueryArgs()['page'];
        page = fn(page);
        args['page'] = page
        let qs = "?";
        for (let x in args) {
            qs += x + '=' + args[x] + '&';
        }
        qs = qs.slice(0, -1);
        return qs;
    }
    // 搜索图书
    function searchBook(aid) {
        let byUserName = document.getElementById('by-user-name').checked
        let byBookName = document.getElementById('by-book-name').checked
        let byNoBack = document.getElementById('by-no-back').checked
        let byYetBack = document.getElementById('by-yet-back').checked
        let byDefault = document.getElementById('by-default').checked
        let info = document.getElementById('search-field').value
        info = info.trim() // =>  创建字符串副本并删除左右空白字符
        if (byDefault) {
            location.search = '?id=' + aid
        } else if (byNoBack) {
            location.search = '?id=' + aid + '&searchType=' + 'back' + "&data=" + "no"
        } else if (byYetBack) {
            location.search = '?id=' + aid + '&searchType=' + 'back' + "&data=" + "yet"
        } else if (info) {
            let searchType = byUserName ? 'username' : (byBookName ? 'bookname' : 'none')
            location.search = '?id=' + aid + '&searchType=' + searchType + '&data=' + info
        }
    }
    // 完成借阅
    function completedBorrow(id) {
        let rs = confirm('确定要完成借阅吗?')
        if (rs) {
            $.ajax({
                url: "/admin/api/completedBorrow",
                type: "GET",
                data: {id: id},
                success: (data) => {
                    let dateJson = JSON.parse(data)
                    alert(dateJson['msg'])
                    location.reload();
                },
                error: (data) => {
                    let dataJson = JSON.parse(data)
                    alert(dataJson['msg'])
                    location.reload();
                },
            })
        }
    }

</script>
</body>
</html>