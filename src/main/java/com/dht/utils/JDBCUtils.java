package com.dht.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * JDBC 工具类. 封装了数据库操作的基本方法
 *
 * @author dht925nerd@126.com
 * @version 2.0
 */
public class JDBCUtils {

    /**
     * 数据库连接池
     * 只需要初始化一次
     */
    private static DataSource dataSource = null;
    static {
        dataSource = new ComboPooledDataSource("c3p0DataSource");
    }

    /**
     * 使用数据库连接池 getConnection()
     * @return Connection 实例
     * @throws SQLException 连接数据库时发生错误
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 释放资源
     * 数据库连接池 close 并不是释放资源
     * 而是将 Connection 归还给数据库连接池
     * @param statement 需要释放的 Statement
     * @param connection 需要释放的 Connection
     */
    public static void release(Statement statement, Connection connection, ResultSet resultSet){
        JDBCUtils.release(statement, connection);

        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void release(Statement statement, Connection connection){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void release(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过 JDBC 对指定的表进行更新操作: INSERT, DELETE, UPDATE
     *
     * Connection、Statement 都是应用程序和数据库连接的资源, 使用后一定要关闭
     * 为防止异常中断, 需要在 finally 中关闭资源
     *
     * 关闭顺序: 按照嵌套顺序, 先关 Statement 再关 Connection
     */
    public static void update(String sql) {
        //1. 获取数据库连接
        Connection connection = null;
        //2. 准备插入的 SQL 语句
        Statement statement = null;
        try {
            connection = getConnection();
            //3. 执行插入操作
            //1). 获取 SQL 语句的 Statement 对象
            statement = connection.createStatement();
            //2). 调用 Statement 对象的 executeUpdate(sql) 执行操作
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4. 关闭连接和 Statement 对象
            JDBCUtils.release(statement, connection);
        }
    }

    /**
     * 使用带占位符的 PreparedStatement
     * 1. 解决了 Statement 语句拼写 SQL 语句复杂的问题
     * 2. 有效的防止了 SQL 注入攻击
     * @param sql 需要执行的 SQL 语句
     * @param args 填充 PreparedStatement 占位符的参数
     */
    public static void update(String sql, Object... args){
        //1. 获取数据库连接
        Connection connection = null;
        //2. 准备插入的 SQL 语句
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            //3. 执行插入操作
            //1). 获取 SQL 语句的 Statement 对象
            statement = connection.prepareStatement(sql);
            //2). 为占位符赋值
            for (int i = 0; i < args.length; i++){
                statement.setObject(i+1, args[i]);
            }
            //3). 调用 PreparedStatement 对象的 executeUpdate() 执行操作
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4. 关闭连接和 Statement 对象
            JDBCUtils.release(statement, connection);
        }
    }

    //处理数据库事务方法
    /**
     * 提交事务
     */
    public static void commit(Connection connection){
        if(connection != null){
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 回滚事务
     */
    public static void rollback(Connection connection){
        if(connection != null){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 开始事务
     */
    public static void beginTx(Connection connection){
        if(connection != null){
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
