package com.inventorymanagement.controller;

import com.inventorymanagement.model.Product;
import com.inventorymanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Właściwy import dla Springa
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    @GetMapping("list")
    public String showListPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "list";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/new";
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return ResponseEntity.ok("Produkt został zapisany pomyślnie!");
    }
    @GetMapping("/low-stock")
    public String lowStockProducts(Model model) {
        model.addAttribute("products", productService.getLowStockProducts(10)); // próg niskiego stanu
        return "product/low_stock";
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product.getQuantity()==0) {
                productService.deleteProduct(id); // Wywołanie metody usuwania z serwisu
                return ResponseEntity.ok("Produkt został pomyślnie usunięty.");
            }else {
                return ResponseEntity.ok("Usunięcie niemożliwe - produkt ma stan dodatni");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nie udało się usunąć produktu.");
        }
    }

    @PutMapping("/updatePrice/{id}")
    public ResponseEntity<String> updateProductPrice(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        double newPrice = body.get("price");
        if (newPrice <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cena musi być większa od zera.");
        }
        productService.updateProductPrice(id, newPrice);
        return ResponseEntity.ok("Cena produktu została zaktualizowana.");
    }

    @PutMapping("/updateDescription/{id}")
    public ResponseEntity<String> updateProductDescription(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newDescription = body.get("description");
        if (newDescription == null || newDescription.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Opis nie może być pusty.");
        }
        productService.updateProductDescription(id, newDescription);
        return ResponseEntity.ok("Opis produktu został zaktualizowany.");
    }

    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Integer newQuantity = body.get("quantity");
        if (newQuantity == null || newQuantity < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ilość musi być dodatnia lub 0.");
        }
        productService.updateProductQuantity(id, newQuantity);
        return ResponseEntity.ok("Ilość produktu została zaktualizowana.");
    }
}


