package com.dht.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * <h3>购物车商品条目类</h3>
 * @author dht925nerd@126.com
 */
public class ShoppingCartItem {

    @Getter
    @NonNull
    private Book book;

    @Getter
    @Setter
    private int quantity;

    public ShoppingCartItem(Book book) {
        this.book = book;
        this.quantity = 1;
    }

    public float getItemMoney(){
        return book.getPrice() * quantity;
    }

    public void increment(){
        quantity++;
    }
}
