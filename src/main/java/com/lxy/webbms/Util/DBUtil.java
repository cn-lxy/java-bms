package com.lxy.webbms.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
    private String driver;
    private String url;
    private String user;
    private String password;

    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBUtil() {
        driver = "com.mysql.cj.jdbc.Driver";
        /*
            url  = "jdbc:mysql://42.192.149.39:3306/bms";
            user = "lxy";
            password = "LXY1019XYXYZ";
        */
        url  = "jdbc:mysql://localhost:3306/web_bms";
        user = "root";
        password = "123456";
    }

    // 获取数据库连接对象
    private boolean getCon() {
        try {
            Class.forName(driver);
            this.con = DriverManager.getConnection(url, user, password);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取sql执行对象
    private boolean getPsm(String sql) {
        if (this.getCon()) {
            try {
                this.psm = this.con.prepareStatement(sql);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 填充 `sql` 参数
    public void setSQLParam(String sql, HashMap<Integer, Object> params) throws SQLException {
        if (this.getPsm(sql) && params != null) {
            for (Integer k : params.keySet()) {
                Object obj = params.get(k);
                if (obj instanceof String)
                    psm.setString(k, (String)obj);
                if (obj instanceof Integer)
                    psm.setInt(k, (Integer)obj);
            }
        }
    }

    // 执行数据库查询操作时，将结果封装到List对象中
    public List<Object> getList(String sql, HashMap<Integer, Object> params) {
        List<Object> list = new ArrayList<Object>();
        try {
            this.setSQLParam(sql, params);
            ResultSet rs = psm.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> m = new HashMap<String, String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    // getColumnCount => 获取字段名
                    // getColumnLabel => 获取字段别名
                    String colName = rsmd.getColumnLabel(i);
                    m.put(colName, rs.getString(colName));
                }
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    // 执行数据库查询操作时，将返回的结果封装到List对象中
    public Map<Object, Object> getMap(String sql, HashMap<Integer, Object> params) {
        List<Object> list = getList(sql, params);
        if (list.isEmpty()) return null;
        else return (Map<Object, Object>) list.get(0);
    }

    // 更新数据库时调用的update方法
    public int update(String sql, HashMap<Integer, Object> params) {
        int recNo = 0;  // result number
        try {
            // 将参数装填到sql
            setSQLParam(sql, params);
            // 执行更新操作
            recNo = psm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return recNo;
    }

    // 关闭对象: rs, pstmt, con
    private void close() {
        try {
            if (rs != null)
                rs.close();
            if (psm != null)
                psm.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
