package com.lxy.webbms.models;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

class ManagerUsersTest {

    @Test
    void getUserByID() {
        ManagerUsers mu = new ManagerUsers();
        User user = mu.getUser(mu.ID, 1);
        System.out.println(user);
    }

    @Test
    void getUsersBySex() {
        ManagerUsers mu = new ManagerUsers();
        List<User> users = mu.getUsers("男");
        for (Object obj : users) {
            User user = (User) obj;
            System.out.println(user);
        }
    }

    @Test
    void getUsers() {
        ManagerUsers mu = new ManagerUsers();
        List<User> users = mu.getUsers();
        for (Object obj : users) {
            User user = (User) obj;
            System.out.println(user);
        }
    }

    @Test
    void addUser() {
        ManagerUsers mu = new ManagerUsers();
        User user = new User("小红", "2019002", "123456", "女", "信息通信工程学院", "2001-10-10");
        int result = mu.addUser(user);
        System.out.println(result);
    }
}