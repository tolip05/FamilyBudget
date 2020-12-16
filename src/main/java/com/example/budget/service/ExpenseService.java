package com.example.budget.service;

import com.example.budget.domein.models.service.ExpenseServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {
    ExpenseServiceModel createExpense(ExpenseServiceModel expenseServiceModel);

    List<ExpenseServiceModel> findAllExpense();

    ExpenseServiceModel findExpenseById(String id);

    ExpenseServiceModel editExpense(String id, ExpenseServiceModel expenseServiceModel);

    void deleteExpense(String id);

    BigDecimal allSumValue(String username);

    List<ExpenseServiceModel> findAllByUser(UserServiceModel userServiceModel);
}
