package com.lxy.webbms.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void login() {
        Admin admin = new Admin();
        admin.setAccount("admin");
        admin.setPassword("123456");
        admin.login();
    }

    @Test
    void initAdminByID() {
        int id = 1;
        Admin admin = new Admin();
        admin.setId(id);
        admin.initAdminByID();
    }

    @Test
    void initAdminByID_2() {
        int id_2 = 2;
        Admin admin1 = new Admin();
        admin1.setId(id_2);
        admin1.initAdminByID();
    }
}