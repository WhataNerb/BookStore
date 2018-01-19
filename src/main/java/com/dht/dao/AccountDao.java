package com.dht.dao;

import com.dht.entity.Account;

/**
 * <h3>账户信息数据访问接口</h3>
 * 实现类: {@link com.dht.daoImpl.AccountDaoImpl}
 * @author dht925nerd@126.com
 */
public interface AccountDao {

    /**
     * 根据账户 id 获取账户信息
     * @param accountId 账户 id
     * @return id 对应的账户信息
     */
    Account get(Integer accountId);

    /**
     * 更新账户余额
     * @param accountId 账户 id
     * @param amount 交易金额
     */
    void updateBalance(Integer accountId, float amount);

}
