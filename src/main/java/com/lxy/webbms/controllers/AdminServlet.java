package com.lxy.webbms.controllers;

import com.lxy.webbms.models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminServlet", value = "/admin/api/*")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println(uri);
        switch (uri) {
            case "/admin/api/login":
                login(request, response);
                break;
            case "/admin/api/updateBook":
                updateBook(request, response);
                break;
            case "/admin/api/deleteBook":
                deleteBook(request, response);
                break;
            case "/admin/api/addBook":
                addBook(request, response);
                break;
            case "/admin/api/updateUser":
                updateUser(request, response);
                break;
            case "/admin/api/deleteUser":
                deleteUser(request, response);
                break;
            case "/admin/api/addUser":
                addUser(request, response);
                break;
            case "/admin/api/completedBorrow":
                completedBorrow(request, response);
                break;
        }
    }

    private void login(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Admin admin = new Admin();
        admin.setAccount(request.getParameter("admin"));
        admin.setPassword(request.getParameter("password"));
        int code = admin.login();
        PrintWriter out = response.getWriter();
        if (code == admin.ADMIN_LOGIN_SUCCESS) {
            String json = String.format("{ \"code\": 1, \"href\": \"/admin/nav.jsp?id=%d\" }", admin.getId());
            out.print(json);
        } else if (code == admin.ADMIN_LOGIN_NO_EXISTS) {
            out.print("{ \"code\": -1}");  // 账号不存在
        } else if (code == admin.ADMIN_LOGIN_PWD_ERR) {
            out.print("{ \"code\": -2}");  // 密码错误
        }
    }

    // 更新书籍信息 handler [POST] => /admin/api/updateBook
    private void updateBook(@NotNull HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Book book = new Book();
        book.setISBN(req.getParameter("isbn"));
        book.setName(req.getParameter("bookName"));
        book.setAuthor(req.getParameter("author"));
        book.setTypeId(Integer.parseInt(req.getParameter("typeID")));
        book.setPress(req.getParameter("press"));
        book.setPressDate(req.getParameter("pressDate"));
        book.setStock(Integer.parseInt(req.getParameter("stock")));

        String msg = "";
        String id = req.getParameter("id");
        if (book.updateBook() == 1) {
            msg = String.format("<script>confirm('更新成功');location.href='/admin/manager/book/index.jsp?id=%s';</script>", id);
        } else {
            msg = "<script>confirm('更新失败');location.reload;</script>";
        }
        resp.setHeader("content-Type", "text/html;charset=UTF-8");
        resp.getWriter().print(msg);
    }

    // 删除图书 handler [GET] => "/admin/api/deleteBook"
    private void deleteBook(@NotNull HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Book book = new Book();
        book.setISBN(req.getParameter("isbn"));
        String id = req.getParameter("id");

        String msg;
        String fmt = "<script>confirm('%s');location.href='/admin/manager/book/index.jsp?id=%s';</script>";
        if (book.checkBorrow() != 0) {
            msg = String.format(fmt, "该书正在被借阅，无法删除", id);
        } else {
            if (book.deleteBook() == 1) {
                msg = String.format(fmt, "删除成功", id);
            } else {
                msg = String.format(fmt, "删除失败", id);
            }
        }
        resp.setHeader("content-Type", "text/html;charset=UTF-8");
        resp.getWriter().print(msg);
    }

    // 添加图书 handler [POST] => "/admin/api/addBook"
    private void addBook(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        Book book = new Book();
        book.setISBN(req.getParameter("isbn"));
        book.setName(req.getParameter("bookName"));
        book.setAuthor(req.getParameter("author"));
        book.setTypeId(Integer.parseInt(req.getParameter("typeID")));
        book.setPress(req.getParameter("press"));
        book.setPressDate(req.getParameter("pressDate"));
        book.setStock(Integer.parseInt(req.getParameter("stock")));

        String id = req.getParameter("id");
        resp.setHeader("content-Type", "text/html;charset=UTF-8");
        if (book.insertBook() == 1) {
            resp.getWriter().print(
                    String.format("<script>confirm('添加成功');location.href='/admin/manager/book/index.jsp?id=%s';</script>", id)
            );
        } else {
            resp.getWriter().print("<script>confirm('添加失败');location.reload;");
        }
    }

    // 更新用户 handler [POST] => "/admin/api/updateUser"
    private void updateUser(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        String adminId = req.getParameter("adminId");
        String userId = req.getParameter("userId");
        String name = req.getParameter("name");
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");
        String college = req.getParameter("college");
        String birthday = req.getParameter("birthday");

        User user = new User(name, account, password, sex, college, birthday);
        user.setId(Integer.parseInt(userId));

        PrintWriter writer = resp.getWriter();
        resp.setHeader("content-Type", "text/html;charset=UTF-8");
        String msg = "";
        String fmt = "<script>confirm('%s');%s</script>";
        if (user.updateUser() == 1) {
            msg = String.format(fmt, "更新成功", "location.href='/admin/manager/user/index.jsp?id=" + adminId + "';");
        } else msg = String.format(fmt, "更新失败", "");
        writer.print(msg);
    }

    // 删除用户 handler [GET] => "/admin/api/deleteUser?uid=[]&aid=[]"
    private void deleteUser(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        // TODO: 根据 `user.id` 删除用户
        int uid = Integer.parseInt(req.getParameter("uid")); // 没有获取到参数将报错
        String aid = req.getParameter("aid");
        ManagerUsers mu = new ManagerUsers();
        PrintWriter writer = resp.getWriter();
        resp.setHeader("content-Type", "text/html;charset=UTF-8;");
        String fmt = "<script>confirm('%s');location.href='/admin/manager/user/index.jsp?id=%s';</script>";
        String msg = "";
        msg = String.format(fmt, "删除失败", aid);

        if (mu.deleteUser(uid) == 1) msg = String.format(fmt, "删除成功", aid);
        writer.print(msg);
    }

    // 添加用户 handler [POST] => "/admin/api/addUser"
    private void addUser(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        String adminId = req.getParameter("adminId");
        String name = req.getParameter("name");
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");
        String college = req.getParameter("college");
        String birthday = req.getParameter("birthday");

        User user = new User(name, account, password, sex, college, birthday);

        PrintWriter writer = resp.getWriter();
        resp.setHeader("content-Type", "text/html;charset=UTF-8");
        String msg = "";
        String fmt = "<script>confirm('%s');%s</script>";
        if (user.register() == 1) {
            msg = String.format(fmt, "添加用户成功", "location.href='/admin/manager/user/index.jsp?id=" + adminId + "';");
        } else msg = String.format(fmt, "添加用户失败", "");
        writer.print(msg);
    }

    // 完成借阅 handler [GET] => "admin/api/completedBorrow"
    private void completedBorrow(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        ManagerBorrow mb = new ManagerBorrow();
        String fmt = "{\"msg\": \"%s\", \"code\": %d}";
        String msg = "";
        resp.setHeader("content-Type", "text/html;charset=UTF-8;");
        PrintWriter writer = resp.getWriter();
        if (mb.completedBorrow(id) == 1) {
            msg = String.format(fmt, "借阅完成!", 1);
        } else msg = String.format(fmt, "失败!", -1);
        writer.print(msg);
    }
}

