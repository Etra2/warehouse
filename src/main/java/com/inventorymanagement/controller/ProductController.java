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

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/low-stock")
    public String lowStockProducts(Model model) {
        model.addAttribute("products", productService.getLowStockProducts(10)); // próg niskiego stanu
        return "product/low_stock";
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

}


