package com.dht.daoImpl;

import com.dht.dao.TradeItemDao;
import com.dht.entity.TradeItem;

import java.util.*;

/**
 * <h3>交易商品条目数据访问实现类</h3>
 * @author dht925nerd@126.com
 */
public class TradeItemDaoImpl
        extends BaseDao<TradeItem>
        implements TradeItemDao {

    /**
     * 批量保存交易商品条目
     * @param items 交易商品条目集合
     */
    @Override
    public void batchSave(Collection<TradeItem> items) {
        String sql = "INSERT INTO tradeitem(bookid, quantity, tradeid) " +
                "VALUES(?,?,?)";
        Object [][] params = new Object[items.size()][3];

        List<TradeItem> tradeItems = new ArrayList<>(items);
        for(int i = 0; i < tradeItems.size(); i++){
            params[i][0] = tradeItems.get(i).getBookId();
            params[i][1] = tradeItems.get(i).getQuantity();
            params[i][2] = tradeItems.get(i).getTradeId();
        }

        batch(sql, params);
    }

    /**
     * 根据交易信息 id 获取该交易信息中的商品条目集合
     * @param tradeId 交易信息 id
     * @return 对应的商品条目集合
     */
    @Override
    public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
        String sql = "SELECT itemId tradeItemId, bookId, " +
                "quantity, tradeId FROM tradeitem WHERE tradeid = ?";
        return new HashSet<>(getForList(sql, tradeId));
    }
}
