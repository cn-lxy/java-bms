package com.lxy.webbms.models;

public class Borrow {
    private int id;
    private String userName;
    private String bookName;
    private int days;
    private String borrowDate;
    private String backDate;

    public Borrow() {
    }

    public Borrow(int id, String userName, String bookName, int days, String borrowDate, String backDate) {
        this.id = id;
        this.userName = userName;
        this.bookName = bookName;
        this.days = days;
        this.borrowDate = borrowDate;
        this.backDate = backDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", bookName='" + bookName + '\'' +
                ", days=" + days +
                ", borrowDate='" + borrowDate + '\'' +
                ", backDate='" + backDate + '\'' +
                '}';
    }
}
