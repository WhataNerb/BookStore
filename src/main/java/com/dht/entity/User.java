package com.dht.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <h3>用户类</h3>
 * @author dht925nerd@126.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;
    private String username;
    private int accountId;

    private Set<Trade> trades = new LinkedHashSet<>();

}
