package com.example.budget.service;

import com.example.budget.domein.entities.PereodicIncome;
import com.example.budget.domein.entities.User;
import com.example.budget.domein.models.service.PereodicIncomeServiceModel;
import com.example.budget.domein.models.service.ProfitServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.repository.PereodicIncomeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PereodicIncomeServiceImpl implements PereodicIncomeService {
    private final PereodicIncomeRepository repository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProfitService profitService;


    @Autowired
    public PereodicIncomeServiceImpl(PereodicIncomeRepository repository, ModelMapper modelMapper, UserService userService, ProfitService profitService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.profitService = profitService;
    }

    @Override
    public PereodicIncomeServiceModel createPereodicIncome(PereodicIncomeServiceModel model) {
        PereodicIncome pereodicIncome =
                this.modelMapper.map(model,PereodicIncome.class);
        return this.modelMapper.map(this.repository.save(pereodicIncome),PereodicIncomeServiceModel.class);
    }

    @Override
    public List<PereodicIncomeServiceModel> findAllPereodicIncomes(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        return this.repository.findAllByUser(user)
                .stream().map(f -> this.modelMapper.map(f,PereodicIncomeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PereodicIncomeServiceModel findPereodicIncomeById(String id) {
        PereodicIncome pereodicIncome = this.repository.findById(id)
                .orElse(null);
        if (null == pereodicIncome){
            throw new IllegalArgumentException("This id is not correct!");
        }
        return this.modelMapper.map(pereodicIncome,PereodicIncomeServiceModel.class);
    }

    @Override
    public PereodicIncomeServiceModel editPereodicIncome(String id, PereodicIncomeServiceModel model) {
        PereodicIncome pereodicIncome = this.repository.findById(id)
                .orElse(null);
        if (null == pereodicIncome){
            throw new IllegalArgumentException("This id is not correct!");
        }
        pereodicIncome.setName(model.getName());
        pereodicIncome.setDay(model.getDay());
        pereodicIncome.setValue(model.getValue());
        return this.modelMapper
                .map(this.repository.saveAndFlush(pereodicIncome),PereodicIncomeServiceModel.class);
    }

    @Override
    public void deletePereodicIncome(String id) {
        PereodicIncome pereodicIncome = this.repository.findById(id)
                .orElse(null);
        if (null == pereodicIncome){
            throw new IllegalArgumentException("This id is not correct!");
        }
        this.repository.delete(pereodicIncome);
    }

    @Scheduled(fixedRate = 150000)
    private void updatePereodicIncomes() {
        Integer day = LocalDateTime.now().getDayOfMonth();
        List<PereodicIncome>pereodicIncomes = this.repository.
                findAllByDay(day);
        if (pereodicIncomes.size() != 0){
            for (PereodicIncome pereodicIncome : pereodicIncomes) {
                UserServiceModel userServiceModel =
                        this.userService
                                .findUserByUserName(pereodicIncome.getUser().getUsername());
                ProfitServiceModel profitServiceModel =
                        new ProfitServiceModel();
                profitServiceModel.setMounth(LocalDateTime.now().getMonthValue());
                profitServiceModel.setUser(userServiceModel);
                profitServiceModel.setDate(LocalDateTime.now());
                profitServiceModel.setName(pereodicIncome.getName());
                profitServiceModel.setValue(pereodicIncome.getValue());
                this.profitService.createProfit(profitServiceModel);
            }
        }
    }
}
