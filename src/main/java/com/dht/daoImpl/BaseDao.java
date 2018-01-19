package com.dht.daoImpl;

import com.dht.dao.DAO;
import com.dht.utils.JDBCUtils;
import com.dht.utils.ReflectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;
import java.util.List;

/**
 * <h3>数据访问接口实现类</h3>
 * 对数据库基本访问操作提供了具体的实现
 * <br>
 * 不同的对象可以继承该类并根据特殊要求提供更加具体的子类实现
 * @author dht925nerd@126.com
 */
public class BaseDao<T> implements DAO<T> {

    private QueryRunner queryRunner = new QueryRunner();

    private Class<T> clazz;

    public BaseDao () {
        clazz = ReflectionUtils.getSuperGenericType(getClass());
    }

    /**
     * 执行 INSERT 操作, 返回插入后的记录 id
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @return 新记录 id
     */
    @Override
    public long insert(String sql, Object... args) {
        long id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (args != null){
                for (int i = 0; i < args.length; i++){
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            preparedStatement.executeUpdate();

            //获取生成id
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(preparedStatement, connection, resultSet);
        }

        return id;
    }

    /**
     * 执行 INSERT(无返回值), UPDATE, DELETE 操作
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     */
    @Override
    public void update(String sql, Object... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();
            queryRunner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn);
        }
    }

    /**
     * 返回一个 T 类型的实例
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @return T 类型实例
     */
    @Override
    public T get(String sql, Object... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();
            return queryRunner.query(conn, sql,
                    new BeanHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn);
        }
        return null;
    }

    /**
     * 返回一组 T 类型实例
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @return List<T>实例
     */
    @Override
    public List<T> getForList(String sql, Object... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();
            return queryRunner.query(conn, sql,
                    new BeanListHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn);
        }
        return null;
    }

    /**
     * 返回一个具体的值
     * @param sql SQL 语句
     * @param args 填充占位符的可变参数
     * @param <E> 值类型
     * @return 所查询的具体值
     */
    @Override
    public <E> E getForValue(String sql, Object... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();
            return queryRunner.query(conn, sql,
                    new ScalarHandler<E>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn);
        }
        return null;
    }

    /**
     * 批处理操作
     * @param sql SQL 语句
     * @param args 填充占位符的 Object[] 类型可变参数
     */
    @Override
    public void batch(String sql, Object[]... args) {
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();
            queryRunner.batch(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn);
        }
    }
}
