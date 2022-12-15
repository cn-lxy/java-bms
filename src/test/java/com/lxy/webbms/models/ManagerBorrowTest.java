package com.lxy.webbms.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerBorrowTest {

    @Test
    void getAllBorrows() {
        ManagerBorrow mb = new ManagerBorrow();
        for (Borrow b : mb.getAllBorrows(1)) {
            System.out.println(b);
        }
    }

    @Test
    void filterBorrows() {
        ManagerBorrow mb = new ManagerBorrow();
        List<Borrow> bs = mb.filterBorrows(2, mb.USER_NAME, "小明");
        for (Borrow b : bs) {
            System.out.println(b);
        }
    }
}