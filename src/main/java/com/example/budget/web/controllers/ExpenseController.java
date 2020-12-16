package com.example.budget.web.controllers;

import com.example.budget.domein.models.binding.ExpenseBindingModel;
import com.example.budget.domein.models.service.ExpenseServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.domein.models.view.ExpenseViewModel;
import com.example.budget.service.ExpenseService;
import com.example.budget.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/expenses")
public class ExpenseController extends BaseController {
    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ModelMapper modelMapper, UserService userService) {
        this.expenseService = expenseService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addExpense(){
        return super.view("expense/add-expense");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addExpenseConfirm(@ModelAttribute ExpenseBindingModel model, Principal principal){
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        ExpenseServiceModel expenseServiceModel = this.modelMapper
                .map(model,ExpenseServiceModel.class);
        LocalDateTime nowTime = LocalDateTime.now();
        Integer month = nowTime.getMonthValue();
        expenseServiceModel.setDate(nowTime);
        expenseServiceModel.setMounth(month);
        expenseServiceModel.setUser(userServiceModel);
        this.expenseService.createExpense(expenseServiceModel);
        return super.redirect("/expenses/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllExpenses(ModelAndView modelAndView,Principal principal){
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        List<ExpenseViewModel> expenses = this.expenseService
                .findAllByUser(userServiceModel).stream()
                .map(e -> this.modelMapper.map(e,ExpenseViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("expenses",expenses);
        return super.view("/expense/all-expenses",modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editExpense(@PathVariable String id, ModelAndView modelAndView) {
        ExpenseServiceModel expenseServiceModel =
                this.expenseService.findExpenseById(id);
        ExpenseBindingModel model = this.modelMapper
                .map(expenseServiceModel,ExpenseBindingModel.class);

        modelAndView.addObject("expense", model);
        modelAndView.addObject("expenseId", id);

        return super.view("expense/edit-expense", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editExpenseConfirm(@PathVariable String id, @ModelAttribute ExpenseBindingModel model) {
        this.expenseService
                .editExpense(id,this.modelMapper.map(model,ExpenseServiceModel.class));
        return super.redirect("/expenses/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteExpense(@PathVariable String id, ModelAndView modelAndView) {
        ExpenseServiceModel expenseServiceModel = this.expenseService.findExpenseById(id);
        ExpenseBindingModel model = this.modelMapper
                .map(expenseServiceModel,ExpenseBindingModel.class);

        modelAndView.addObject("expense", model);
        modelAndView.addObject("expenseId", id);

        return super.view("expense/delete-expense", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteExpenseConfirm(@PathVariable String id) {
        this.expenseService.deleteExpense(id);
        return super.redirect("/expenses/all");
    }
}
