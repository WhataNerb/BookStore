package com.dht.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <h3>书籍查询条件类</h3>
 * @author dht925nerd@126.com
 */
@Data
@AllArgsConstructor
public class CriteriaBook {

    private float minPrice = 0;
    private float maxPrice = Integer.MAX_VALUE;

    private int pageNo;

}
