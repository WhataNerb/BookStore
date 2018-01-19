package com.dht.entity;

import lombok.Data;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <h3>交易信息类</h3>
 * @author dht925nerd@126.com
 */
@Data
public class Trade {

    private Integer tradeId;
    private Date tradeTime;
    private Integer userId;

    private Set<TradeItem> items = new LinkedHashSet<>();

}
