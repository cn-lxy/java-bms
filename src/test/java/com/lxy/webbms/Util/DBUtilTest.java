package com.lxy.webbms.Util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilTest {
    private final DBUtil dbUtil = new DBUtil();
    @Test
    void sql() {
        String sql = "select * from users where id = ?";
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, 1);
        Map<Object, Object> m = dbUtil.getMap(sql, params);
        System.out.println(m);
    }
}