package com.lxy.webbms.models;

import com.lxy.webbms.Util.DBUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerUsers {
    private final DBUtil dbUtil;

    // 常量
    public final String ID = "id";
    public final String NAME = "name";
    public final String ACCOUNT = "account";

    public ManagerUsers() {
        this.dbUtil = new DBUtil();
    }

    @Contract("_ -> new")
    private @NotNull User MapToUser(@NotNull Map<Object, Object> map) {
        return new User(
                Integer.parseInt((String) map.get("id")),
                (String) map.get("name"),
                (String) map.get("account"),
                (String) map.get("password"),
                (String) map.get("sex"),
                (String) map.get("college"),
                (String) map.get("birthday"),
                (String) map.get("register")
        );
    }

    private @NotNull List<User> ToUserList(@NotNull List<Object> list) {
        List<User> users = new ArrayList<>();
        for (Object obj : list) {
            Map<Object, Object> map = (Map<Object, Object>) obj;
            users.add(MapToUser(map));
        }
        return users;
    }

    /**
     * 通过 `id` | `name` | `account` 获取用户对象
     *
     * @return 用户对象
     */
    public User getUser(@NotNull String k, @NotNull Object v) {
        String tmpl = "select id, name, account, password, sex, college, birthday, register  from users where %s=?";
        String sql = "";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, v);
        switch (k) {
            case ID:
                sql = String.format(tmpl, ID);
                break;
            case NAME:
                sql = String.format(tmpl, NAME);
                break;
            case ACCOUNT:
                sql = String.format(tmpl, ACCOUNT);
                break;
        }
        return MapToUser(dbUtil.getMap(sql, params));
    }

    // TODO: 未来可能实现通过学院获取用户

    /**
     * 可通过 `性别` 获取 多个用户
     */
    public List<User> getUsers(String sex) {
        String sql = "select id, name, account, password, sex, college, birthday, register  from users where sex=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, sex);
        return ToUserList(dbUtil.getList(sql, params));
    }

    public List<User> getUsers() {
        String sql = "select id, name, account, password, sex, college, birthday, register  from users";
        return ToUserList(dbUtil.getList(sql, null));
    }


    /**
     * 添加一个学生
     * @return 添加状态码 1: 成功
     */
    public int addUser(@NotNull User user) {
        // 字段顺序: id, name, account, password, sex, college, birthday(YY-MM-DD), register
        String sql = "insert into users values(null, ?, ?, ?, ?, ?, ?, now())";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, user.getName());
        params.put(2, user.getAccount());
        params.put(3, user.getPassword());
        params.put(4, user.getSex());
        params.put(5, user.getCollege());
        params.put(6, user.getBirthday());
        return dbUtil.update(sql, params);
    }

    /**
     * 通过 id 删除用户
     */
    public int deleteUser(int id) {
        String sql1 = "delete from borrow where uid = ?"; // 删除用户借阅的SQL
        String sql2 = "delete from users where id = ?";   // 删除用户的SQL

        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        dbUtil.update(sql1, params);
        return dbUtil.update(sql2, params);
    }
}
