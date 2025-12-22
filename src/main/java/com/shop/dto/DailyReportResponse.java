package com.shop.dto;

import com.shop.entity.Expense;
import com.shop.entity.Sale;
import com.shop.entity.Withdrawal;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyReportResponse {

    private LocalDate date;

    private double salesTotal;
    private double salesProfit;
    private double expensesTotal;
    private double withdrawalsTotal;

    private double finalProfit;

    private List<Sale> sales;
    private List<Expense> expenses;
    private List<Withdrawal> withdrawals;
}
