package com.dht.dao;

import com.dht.entity.Trade;

import java.util.Set;

/**
 * <h3>交易信息数据访问接口</h3>
 * 实现类: {@link com.dht.daoImpl.TradeDaoImpl}
 * @author dht925nerd@126.com
 */
public interface TradeDao {

    /**
     * 新建交易
     * @param trade 交易信息
     */
    void insert(Trade trade);

    /**
     * 根据用户 id 获取用户的交易信息
     * @param userId 用户 id
     * @return 用户交易信息
     */
    Set<Trade> getTradesWithUserId(Integer userId);

}
