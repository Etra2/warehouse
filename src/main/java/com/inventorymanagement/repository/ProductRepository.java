package com.inventorymanagement.repository;

import com.inventorymanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByQuantityLessThan(int quantity);
    Product getById(Long id);
}

