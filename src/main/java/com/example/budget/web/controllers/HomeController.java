package com.example.budget.web.controllers;

import com.example.budget.service.ExpenseService;
import com.example.budget.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
public class HomeController extends BaseController {
    private final ProfitService profitService;
    private final ExpenseService expenseService;

    @Autowired
    public HomeController(ProfitService profitService, ExpenseService expenseService) {
        this.profitService = profitService;
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Principal principal) {
        BigDecimal profit = this.profitService.allSummValue(principal.getName());
        BigDecimal expense = this.expenseService.allSumValue(principal.getName());
        if (null == profit){
            profit = new BigDecimal("0");
            profit.add(BigDecimal.valueOf(0));
        }
        if (null == expense){
            expense = new BigDecimal("0");
            expense.add(BigDecimal.valueOf(0));
        }
        BigDecimal result = profit.subtract(expense);

        modelAndView.addObject("balance",result);
        return super.view("home",modelAndView);
    }
}
