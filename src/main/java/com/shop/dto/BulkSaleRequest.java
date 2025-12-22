package com.shop.dto;

import lombok.Data;

@Data
public class BulkSaleRequest {
    private Long productId;
    private int quantity;
    private double salePrice;
}
