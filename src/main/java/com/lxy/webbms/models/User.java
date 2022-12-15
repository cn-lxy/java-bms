package com.lxy.webbms.models;

import com.lxy.webbms.Util.DBUtil;

import javax.management.ObjectName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private int id;
    private String name;
    private String account;
    private String password;
    private String sex;
    private String college;
    private String birthday;
    private String register;

    private final DBUtil db;

    // login: code
    public int USER_NOT_EXIST = 0;
    public int USER_PWD_ERR = 1;
    public int LOGIN_SUCCESS = 2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public User() {
        db = new DBUtil();
    }

    public User(String name, String account, String password, String sex, String college, String birthday) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.sex = sex;
        this.college = college;
        this.birthday = birthday;
        db = new DBUtil();
    }

    public User(int id, String name, String account, String password, String sex, String college, String birthday, String register) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.sex = sex;
        this.college = college;
        this.birthday = birthday;
        this.register = register;
        db = new DBUtil();
    }

    @Override
    public String toString() {
        return String.format(
                "user => id: %d, name: %s, account: %s, password: %s, sex: %s, college: %s, birthday: %s, register: %s\n",
                this.getId(), this.getName(), this.getAccount(), this.getPassword(), this.getSex(), this.getCollege(),
                this.getBirthday(), this.getRegister()
        );
    }

    public int login() {
        String sql = "select * from users where account=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.account);
        Map<Object, Object> user = db.getMap(sql, params);
        if (user != null) {
            if (user.get("password").equals(this.password)) {
                // 登录成功
                setId(Integer.parseInt((String) user.get("id")));
                return LOGIN_SUCCESS;
            }
            return USER_PWD_ERR;
        }
        return USER_NOT_EXIST;
    }

    // 用户注册
    public int register() {
        // 1:name, 2: account, 3: password, 4: sex, 5: college, 6: birthday
        String sql = "insert into `users` values (null, ?, ?, ?, ?, ?, ?, now())";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.name);
        params.put(2, this.account);
        params.put(3, this.password);
        params.put(4, this.sex);
        params.put(5, this.college);
        params.put(6, this.birthday);
        return db.update(sql, params);
    }

    // 通过id初始化user
    public void initUserByID() {
        String sql = "select * from users where id = " + String.valueOf(this.id);
        Map<Object, Object> m =  db.getMap(sql, null);
        setName((String) m.get("name"));
        setAccount((String) m.get("account"));
        setPassword((String) m.get("password"));
        setSex((String) m.get("sex"));
        setCollege((String) m.get("college"));
        setBirthday((String) m.get("birthday"));
        setRegister((String) m.get("register"));
    }

    // 更新用户信息
    public int updateUser() {
        // params: 1.name, 2.account, 3.password, 4.sex, 5.college, 6.birthday, 7.id(int);
        String sql = "update users set name=?, account=?, password=?, sex=?, college=?, birthday=? where id=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.name);
        params.put(2, this.account);
        params.put(3, this.password);
        params.put(4, this.sex);
        params.put(5, this.college);
        params.put(6, this.birthday);
        params.put(7, this.id);
        return db.update(sql, params);
    }
    public List<Object> allBorrow() {
        String sql = "select users.name as user_name, b2.isbn, b2.name as book_name, days, b.borrow_date, b.back_date from users\n" +
                "join borrow b on users.id = b.uid\n" +
                "join books b2 on b2.isbn = b.bid\n" +
                "where users.id=" + this.getId();
        return db.getList(sql, null);
    }

    // 查询未完成的借阅
    public List<Object> borrowing() {
        // 通过 `user.id` 查询
        String sql = "select users.name as user_name, b2.isbn, b2.name as book_name, days, b.borrow_date, b.back_date from users\n" +
                "join borrow b on users.id = b.uid\n" +
                "join books b2 on b2.isbn = b.bid\n" +
                "where users.id=" + this.getId();
        List<Object> list = db.getList(sql, null);
        List<Object> newList = new ArrayList<>();
        for (Object obj : list) {
            Map<Object, String> m = (Map<Object, String>) obj;
            if (m.get("back_date") == null)
                newList.add(m);
        }
        return newList;
    }

    // 查询历史借阅 TODO
    public List<Object> historyBorrow() {
        String sql = "select users.name as user_name, b2.isbn, b2.name as book_name, days, b.borrow_date, b.back_date from users\n" +
                "join borrow b on users.id = b.`uid`\n" +
                "join books b2 on b2.isbn = b.bid\n" +
                "where users.id=" + this.getId();
        // TODO: 获取历史借阅 list
        List<Object> list = db.getList(sql, null);
        List<Object> historyBorrowList = new ArrayList<>();
        for (Object obj : list) {
            Map<Object, Object> m = (Map<Object, Object>) obj;
            if (m.get("back_date") != null)
                historyBorrowList.add(m);
        }
        return historyBorrowList;
    }

    // 借书
    public int borrowBook(String isbn, int days) {
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.getId());
        params.put(2, isbn);
        params.put(3, days);
        // params: 1.uid 2.isbn 3.days
        String sql = "insert into borrow values (null, ?, ?, ?, now(), null)";
        return db.update(sql, params);
    }

    public int backBook(String isbn) {
        String sql = "update borrow set back_date=now() where uid=? and bid=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.getId());
        params.put(2, isbn);
        return db.update(sql, params);
    }
}
