package com.example.budget.service;

import com.example.budget.domein.entities.PereodicCost;
import com.example.budget.domein.entities.User;
import com.example.budget.domein.models.binding.ExpenseBindingModel;
import com.example.budget.domein.models.service.ExpenseServiceModel;
import com.example.budget.domein.models.service.PereodicCostServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.repository.PereodicCostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PereodicCostServiceImpl implements PereodicCostService {
    private final PereodicCostRepository costRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ExpenseService expenseService;

    @Autowired
    public PereodicCostServiceImpl(PereodicCostRepository costRepository,
                                   ModelMapper modelMapper,
                                   UserService userService,
                                   ExpenseService expenseService) {
        this.costRepository = costRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @Override
    public PereodicCostServiceModel createPereodicCost(PereodicCostServiceModel pereodicCostServiceModel) {
        PereodicCost pereodicCost = this.modelMapper
                .map(pereodicCostServiceModel, PereodicCost.class);
        return this.modelMapper.map(this.costRepository.save(pereodicCost), PereodicCostServiceModel.class);
    }

    @Override
    public List<PereodicCostServiceModel> findAllPereodicCost(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        return this.costRepository.findAllByUser(user)
                .stream()
                .map(p -> this.modelMapper.map(p, PereodicCostServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PereodicCostServiceModel findPereodicCostById(String id) {
        PereodicCost pereodicCost = this.costRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This id is not valid!"));
        return this.modelMapper.map(pereodicCost, PereodicCostServiceModel.class);
    }

    @Override
    public PereodicCostServiceModel editPereodicCost(String id, PereodicCostServiceModel model) {
        PereodicCost pereodicCost = this.costRepository
                .findById(id).orElse(null);
        if (null == pereodicCost) {
            throw new IllegalArgumentException("This id is not valid!");
        }
        pereodicCost.setName(model.getName());
        pereodicCost.setDay(model.getDay());
        pereodicCost.setValue(model.getValue());
        return this.modelMapper
                .map(this.costRepository.saveAndFlush(pereodicCost), PereodicCostServiceModel.class);
    }

    @Override
    public void deletePereodicCost(String id) {
        PereodicCost pereodicCost = this.costRepository
                .findById(id).orElse(null);
        if (null == pereodicCost) {
            throw new IllegalArgumentException("This id is noy valid!");
        }
        this.costRepository.delete(pereodicCost);
    }

    @Scheduled(fixedRate = 150000)
    private void updatePereodicCost() {
        Integer day = LocalDateTime.now().getDayOfMonth();
        List<PereodicCost> pereodicCosts = this.costRepository.findAllByDay(day);
        if (pereodicCosts.size() > 0) {
            for (PereodicCost pereodicCost : pereodicCosts) {
                UserServiceModel userServiceModel = this.userService
                        .findUserByUserName(pereodicCost.getUser().getUsername());
                ExpenseServiceModel expenseServiceModel = new ExpenseServiceModel();
                expenseServiceModel.setUser(userServiceModel);
                expenseServiceModel.setMounth(LocalDateTime.now().getMonthValue());
                expenseServiceModel.setDate(LocalDateTime.now());
                expenseServiceModel.setName(pereodicCost.getName());
                expenseServiceModel.setValue(pereodicCost.getValue());
                this.expenseService.createExpense(expenseServiceModel);
            }
        }
    }
}
