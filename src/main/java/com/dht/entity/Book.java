package com.dht.entity;

import lombok.Data;

import java.sql.Date;

/**
 * <h3>书籍信息类</h3>
 * @author dht925nerd@126.com
 */
@Data
public class Book {

    private Integer id;

    private String author;

    private String title;
    private float price;

    private Date publishingDate;
    private int salesAmount;

    private int storeNumber;
    private String remark;

}
