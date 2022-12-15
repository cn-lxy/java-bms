package com.lxy.webbms.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void getBooksDefault() {
        Book book = new Book();
        List<Object> list = book.getBooksDefault(0, 10);
        for (Object obj : list) {
            System.out.println(obj.toString());
        }
    }

    @Test
    void getBooksByType() {
        Book book = new Book();
        List<Object> list = book.getBooksByType("小说", 0, 10);
        for (Object obj : list) {
            System.out.println(obj.toString());
        }
    }

    @Test
    void getBooksByName() {
        Book book = new Book();
        List<Object> list = book.getBooksByName("红楼梦", 0, 10);
        System.out.println(list.toString());
    }

    @Test
    void getBookByISBN() {
        Book book = new Book();
        List<Object> list = book.getBooksByISBN("9787020002207", 0, 10);
        System.out.println(list.toString());
    }

    @Test
    void insertBook() {
        Book book = new Book();
        book.setISBN("测试isbn");
        book.setName("测试bookName");
        book.setAuthor("测试author");
        book.setTypeId(2);
        book.setPress("测试出版社");
        book.setPressDate("2022-4");
        book.setStock(110);

        int result = book.insertBook();
        System.out.println(result);
    }
}