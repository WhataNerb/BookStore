package com.dht.dao;

import com.dht.daoImpl.BaseDao;
import com.dht.daoImpl.BookDaoImpl;
import com.dht.entity.Book;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

/**
 * @author dht925nerd@126.com
 */
class BaseDaoTest {

    private BaseDao baseDao = new BaseDao();
    private BookDaoImpl bookDao = new BookDaoImpl();

    @Test
    void insert() {
        String sql = "INSERT INTO trade(userid, tradetime) VALUES(?, ?)";
        long id = baseDao.insert(sql, 1, new Date(new java.util.Date().getTime()));
        System.out.println(id);
    }

    @Test
    void update() {
        String sql = "UPDATE trade SET userid=2 WHERE tradeid=17";
        baseDao.update(sql);
    }

    @Test
    void get() {
        Book book = bookDao.getBook(5);
        System.out.println(book.toString());
    }

    @Test
    void getForList() {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE id < ?";
        List<Book> books = bookDao.getForList(sql, 5);
        for (Book book : books){
            System.out.println(book.toString());
        }
    }

    @Test
    void getForValue() {
        String sql = "SELECT title FROM mybooks WHERE id = ?";
    }

    @Test
    void batch() {
        String sql = "UPDATE mybooks SET salesamount = ?, storenumber = ? WHERE id = ?";
        bookDao.batch(sql, new Object[]{5, 24, 8}, new Object[]{8, 17, 9});
    }
}