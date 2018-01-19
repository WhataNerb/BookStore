package com.dht.dao;

import com.dht.entity.TradeItem;

import java.util.Collection;
import java.util.Set;

/**
 * <h3>交易商品条目数据访问接口</h3>
 * 实现类: {@link com.dht.daoImpl.TradeItemDaoImpl}
 * @author dht925nerd@126.com
 */
public interface TradeItemDao {

    /**
     * 批量保存交易商品条目
     * @param items 交易商品条目集合
     */
    void batchSave(Collection<TradeItem> items);

    /**
     * 根据交易信息 id 获取该交易信息中的商品条目集合
     * @param tradeId 交易信息 id
     * @return 对应的商品条目集合
     */
    Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId);
}
