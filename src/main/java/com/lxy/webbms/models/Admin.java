package com.lxy.webbms.models;

import com.lxy.webbms.Util.DBUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Admin implements Serializable {
    private int id;
    private String account;
    private String password;

    private final DBUtil db;

    // code
    public final int ADMIN_LOGIN_PWD_ERR = 0;
    public final int ADMIN_LOGIN_SUCCESS = 1;
    public final int ADMIN_LOGIN_NO_EXISTS = 3;
    public final int INIT_FAILED = 3;
    public final int INIT_SUCCESS = 4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin() {
        db = new DBUtil();
    }

    /**
     * 在登陆前先使用 set方法,设置account & password
     * @return login status code.
    * */
    public int login() {
        String sql = "select id, password from admins where account=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.account);
        Map<Object, Object> result = db.getMap(sql, params);
        if (result != null) {
            if (result.get("password").equals(this.password)) {
                this.id = Integer.parseInt((String) result.get("id"));
                return ADMIN_LOGIN_SUCCESS;
            } else return ADMIN_LOGIN_PWD_ERR;
        }
        return ADMIN_LOGIN_NO_EXISTS;
    }

    public int initAdminByID() {
        String sql = "select id, account, password from admins where id=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.id);
        Map<Object, Object> map = db.getMap(sql, params);
        try {
            setAccount((String) map.get("account"));
            setPassword((String) map.get("password"));
        } catch (Exception e) {
            return INIT_FAILED;
        }
        return INIT_SUCCESS;
    }

}
