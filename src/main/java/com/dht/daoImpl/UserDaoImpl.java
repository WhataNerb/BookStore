package com.dht.daoImpl;

import com.dht.dao.UserDao;
import com.dht.entity.User;

/**
 * <h3>用户信息数据访问实现类</h3>
 * @author dht925nerd@126.com
 */
public class UserDaoImpl
        extends BaseDao<User>
        implements UserDao{

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getUser(String username) {
        String sql = "SELECT userId, username, accountId " +
                "FROM userinfo WHERE username = ?";
        return get(sql, username);
    }
}
