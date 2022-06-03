package com.micropos.amazondatabase.service;

import com.micropos.amazondatabase.model.Cart;
import com.micropos.amazondatabase.model.Item;
import com.micropos.amazondatabase.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosServiceImpl implements PosService{

    @Autowired
    private DataBaseService dataBaseService;

    @Override
    public void checkout(Cart cart) {

    }

    @Override
    public Cart add(Cart cart, Product product, int amount) {
        return add(cart, product.getAsin(), amount);
    }

    @Override
    public Cart add(Cart cart, String productId, int amount) {
        Product product = dataBaseService.getProduct(productId);
        if(product == null){
            return cart;
        }
        cart.addItem(new Item(product, amount));
        return cart;
    }

    @Override
    public List<Product> products() {
        return dataBaseService.getProducts();
    }

    @Override
    public List<Product> products(String category){
        if(category == null){
            return dataBaseService.getProducts();
        }else {
            return dataBaseService.getProducts(category);
        }
    }

    private Item getTarget(Cart cart, String productId){
        for (Item item : cart.getItems()) {
            if(item.getProduct().getAsin().equalsIgnoreCase(productId)){
                return item;
            }
        }
        return null;
    }

    @Override
    public Cart increase(Cart cart, String productId, int amount){
        Item target = getTarget(cart, productId);
        int q = target.getQuantity() + amount;
        if(q > 0){
            target.setQuantity(q);
        }else{
            cart.getItems().remove(target);
        }
        return cart;
    }

    @Override
    public Cart remove(Cart cart, String productId){
        Item target = getTarget(cart, productId);
        cart.getItems().remove(target);
        return cart;
    }

    @Override
    public List<String> getCategories(){
        return dataBaseService.getCategories();
    }

    @Override
    public Product randomProduct() {
        return null;
    }
}
