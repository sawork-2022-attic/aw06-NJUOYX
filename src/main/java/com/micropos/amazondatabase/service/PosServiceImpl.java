package com.micropos.amazondatabase.service;

import com.micropos.amazondatabase.model.Cart;
import com.micropos.amazondatabase.model.Item;
import com.micropos.amazondatabase.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Product randomProduct() {
        return null;
    }
}
