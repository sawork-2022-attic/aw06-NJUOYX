package com.micropos.amazondatabase.service;

import com.micropos.amazondatabase.model.Product;
import com.micropos.amazondatabase.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product getProduct(String asin){
        Example<Product> example = Example.of(Product.fromAsin(asin));
        Optional<Product> res = productRepository.findOne(example);
        return res.orElse(null);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProducts(String category){
        Example<Product> example = Example.of(Product.fromCategory(category));
        return productRepository.findAll(example);
    }

    @Cacheable(value = "categories")
    public List<String> getCategories(){
        return List.of(productRepository.findCategories().toArray(new String[0]));
    }
}
