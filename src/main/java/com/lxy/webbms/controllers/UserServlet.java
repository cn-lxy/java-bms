package com.lxy.webbms.controllers;

import com.lxy.webbms.models.Book;
import com.lxy.webbms.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "User Servlet", value = "/user/api/*", description = "this is user api")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 获取请求路径
        String uri = req.getRequestURI();
        System.out.println(uri);

        switch (uri) {
            case "/user/api/login":
                loginHandler(req, resp);
                break;
            case "/user/api/register":
                registerHandler(req, resp);
                break;
            case "/user/api/modify":
                modifyHandler(req, resp);
                break;
            case "/user/api/borrow":
                borrowBook(req, resp);
                break;
            case "/user/api/back":
                backBook(req, resp);
                break;
        }
    }

    // 用户登录[POST] handler => /user/api/login
    private void loginHandler(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setAccount(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        int code = user.login();
        // 登陆成功 => 跳转home页面
        if (code == user.LOGIN_SUCCESS) {
            resp.sendRedirect("/user/home.jsp?id=" + user.getId());
        } else if (code == user.USER_NOT_EXIST) {
            req.setAttribute("msg", "用户不存在！");
            req.getRequestDispatcher("/user/error.jsp").forward(req, resp);
        }
        else if (code == user.USER_PWD_ERR) {
            req.setAttribute("msg", "密码错误！");
            req.getRequestDispatcher("/user/error.jsp").forward(req, resp);
        }
    }

    // 注册用户[POST] handler => /user/api/register
    private void registerHandler(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");
        String college = req.getParameter("college");
        String birthday = req.getParameter("birthday");

        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPassword(password);
        user.setSex(sex);
        user.setCollege(college);
        user.setBirthday(birthday);

        PrintWriter out = resp.getWriter();
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        if (user.register() == 1) {
            out.print("注册成功,请登录!");
        } else {
            out.print("注册失败!");
        }
    }

    // 更改用户信息[POST] handler => /user/api/modify
    private void modifyHandler(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        // TODO 根据id查询用户信息并设置params, 在jsp端通过javaBean拿到数据渲染页面
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");
        String college = req.getParameter("college");
        String birthday = req.getParameter("birthday");

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setAccount(account);
        user.setPassword(password);
        user.setSex(sex);
        user.setCollege(college);
        user.setBirthday(birthday);

        PrintWriter out = resp.getWriter();
        if (user.updateUser() == 1) {
            out.print("更新成功!");
            resp.setHeader("content-type", "text/html;charset=UTF-8");
            resp.setHeader("Refresh", "2;url=/user/login.jsp");
        }
        else {
            resp.setHeader("content-type", "text/html;charset=UTF-8");
            out.print("更新失败!");
        }
    }

    // 借书[GET] => /user/api/borrow
    private void borrowBook(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        String isbn = req.getParameter("isbn");
        String uid = req.getParameter("uid");
        int days = Integer.parseInt(req.getParameter("days"));
        User user = new User();
        user.setId(Integer.parseInt(uid));
        user.initUserByID();
        PrintWriter out = resp.getWriter();
        if (user.borrowBook(isbn, days) == 1) {
            out.print("借阅成功");
        }
        else {
            out.print("借阅失败!");
        }
    }

    // 还书[GET] => /user/api/back
    private void backBook(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        // 获取 `uid` 和 `isbn`
        int uid = Integer.parseInt(req.getParameter("uid"));
        String isbn = req.getParameter("isbn");
        User user = new User();
        user.setId(uid);
        user.initUserByID();
        int result = user.backBook(isbn);
        PrintWriter out = resp.getWriter();
        if (result == 1) {
            out.print("归还成功");
        } else {
            out.print("归还失败");
        }
    }
}
