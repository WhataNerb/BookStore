package com.dht.daoImpl;

import com.dht.dao.TradeDao;
import com.dht.entity.Trade;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <h3>交易信息数据访问实现类</h3>
 * @author dht925nerd@126.com
 */
public class TradeDaoImpl extends
        BaseDao<Trade>
        implements TradeDao {

    /**
     * 新建交易
     * @param trade 交易信息
     */
    @Override
    public void insert(Trade trade) {
        String sql = "INSERT INTO trade(userid, tradetime) VALUES " +
                "(?, ?)";
        long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
        trade.setTradeId((int)tradeId);
    }

    /**
     * 根据用户 id 获取用户的交易信息
     * @param userId 用户 id
     * @return 用户交易信息
     */
    @Override
    public Set<Trade> getTradesWithUserId(Integer userId) {
        String sql = "SELECT tradeId, userId, tradeTime FROM trade " +
                "WHERE userId = ? ORDER BY tradeTime DESC";
        return new LinkedHashSet(getForList(sql, userId));
    }
}
