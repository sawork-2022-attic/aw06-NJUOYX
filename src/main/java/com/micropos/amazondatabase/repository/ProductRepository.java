package com.micropos.amazondatabase.repository;

import com.micropos.amazondatabase.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(
            value = "select main_cat from product group by main_cat",
            nativeQuery = true)
    Collection<String> findCategories();
}