package com.example.budget.web.controllers;

import com.example.budget.domein.models.binding.PereodicIncomeBindingModel;
import com.example.budget.domein.models.service.PereodicIncomeServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.domein.models.view.PereodicCostViewModel;
import com.example.budget.domein.models.view.PereodicIncomeViewModel;
import com.example.budget.service.PereodicIncomeService;
import com.example.budget.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pereodicinc")
public class PeriodicIncomeController extends BaseController {
    private final PereodicIncomeService pereodicIncomeService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public PeriodicIncomeController(PereodicIncomeService pereodicIncomeService,
                                    ModelMapper modelMapper, UserService userService) {
        this.pereodicIncomeService = pereodicIncomeService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addPereodicIncome() {
        return super.view("pereodicin/add-pereodicin");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addPereodicIncomeConfirm(@ModelAttribute PereodicIncomeBindingModel model,
                                                 Principal principal) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        PereodicIncomeServiceModel pereodicIncomeServiceModel = this.modelMapper
                .map(model, PereodicIncomeServiceModel.class);
        pereodicIncomeServiceModel.setUser(userServiceModel);
        this.pereodicIncomeService.createPereodicIncome(pereodicIncomeServiceModel);
        return super.redirect("/pereodicinc/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllPereodicIncomes(ModelAndView modelAndView, Principal principal) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(principal.getName());
        List<PereodicIncomeViewModel> pereodicIncomeViewModelList = this.pereodicIncomeService
                .findAllPereodicIncomes(userServiceModel)
                .stream()
                .map(p -> this.modelMapper.map(p, PereodicIncomeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("pereodicin", pereodicIncomeViewModelList);
        return super.view("/pereodicin/all-periodic", modelAndView);
    }
    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editPereodicIncome(@PathVariable String id, ModelAndView modelAndView) {
        PereodicIncomeServiceModel pereodicIncomeServiceModel =
                this.pereodicIncomeService.findPereodicIncomeById(id);
        PereodicIncomeBindingModel pereodicIncomeBindingModel
                = this.modelMapper.map(pereodicIncomeServiceModel,PereodicIncomeBindingModel.class);

        modelAndView.addObject("pereodicIncome", pereodicIncomeBindingModel);
        modelAndView.addObject("pereodicinId", id);

        return super.view("pereodicin/edit-periodic", modelAndView);
    }
    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editPereodicIncome(@PathVariable String id,
                                           @ModelAttribute PereodicIncomeBindingModel model) {
        this.pereodicIncomeService.editPereodicIncome(id, this.modelMapper.map(model, PereodicIncomeServiceModel.class));
        return super.redirect("/pereodicinc/all");
    }
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deletePereodicIncome(@PathVariable String id, ModelAndView modelAndView) {
        PereodicIncomeServiceModel profitServiceModel = this.pereodicIncomeService
                .findPereodicIncomeById(id);
        PereodicIncomeBindingModel model = this.modelMapper
                .map(profitServiceModel,PereodicIncomeBindingModel.class);

        modelAndView.addObject("pereodicinc", model);
        modelAndView.addObject("pereodicincId", id);

        return super.view("pereodicin/delete-periodic", modelAndView);
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deletePereodicIncome(@PathVariable String id) {
        this.pereodicIncomeService.deletePereodicIncome(id);
        return super.redirect("/pereodicinc/all");
    }
}
