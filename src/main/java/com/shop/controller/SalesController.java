package com.shop.controller;

import com.shop.dto.BulkSaleRequest;
import com.shop.dto.SaleRequest;
import com.shop.entity.Sale;
import com.shop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SalesController {

    private final SaleService saleService;

    @PostMapping("/single")
    public Sale processSingleSale(@RequestBody SaleRequest req) {
        return saleService.processSale(req.getProductId(), req.getQuantity(), req.getSalePrice());
    }

    @PostMapping("/bulk")
    public String processBulkSale(@RequestBody List<BulkSaleRequest> sales) {
        sales.forEach(s ->
                saleService.processSale(s.getProductId(), s.getQuantity(), s.getSalePrice())
        );
        return "Bulk sale completed";
    }
}
