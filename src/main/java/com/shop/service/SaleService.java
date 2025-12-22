package com.shop.service;

import com.shop.entity.Product;
import com.shop.entity.Sale;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.SaleRepository;
import com.shop.service.StockMovementService;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final StockMovementService movementService;

    public Sale processSale(Long productId, int quantity, double salePrice) {

        Product product = productService.getById(productId);

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        double profit = (salePrice - product.getCostPrice()) * quantity;

        // Reduce stock
        productService.adjustQuantity(productId, -quantity);

        // Log stock movement
        movementService.logMovement(product, "sale", -quantity, "Sale processed");

        // Save sale record
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantity(quantity);
        sale.setSalePrice(salePrice);
        sale.setProfit(profit);

        return saleRepository.save(sale);
    }
}
