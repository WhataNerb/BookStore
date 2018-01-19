package com.dht.utils;

import com.dht.entity.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author dht925nerd@126.com
 */
public class BookStoreWebUtils {

    /**
     * 从 session 中建立或获取购物车信息
     * @param request
     * @return 购物车信息
     */
    public static ShoppingCart getShoppingCart(HttpServletRequest request){
        HttpSession session = request.getSession();

        ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
        if(sc == null){
            sc = new ShoppingCart();
            session.setAttribute("ShoppingCart", sc);
        }

        return sc;
    }
}
