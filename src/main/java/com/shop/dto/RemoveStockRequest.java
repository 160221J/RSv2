package com.shop.dto;

import lombok.Data;

@Data
public class RemoveStockRequest {
    private Long productId;
    private int quantity;
    private String type; // expired, damaged, consumed_owner, consumed_staff, lost
    private String note;
}
