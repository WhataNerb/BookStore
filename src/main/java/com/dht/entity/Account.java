package com.dht.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>账户信息类</h3>
 * @author dht925nerd@126.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Integer accountId;

    private int balance;

}
