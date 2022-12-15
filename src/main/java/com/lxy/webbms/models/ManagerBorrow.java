package com.lxy.webbms.models;

import com.lxy.webbms.Util.DBUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerBorrow {
    private final DBUtil dbUtil;

    public final int USER_NAME = 1;
    public final int BOOK_NAME = 2;
    public final int YET_BACK  = 3;
    public final int NO_BACK   = 4;

    public ManagerBorrow() {
        this.dbUtil = new DBUtil();
    }

    /**
     * 将Map转换为Borrow对象
     */
    private @NotNull Borrow MapToBorrow(@NotNull Map<Object, Object> map) {
        return new Borrow(
                Integer.parseInt((String) map.get("id")),
                (String) map.get("username"),
                (String) map.get("bookname"),
                Integer.parseInt((String) map.get("days")),
                (String) map.get("borrow_date"),
                (String) map.get("back_date")
        );
    }

    /**
     * 将 List 转换成 BorrowList
     */
    private @NotNull List<Borrow> getBorrowList(@NotNull List<Object> list) {
        List<Borrow> borrowList = new ArrayList<>();
        for (Object obj : list) {
            borrowList.add(MapToBorrow((Map<Object, Object>) obj));
        }
        return borrowList;
    }

    /**
     * 得到所有的借阅信息
     * @param page 获取那一页 一页10条记录 从1开始
     */
    public List<Borrow> getAllBorrows(int page) {
        String sql = "select borrow.id as id, u.name as username, b.name as bookname, days, borrow_date, back_date\n" +
                "from borrow join users u on borrow.uid = u.id join books b on b.isbn = borrow.bid\n" +
                "limit ?, 10";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, (page-1)*10);
        return getBorrowList(dbUtil.getList(sql, params));
    }

    /**
     * 通过参数过滤借阅信息
     * @param page 获取那一页 每页10条数据 从1开始
     * @param TAG 过滤模式: 1.一个用户的所有借阅; 2.一本书的所有借阅; 3. 未完成的借阅; 4.已完成的借阅
     * @param value 过滤模式的值
     * */
    public List<Borrow> filterBorrows(int page, int TAG, String... value) {
        String sql = "select borrow.id as id, u.name as username, b.name as bookname, days, borrow_date, back_date\n" +
                "from borrow join users u on borrow.uid = u.id join books b on b.isbn = borrow.bid\n";
        String where = "";
        switch (TAG) {
            case USER_NAME:
                where = String.format("where u.name='%s'", value[0]);
                break;
            case BOOK_NAME:
                where = String.format("where b.name ='%s'", value[0]);
                break;
            case YET_BACK:
                where = "where back_date is not null";
                break;
            case NO_BACK:
                where = "where back_date is null";
                break;
        }
        sql += where + "\nlimit ?, 10";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, (page-1)*10);
        return getBorrowList(dbUtil.getList(sql, params));
    }

    /**
     * 通过borrow id设置借阅完成
     * @param id borrow id
     * */
    public int completedBorrow(int id) {
        String sql = "update borrow set back_date = now() where id = ?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return dbUtil.update(sql, params);
    }
}
