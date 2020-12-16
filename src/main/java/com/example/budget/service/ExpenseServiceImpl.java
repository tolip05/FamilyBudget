package com.example.budget.service;

import com.example.budget.domein.entities.Expense;
import com.example.budget.domein.entities.User;
import com.example.budget.domein.models.service.ExpenseServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              ModelMapper modelMapper, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public ExpenseServiceModel createExpense(ExpenseServiceModel expenseServiceModel) {
        Expense expense = this.modelMapper.map(expenseServiceModel, Expense.class);
        expense = this.expenseRepository.save(expense);
        return this.modelMapper.map(expense, ExpenseServiceModel.class);
    }

    @Override
    public List<ExpenseServiceModel> findAllExpense() {
        return this.expenseRepository
                .findAll()
                .stream()
                .map(e -> this.modelMapper.map(e, ExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseServiceModel findExpenseById(String id) {
        return this.expenseRepository
                .findById(id)
                .map(e -> this.modelMapper.map(e, ExpenseServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Expense with the given id was not found!"));
    }

    @Override
    public ExpenseServiceModel editExpense(String id, ExpenseServiceModel expenseServiceModel) {
        Expense expense = this.expenseRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense with the given id was not found!"));
        expense.setName(expenseServiceModel.getName());
        expense.setValue(expenseServiceModel.getValue());
        expense.setDate(LocalDateTime.now());
        expense.setMounth(LocalDateTime.now().getMonthValue());

        return this.modelMapper
                .map(this.expenseRepository
                        .saveAndFlush(expense), ExpenseServiceModel.class);
    }

    @Override
    public void deleteExpense(String id) {
        Expense expense = this.expenseRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense with the given id was not found!"));
        this.expenseRepository.delete(expense);
    }

    @Override
    public BigDecimal allSumValue(String username) {
        UserServiceModel userServiceModel = this.userService.findUserByUserName(username);
        User user = this.modelMapper.map(userServiceModel,User.class);
        return this.expenseRepository.sumAll(user);
    }

    @Override
    public List<ExpenseServiceModel> findAllByUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        return this.expenseRepository.findAllByUserOrderByDateDesc(user)
                .stream()
                .map(e -> this.modelMapper.map(e, ExpenseServiceModel.class))
                .collect(Collectors.toList());
    }
}
