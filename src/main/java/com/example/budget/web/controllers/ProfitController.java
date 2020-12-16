package com.example.budget.web.controllers;

import com.example.budget.domein.models.binding.ExpenseBindingModel;
import com.example.budget.domein.models.binding.ProfitBindingModel;
import com.example.budget.domein.models.service.ExpenseServiceModel;
import com.example.budget.domein.models.service.ProfitServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.domein.models.view.ExpenseViewModel;
import com.example.budget.domein.models.view.ProfitViewModel;
import com.example.budget.service.ProfitService;
import com.example.budget.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profits")
public class ProfitController extends BaseController {

    private final ProfitService profitService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ProfitController(ProfitService profitService,
                            ModelMapper modelMapper,
                            UserService userService) {
        this.profitService = profitService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addProfit() {
        return super.view("profit/add-profit");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addProfitConfirm(@ModelAttribute ProfitBindingModel model,
                                         Principal principal) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        ProfitServiceModel profitServiceModel = this.modelMapper
                .map(model, ProfitServiceModel.class);
        profitServiceModel.setUser(userServiceModel);
        profitServiceModel.setDate(LocalDateTime.now());
        profitServiceModel.setMounth(LocalDateTime.now().getMonthValue());
        this.profitService.createProfit(profitServiceModel);
        return super.redirect("/profits/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllProfits(ModelAndView modelAndView, Principal principal) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        List<ProfitViewModel> profits = this.profitService
                .findAllByUser(userServiceModel)
                .stream()
                .map(p -> this.modelMapper.map(p, ProfitViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("profits", profits);
        return super.view("/profit/all-profits", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfit(@PathVariable String id, ModelAndView modelAndView) {
        ProfitServiceModel profitServiceModel = this.profitService
                .findProfitById(id);
        ProfitBindingModel profitBindingModel = this.modelMapper
                .map(profitServiceModel, ProfitBindingModel.class);

        modelAndView.addObject("profit", profitBindingModel);
        modelAndView.addObject("profitId", id);

        return super.view("profit/edit-profit", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editExpenseConfirm(@PathVariable String id, @ModelAttribute ProfitBindingModel model) {
        this.profitService.editProfit(id, this.modelMapper.map(model, ProfitServiceModel.class));
        return super.redirect("/profits/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteProfit(@PathVariable String id, ModelAndView modelAndView) {
        ProfitServiceModel profitServiceModel = this.profitService
                .findProfitById(id);
        ProfitBindingModel model = this.modelMapper
                .map(profitServiceModel,ProfitBindingModel.class);

        modelAndView.addObject("profit", model);
        modelAndView.addObject("profitId", id);

        return super.view("profit/delete-profit", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteProfitConfirm(@PathVariable String id) {
        this.profitService.deleteProfit(id);
        return super.redirect("/profits/all");
    }
}
