package com.dht.dao;

import com.dht.daoImpl.BaseDao;

import java.util.List;

/**
 * <h3>数据库访问接口</h3>
 * <br><br>
 * 提供了对数据库访问的基本操作
 * <br><br>
 * 实现类:{@link BaseDao}
 * @author dht925nerd@126.com
 */
public interface DAO<T> {

    /**
     * 执行 INSERT 操作, 返回插入后的记录 id
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @return 新记录 id
     */
    long insert(String sql, Object... args);

    /**
     * 执行 INSERT(无返回值), UPDATE, DELETE 操作
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     */
    void update(String sql, Object... args);

    /**
     * 返回一个 T 类型的实例
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @return T 类型实例
     */
    T get(String sql, Object... args);

    /**
     * 返回一组 T 类型实例
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @return List<T>实例
     */
    List<T> getForList(String sql, Object... args);

    /**
     * 返回一个具体的值
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @param <E> 值类型
     * @return 所查询的具体值
     */
    <E> E getForValue(String sql, Object... args);

    /**
     * 批处理操作
     * @param sql SQL 语句
     * @param args 填充占位符的 Object[] 类型可变参数
     */
    void batch(String sql, Object[][] args);

}
