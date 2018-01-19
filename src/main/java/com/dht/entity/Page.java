package com.dht.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h3>商城页面类</h3>
 * @author dht925nerd@126.com
 */
public class Page<T> {

    /**
     * 当前页码
     */
    private int pageNo;

    /**
     * 当前页的商品信息列表
     */
    @Getter
    @Setter
    private List<T> list;

    /**
     * 每页可以显示的商品信息数
     */
    @Getter
    private int pageSize = 3;

    /**
     * 商品总数
     */
    @Setter
    private long totalItemNumber;

    /**
     * 构造器中需要对当前页码初始化
     * @param pageNo 当前页码
     */
    public Page(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        //校验页码是否合法
        if (pageNo <= 0){ pageNo = 1; }
        if (pageNo > getTotalPageNumber()){ pageNo = getTotalPageNumber(); }
        return pageNo;
    }

    public int getTotalPageNumber(){
        //页面总数通过商品总数和每页可显示的商品数计算得出
        int totalPageNumber = (int) (totalItemNumber / pageSize);
        if (totalItemNumber % pageSize != 0){
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public boolean isHasPrev(){ return getPageNo() > 1; }

    public boolean isHasNext(){ return getPageNo() < getTotalPageNumber(); }

    public int getPrevPage(){
        if (isHasPrev()){
            return getPageNo() - 1;
        }
        return getPageNo();
    }

    public int getNextPage(){
        if (isHasNext()){
            return getPageNo() + 1;
        }
        return getPageNo();
    }

}
