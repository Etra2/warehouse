package com.inventorymanagement.service;

import com.inventorymanagement.model.Product;
import com.inventorymanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByQuantityLessThan(threshold);
    }

    public void updateProductPrice(Long id, double newPrice) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono produktu o ID: " + id));
        product.setPrice(newPrice);
        productRepository.save(product);
    }

    public void updateProductDescription(Long id, String newDescription) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono produktu o ID: " + id));
        product.setDescription(newDescription);
        productRepository.save(product);
    }

    public void updateProductQuantity(Long id, int newQuantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono produktu o ID: " + id));
        product.setQuantity(newQuantity);
        productRepository.save(product);
    }
}
