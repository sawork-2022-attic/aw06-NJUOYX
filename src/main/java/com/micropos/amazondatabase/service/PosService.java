package com.micropos.amazondatabase.service;

import com.micropos.amazondatabase.model.Cart;
import com.micropos.amazondatabase.model.Product;

import java.util.List;

public interface PosService {

    void checkout(Cart cart);

    Cart add(Cart cart, Product product, int amount);

    Cart add(Cart cart, String productId, int amount);

    List<Product> products();

    Product randomProduct();
}