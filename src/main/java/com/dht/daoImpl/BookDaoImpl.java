package com.dht.daoImpl;

import com.dht.dao.BookDao;
import com.dht.entity.Book;
import com.dht.entity.CriteriaBook;
import com.dht.entity.Page;
import com.dht.entity.ShoppingCartItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <h3>书籍信息数据访问实现类</h3>
 * @author dht925nerd@126.com
 */
public class BookDaoImpl
        extends BaseDao<Book>
        implements BookDao{

    /**
     * 根据书籍 id 获取书籍信息
     * @param id 书籍 id
     * @return id 对应的书籍信息
     */
    @Override
    public Book getBook(int id) {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
        return get(sql, id);
    }

    /**
     * 获取按照条件查找书籍的结果页面(默认页面大小为 3)
     * @param criteria 查询条件
     * @return 结果页面
     */
    @Override
    public Page<Book> getPage(CriteriaBook criteria) {
        Page<Book> page = new Page<>(criteria.getPageNo());

        page.setTotalItemNumber(getTotalBookNumber(criteria));
        criteria.setPageNo(page.getPageNo());
        page.setList(getPageList(criteria, 3));

        return page;
    }

    /**
     * 获取符合查找条件的书籍总数
     * @param criteria 查询条件
     * @return 符合条件的书籍总数
     */
    @Override
    public long getTotalBookNumber(CriteriaBook criteria) {
        String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
        return getForValue(sql, criteria.getMinPrice(), criteria.getMaxPrice());
    }

    /**
     * 获取按照条件查找书籍的结果页面,并指定页面大小(返回页面列表)
     * @param criteria 查询条件
     * @param pageSize 页面大小
     * @return 结果页面列表
     */
    @Override
    public List<Book> getPageList(CriteriaBook criteria, int pageSize) {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks " +
                "WHERE price >= ? AND price <= ? " +
                "LIMIT ?, ?";

        return getForList(sql, criteria.getMinPrice(), criteria.getMaxPrice(),
                (criteria.getPageNo() - 1) * pageSize, pageSize);
    }

    /**
     * 根据书籍 id 获取数据库存
     * @param id 书籍 id
     * @return 库存数
     */
    @Override
    public int getStoreNumber(Integer id) {
        String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
        return getForValue(sql, id);
    }

    /**
     * 批量更新书籍库存和销量
     * @param items 购物车商品条目列表
     */
    @Override
    public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
        String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, " +
                "storeNumber = storeNumber - ? " +
                "WHERE id = ?";
        Object [][] params;
        params = new Object[items.size()][3];
        List<ShoppingCartItem> scis = new ArrayList<>(items);
        for(int i = 0; i < items.size(); i++){
            params[i][0] = scis.get(i).getQuantity();
            params[i][1] = scis.get(i).getQuantity();
            params[i][2] = scis.get(i).getBook().getId();
        }
        batch(sql, params);
    }
}
