package com.lxy.webbms.models;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void borrowing() {
        User user = new User();
        user.setId(1);
        List<Object> list =  user.borrowing();
        Map<Object, Object> m = (Map<Object, Object>) list.get(0);
        System.out.println(m.toString());
        System.out.println(m.get("book_name"));

    }
}