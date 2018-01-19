package com.dht.entity;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>购物车类</h3>
 * @author dht925nerd@126.com
 */
public class ShoppingCart {

    @Getter
    private Map<Integer, ShoppingCartItem> books = new HashMap<>();

    public void updateItemQuantity(Integer id, int quantity){
        ShoppingCartItem sci =books.get(id);
        if(sci != null){
            sci.setQuantity(quantity);
        }
    }

    public void removeItem(Integer id){
        books.remove(id);
    }

    public void clear(){
        books.clear();
    }

    public boolean isEmpty(){
        return books.isEmpty();
    }

    public float getTotalMoney(){
        float total = 0;

        for(ShoppingCartItem sci: getItems()){
            total += sci.getItemMoney();
        }

        return total;
    }

    public Collection<ShoppingCartItem> getItems(){
        return books.values();
    }

    public int getBookNumber(){
        int total = 0;

        for(ShoppingCartItem sci: books.values()){
            total += sci.getQuantity();
        }

        return total;
    }

    public boolean hasBook(Integer id){
        return books.containsKey(id);
    }

    public void addBook(Book book){
        ShoppingCartItem sci = books.get(book.getId());

        if(sci == null){
            sci = new ShoppingCartItem(book);
            books.put(book.getId(), sci);
        }else{
            sci.increment();
        }
    }

}
