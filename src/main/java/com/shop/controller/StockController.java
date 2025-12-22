package com.shop.controller;

import com.shop.dto.RemoveStockRequest;
import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StockController {

    private final ProductService productService;
    private final StockMovementService movementService;

    // Add stock (arrival)
    @PostMapping("/add")
    public Product addStock(@RequestBody Product request) {
        Product existing = null;

        if (request.getBarcode() != null) {
            existing = productService.findByBarcode(request.getBarcode());
        }

        if (existing == null && request.getName() != null) {
            // Search by name if barcode is missing
            var matches = productService.searchByName(request.getName());
            if (!matches.isEmpty()) existing = matches.get(0);
        }

        if (existing == null) {
            // Create new product
            request.setId(null);
            Product saved = productService.save(request);
            movementService.logMovement(saved, "arrival", request.getQuantity(), "New item added");
            return saved;
        }

        // Update existing stock quantity
        existing.setQuantity(existing.getQuantity() + request.getQuantity());
        existing.setCostPrice(request.getCostPrice());
        existing.setSellingPrice(request.getSellingPrice());

        Product saved = productService.save(existing);
        movementService.logMovement(saved, "arrival", request.getQuantity(), "Stock increased");

        return saved;
    }

    // Remove stock
    @PostMapping("/remove")
    public String removeStock(@RequestBody RemoveStockRequest req) {

        Product product = productService.getById(req.getProductId());

        if (product.getQuantity() < req.getQuantity())
            throw new IllegalArgumentException("Not enough stock");

        productService.adjustQuantity(req.getProductId(), -req.getQuantity());

        movementService.logMovement(product, req.getType(), -req.getQuantity(), req.getNote());

        return "Stock removed successfully";
    }
}
