package com.micropos.amazondatabase.repository;

import com.micropos.amazondatabase.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}