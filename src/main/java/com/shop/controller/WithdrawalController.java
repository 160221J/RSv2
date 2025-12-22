package com.shop.controller;

import com.shop.entity.Withdrawal;
import com.shop.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/withdrawals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @PostMapping
    public Withdrawal addWithdrawal(@RequestBody Withdrawal withdrawal) {
        return withdrawalService.addWithdrawal(withdrawal);
    }
}
