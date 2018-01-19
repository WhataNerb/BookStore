package com.dht.service;

import com.dht.dao.*;
import com.dht.daoImpl.*;
import com.dht.entity.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 书籍数据操作类
 * @author dht925nerd@126.com
 */
public class BookService {

    private BookDao bookDao = new BookDaoImpl();

    public Page<Book> getPage(CriteriaBook criteriaBook){
        return bookDao.getPage(criteriaBook);
    }

    public Book getBook(int id){ return bookDao.getBook(id); }

    public boolean addToCart(int id, ShoppingCart sc) {
        Book book = bookDao.getBook(id);

        if(book != null){
            sc.addBook(book);
            return true;
        }
        return false;
    }

    public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
        sc.removeItem(id);
    }

    public void clearShoppingCart(ShoppingCart sc) {
        sc.clear();
    }

    public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
        sc.updateItemQuantity(id, quantity);
    }

    private AccountDao accountDao = new AccountDaoImpl();
    private TradeDao tradeDao = new TradeDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private TradeItemDao tradeItemDAO = new TradeItemDaoImpl();

    public void cash(ShoppingCart shoppingCart, String username,
                     String accountId) {

        bookDao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());

        accountDao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());

        Trade trade = new Trade();
        trade.setTradeTime(new Date(new java.util.Date().getTime()));
        trade.setUserId(userDao.getUser(username).getUserId());
        tradeDao.insert(trade);

        Collection<TradeItem> items = new ArrayList<>();
        for(ShoppingCartItem sci: shoppingCart.getItems()){
            TradeItem tradeItem = new TradeItem();

            tradeItem.setBookId(sci.getBook().getId());
            tradeItem.setQuantity(sci.getQuantity());
            tradeItem.setTradeId(trade.getTradeId());

            items.add(tradeItem);
        }
        tradeItemDAO.batchSave(items);

        shoppingCart.clear();
    }
}
