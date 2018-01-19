package com.dht.service;

import com.dht.dao.AccountDao;
import com.dht.daoImpl.AccountDaoImpl;
import com.dht.entity.Account;

/**
 * 账户数据操作类
 * @author dht925nerd@126.com
 */
public class AccountService {

    private AccountDao accountDao = new AccountDaoImpl();

    public Account getAccount(int accountId){
        return accountDao.get(accountId);
    }

}
