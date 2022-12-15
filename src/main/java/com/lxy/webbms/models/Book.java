package com.lxy.webbms.models;

import com.lxy.webbms.Util.DBUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book implements Serializable {
    private String ISBN;
    private String name;
    private int typeId;
    private String author;
    private String press;
    private String pressDate;
    private int stock;

    private DBUtil db;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPressDate() {
        return pressDate;
    }

    public void setPressDate(String pressDate) {
        this.pressDate = pressDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Book() {
        db = new DBUtil();
    }

    /**
     * 通过设置 ISBN 属性，然后调用该方法，自动初始化book的其他属性
     * */
    public void initBook() {
        String sql = "select name, author, type_id, public, public_date, stock from books where isbn=" + this.ISBN;
        Map<Object, Object> book = db.getMap(sql, null);
        setName((String) book.get("name"));
        setAuthor((String) book.get("author"));
        setTypeId(Integer.parseInt( (String) book.get("type_id")));
        setPress((String) book.get("public"));
        setPressDate((String) book.get("public_date"));
        setStock(Integer.parseInt((String) book.get("stock")));
    }

    public List<Object> getBooksDefault(int offset, int nums) {
        String sql = "select bt.type as type, books.isbn as isbn, books.name as name, books.author as author, books.public as public, books.public_date as public_date, books.stock as stock\n" +
                "from books\n" +
                "join book_type bt on books.type_id = bt.id\n" +
                "limit ?, ?"; // => 默认 offset: 0, nums: 10;
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, offset);
        params.put(2, nums);
        return db.getList(sql, params);
    }

    public List<Object> getBooksByType(String type, int offset, int nums) {
        String sql = "select bt.type as type, books.isbn as isbn, books.name as name, books.author as author, books.public as public, books.public_date as public_date, books.stock as stock\n" +
                "from books\n" +
                "join book_type bt on books.type_id = bt.id\n" +
                "where bt.type=? limit ?, ?"; // => 默认 offset: 0, nums: 10;
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, type);
        params.put(2, offset);
        params.put(3, nums);
        return db.getList(sql, params);
    }

    public List<Object> getBooksByName(String name, int offset, int nums) {
        String sql = "select bt.type as type, books.isbn as isbn, books.name as name, books.author as author, books.public as public, books.public_date as public_date, books.stock as stock\n" +
                "from books\n" +
                "join book_type bt on books.type_id = bt.id\n" +
                "where books.name = ? limit ?, ?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        params.put(2, offset);
        params.put(3, nums);
        return db.getList(sql, params);
    }

    public List<Object> getBooksByISBN(String isbn, int offset, int nums) {
        String sql = "select bt.type as type, books.isbn as isbn, books.name as name, books.author as author, books.public as public, books.public_date as public_date, books.stock as stock\n" +
                "from books\n" +
                "join book_type bt on books.type_id = bt.id\n" +
                "where books.isbn = ? limit ?, ?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, isbn);
        params.put(2, offset);
        params.put(3, nums);
        return db.getList(sql, params);
    }

    // 获取书籍分类
    public List<Object> getBookTypes() {
        String sql = "select id, type from book_type";
        return db.getList(sql, null);
    }

    public List<Object> getBooksByTypeID(int id, int offset, int nums) {
        String sql = "select bt.type as type, books.isbn as isbn, books.name as name, books.author as author, books.public as public, books.public_date as public_date, books.stock as stock\n" +
                "from books\n" +
                "join book_type bt on books.type_id = bt.id\n" +
                "where bt.id = ? limit ?, ?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        params.put(2, offset);
        params.put(3, nums);
        return db.getList(sql, params);
    }

    /**
     * 通过主键即，isbn插入图书信息
     * 在调用方法前应该先设置book的属性
     * @return 返回执行删除的状态码: 1:成功
     * */
    public int insertBook() {
        // params: ISBN, name, typeId[int], author, press, pressDate[date], stock[int]
        // ('9787010131566', '共产党宣言', 4, '马克思、恩格斯', '人民出版社', '2015-01', now(), 100),
        String sql = "insert into `books` values (?, ?, ?, ?, ?, ?, now(), ?)";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.ISBN);
        params.put(2, this.name);
        params.put(3, this.typeId);
        params.put(4, this.author);
        params.put(5, this.press);
        params.put(6, this.pressDate);
        params.put(7, this.stock);
        return db.update(sql, params);
    }

    /**
     * 通过主键即，isbn更新图书信息
     * 在调用方法前应该先设置book的属性
     * @return 返回执行删除的状态码: 1:成功
     * */
    public int updateBook() {
        // params: ISBN, name, typeId[int], author, press, pressDate[date], stock[int]
        String sql = "update `books` set isbn=?, name=?, type_id=?, author=?, public=?, public_date=?, stock=? where isbn=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.ISBN);
        params.put(2, this.name);
        params.put(3, this.typeId);
        params.put(4, this.author);
        params.put(5, this.press);
        params.put(6, this.pressDate);
        params.put(7, this.stock);
        params.put(8, this.ISBN);

        return db.update(sql, params);
    }

    /**
     * 通过book.ISBN属性删除图书
     * @return 返回执行删除的状态码: 1:成功
     * */
    public int deleteBook() {
        String sql = "delete from `books` where isbn=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.getISBN());
        return db.update(sql, params);
    }

    /**
     * 通过isbn检查本书是否被借阅
     * @return int 被借阅的记录数
     * */
    public int checkBorrow() {
        String sql = "select * from borrow join books b on b.isbn = borrow.bid where b.isbn=?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, this.getISBN());
        List< Object> list = db.getList(sql, params);
        return list.size();
    }
}
