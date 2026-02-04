package com.shop.controller;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.searchByName("");
    }

     @GetMapping("/search")
    public List<Product> search(@RequestParam String q) {
        return productService.searchProducts(q);
    }


    // Find by barcode
    @GetMapping("/barcode/{barcode}")
    public Product findByBarcode(@PathVariable String barcode) {
        return productService.findByBarcode(barcode);
    }

    // Create or update product
    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    // Update product fields
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updated) {
        Product product = productService.getById(id);

        product.setName(updated.getName());
        product.setBarcode(updated.getBarcode());
        product.setCategory(updated.getCategory());
        product.setCostPrice(updated.getCostPrice());
        product.setSellingPrice(updated.getSellingPrice());
        product.setQuantity(updated.getQuantity());
        product.setExpiryDate(updated.getExpiryDate());

        return productService.save(product);
    }
}
