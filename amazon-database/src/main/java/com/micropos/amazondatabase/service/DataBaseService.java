package com.micropos.amazondatabase.service;

import com.micropos.amazondatabase.model.Product;
import com.micropos.amazondatabase.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataBaseService {

    @Autowired
    private ProductRepository productRepository;

    public void addAll(List<Product> products){
        productRepository.saveAllAndFlush(products);
    }

    public void add(Product product){
        productRepository.save(product);
    }

    public void flush(){
        productRepository.flush();
    }
}
