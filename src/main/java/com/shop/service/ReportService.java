package com.shop.service;

import com.shop.dto.DailyReportResponse;
import com.shop.entity.Expense;
import com.shop.entity.Sale;
import com.shop.entity.Withdrawal;
import com.shop.repository.ExpenseRepository;
import com.shop.repository.SaleRepository;
import com.shop.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SaleRepository saleRepository;
    private final ExpenseRepository expenseRepository;
    private final WithdrawalRepository withdrawalRepository;

    public DailyReportResponse generateReport(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        // Fetch data
        List<Sale> sales = saleRepository.findBySoldAtBetween(start, end);
        List<Expense> expenses = expenseRepository.findByDate(date);
        List<Withdrawal> withdrawals = withdrawalRepository.findByDate(date);

        // Calculate totals
        double salesTotal = sales.stream()
                .mapToDouble(s -> s.getSalePrice() * s.getQuantity())
                .sum();

        double salesProfit = sales.stream()
                .mapToDouble(Sale::getProfit)
                .sum();

        double expensesTotal = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double withdrawalsTotal = withdrawals.stream()
                .mapToDouble(Withdrawal::getAmount)
                .sum();

        double finalProfit = salesProfit - expensesTotal - withdrawalsTotal;

        // Build the response
        DailyReportResponse report = new DailyReportResponse();
        report.setDate(date);
        report.setSalesTotal(salesTotal);
        report.setSalesProfit(salesProfit);
        report.setExpensesTotal(expensesTotal);
        report.setWithdrawalsTotal(withdrawalsTotal);
        report.setFinalProfit(finalProfit);
        report.setSales(sales);
        report.setExpenses(expenses);
        report.setWithdrawals(withdrawals);

        return report;
    }
}
