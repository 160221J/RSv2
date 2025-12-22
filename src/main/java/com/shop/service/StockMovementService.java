package com.shop.service;

import com.shop.entity.Product;
import com.shop.entity.StockMovement;
import com.shop.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public void logMovement(Product product, String type, int qty, String note) {

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(type);
        movement.setQuantity(qty);
        movement.setCostPriceAtTime(product.getCostPrice());
        movement.setSellingPriceAtTime(product.getSellingPrice());
        movement.setNote(note);

        stockMovementRepository.save(movement);
    }
}
