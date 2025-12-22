package com.shop.service;

import com.shop.entity.Withdrawal;
import com.shop.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;

    public Withdrawal addWithdrawal(Withdrawal withdrawal) {
        return withdrawalRepository.save(withdrawal);
    }
}
