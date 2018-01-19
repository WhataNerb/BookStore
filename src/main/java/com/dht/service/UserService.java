package com.dht.service;

import com.dht.dao.BookDao;
import com.dht.dao.TradeDao;
import com.dht.dao.TradeItemDao;
import com.dht.dao.UserDao;
import com.dht.daoImpl.BookDaoImpl;
import com.dht.daoImpl.TradeDaoImpl;
import com.dht.daoImpl.TradeItemDaoImpl;
import com.dht.daoImpl.UserDaoImpl;
import com.dht.entity.Trade;
import com.dht.entity.TradeItem;
import com.dht.entity.User;

import java.util.Iterator;
import java.util.Set;

/**
 * 用户数据操作类
 * @author dht925nerd@126.com
 */
public class UserService {

    private UserDao userDao = new UserDaoImpl();

    public User getUserByUserName(String username){
        return userDao.getUser(username);
    }

    private TradeDao tradeDAO = new TradeDaoImpl();
    private TradeItemDao tradeItemDAO = new TradeItemDaoImpl();
    private BookDao bookDAO = new BookDaoImpl();

    public User getUserWithTrades(String username){

        User user = userDao.getUser(username);
        if(user == null){
            return null;
        }

        int userId = user.getUserId();

        Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);

        if(trades != null){
            Iterator<Trade> tradeIt = trades.iterator();

            while(tradeIt.hasNext()){
                Trade trade = tradeIt.next();
                int tradeId = trade.getTradeId();
                Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);

                if(items != null){
                    for(TradeItem item: items){
                        item.setBook(bookDAO.getBook(item.getBookId()));
                    }
                    if(items != null && items.size() != 0){
                        trade.setItems(items);
                    }
                }
                if(items == null || items.size() == 0){
                    tradeIt.remove();
                }
            }
        }

        if(trades != null && trades.size() != 0){
            user.setTrades(trades);
        }

        return user;
    }
}
