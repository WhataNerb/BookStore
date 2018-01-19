package com.dht.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>交易商品条目类</h3>
 * @author dht925nerd@126.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeItem {

    private Integer tradeItemId;
    private Book book;
    private int quantity;
    private Integer bookId;
    private Integer tradeId;

}
