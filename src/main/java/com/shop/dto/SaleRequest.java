package com.shop.dto;

import lombok.Data;

@Data
public class SaleRequest {
    private Long productId;
    private int quantity;
    private double salePrice;
}
