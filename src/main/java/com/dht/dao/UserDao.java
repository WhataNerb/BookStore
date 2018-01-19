package com.dht.dao;

import com.dht.entity.User;

/**
 * <h3>用户信息数据访问接口</h3>
 * 实现类: {@link com.dht.daoImpl.UserDaoImpl}
 * @author dht925nerd@126.com
 */
public interface UserDao {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getUser(String username);
}
