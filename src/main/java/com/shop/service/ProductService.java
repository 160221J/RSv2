package com.shop.service;

import com.shop.entity.Product;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Find by barcode
    public Product findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .orElse(null);
    }

    // Find by id
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    // Search by name (autocomplete)
    public List<Product> searchByName(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Product> searchProducts(String query) {

        List<Product> byName = productRepository.findByNameContainingIgnoreCase(query);
        List<Product> byBarcode = productRepository.findByBarcodeContaining(query);

        // merge results without duplicates
        Set<Product> result = new LinkedHashSet<>();
        result.addAll(byName);
        result.addAll(byBarcode);

        return new ArrayList<>(result);
    }

    // Add or update product
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Update quantity after sale or movement
    public void adjustQuantity(Long productId, int delta) {
        Product p = getById(productId);
        p.setQuantity(p.getQuantity() + delta);

        if (p.getQuantity() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        productRepository.save(p);
    }
}
