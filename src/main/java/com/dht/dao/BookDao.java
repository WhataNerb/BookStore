package com.dht.dao;

import com.dht.entity.Book;
import com.dht.entity.CriteriaBook;
import com.dht.entity.Page;
import com.dht.entity.ShoppingCartItem;

import java.util.Collection;
import java.util.List;

/**
 * <h3>书籍信息数据访问接口</h3>
 * 实现类: {@link com.dht.daoImpl.BookDaoImpl}
 * @author dht925nerd@126.com
 */
public interface BookDao {

    /**
     * 根据书籍 id 获取书籍信息
     * @param id 书籍 id
     * @return id 对应的书籍信息
     */
    Book getBook(int id);

    /**
     * 获取按照条件查找书籍的结果页面(所有结果在一页显示)
     * @param criteria 查询条件
     * @return 结果页面
     */
    Page<Book> getPage(CriteriaBook criteria);

    /**
     * 获取符合查找条件的书籍总数
     * @param criteria 查询条件
     * @return 符合条件的书籍总数
     */
    long getTotalBookNumber(CriteriaBook criteria);

    /**
     * 获取按照条件查找书籍的结果页面,并指定页面大小(返回页面列表)
     * @param criteria 查询条件
     * @param pageSize 页面大小
     * @return 结果页面列表
     */
    List<Book> getPageList(CriteriaBook criteria, int pageSize);

    /**
     * 根据书籍 id 获取数据库存
     * @param id 书籍 id
     * @return 库存数
     */
    int getStoreNumber(Integer id);

    /**
     * 批量更新书籍库存和销量
     * @param items 购物车商品条目列表
     */
    void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
}
