package com.dht.daoImpl;

import com.dht.dao.AccountDao;
import com.dht.entity.Account;

/**
 * <h3>账户信息数据访问实现类</h3>
 * @author dht925nerd@126.com
 */
public class AccountDaoImpl
        extends BaseDao<Account>
        implements AccountDao {

    /**
     * 根据账户 id 获取账户信息
     * @param accountId 账户 id
     * @return id 对应的账户信息
     */
    @Override
    public Account get(Integer accountId) {
        String sql = "SELECT accountid, balance FROM account WHERE accountid = ?";
        return get(sql, accountId);
    }

    /**
     * 更新账户余额
     * @param accountId 账户 id
     * @param amount 交易金额
     */
    @Override
    public void updateBalance(Integer accountId, float amount) {
        String sql = "UPDATE account SET balance = balance - ? WHERE accountid = ?";
        update(sql, amount, accountId);
    }
}
