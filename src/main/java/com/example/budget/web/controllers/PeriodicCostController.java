package com.example.budget.web.controllers;

import com.example.budget.domein.entities.PereodicCost;
import com.example.budget.domein.models.binding.PereodicCostBindingModel;
import com.example.budget.domein.models.service.PereodicCostServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.domein.models.view.PereodicCostViewModel;
import com.example.budget.service.PereodicCostService;
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
@RequestMapping("/pereodcost")
public class PeriodicCostController extends BaseController {
    private final PereodicCostService pereodicCostService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public PeriodicCostController(PereodicCostService pereodicCostService,
                                  ModelMapper modelMapper, UserService userService) {
        this.pereodicCostService = pereodicCostService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }
    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addPereodicCost() {
        return super.view("pereodcos/add-periodcos");
    }
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addPereodicCostConfirm(@ModelAttribute PereodicCostBindingModel model,
                                                 Principal principal) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        PereodicCostServiceModel pereodicIncomeServiceModel = this.modelMapper
                .map(model, PereodicCostServiceModel.class);
        pereodicIncomeServiceModel.setUser(userServiceModel);
        this.pereodicCostService.createPereodicCost(pereodicIncomeServiceModel);
        return super.redirect("/pereodcost/all");
    }
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllPereodicCosts(ModelAndView modelAndView,
                                             Principal principal) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        List<PereodicCostViewModel> pereodicCostServiceModelLists =
                this.pereodicCostService.findAllPereodicCost(userServiceModel)
                .stream()
                .map(p -> this.modelMapper.map(p, PereodicCostViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("pereodiccost", pereodicCostServiceModelLists);
        return super.view("/pereodcos/all-periodcosts", modelAndView);
    }
    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editPereodicCost(@PathVariable String id, ModelAndView modelAndView) {
        PereodicCostServiceModel pereodicCostServiceModel =
                this.pereodicCostService.findPereodicCostById(id);
        PereodicCostBindingModel pereodicCostBindingModel
                = this.modelMapper.map(pereodicCostServiceModel,PereodicCostBindingModel.class);

        modelAndView.addObject("pereodiccost", pereodicCostBindingModel);
        modelAndView.addObject("pereodiccostId", id);

        return super.view("pereodcos/edit-periodicost", modelAndView);
    }
    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editPereodicCosted(@PathVariable String id,
                                           @ModelAttribute PereodicCostBindingModel model) {
        this.pereodicCostService.editPereodicCost(id, this.modelMapper.map(model, PereodicCostServiceModel.class));
        return super.redirect("/pereodcost/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deletePereodicIncome(@PathVariable String id, ModelAndView modelAndView) {
        PereodicCostServiceModel profitServiceModel = this.pereodicCostService
                .findPereodicCostById(id);
        PereodicCostBindingModel model = this.modelMapper
                .map(profitServiceModel,PereodicCostBindingModel.class);

        modelAndView.addObject("pereodicinc", model);
        modelAndView.addObject("pereodicincId", id);

        return super.view("pereodcos/delete-periodicost", modelAndView);
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deletePereodicIncome(@PathVariable String id) {
        this.pereodicCostService.deletePereodicCost(id);
        return super.redirect("/pereodcost/all");
    }
}
