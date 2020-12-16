package com.example.budget.service;

import com.example.budget.domein.entities.Profit;
import com.example.budget.domein.entities.User;
import com.example.budget.domein.models.service.ProfitServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;
import com.example.budget.repository.ProfitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfitServiceImpl implements ProfitService {
    private final ProfitRepository profitRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ProfitServiceImpl(ProfitRepository profitRepository,
                             ModelMapper modelMapper,
                             UserService userService) {
        this.profitRepository = profitRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public ProfitServiceModel createProfit(ProfitServiceModel profitServiceModel) {
       Profit profit = this.modelMapper
               .map(profitServiceModel,Profit.class);
        return this.modelMapper
                .map(this.profitRepository.save(profit),ProfitServiceModel.class);
    }

    @Override
    public List<ProfitServiceModel> findAllProfits() {
        return this.profitRepository.findAllByOrderByDateAsc()
                .stream()
                .map(p -> this.modelMapper.map(p,ProfitServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfitServiceModel findProfitById(String id) {
        Profit profit = this.profitRepository
                .findById(id).orElse(null);
        ProfitServiceModel profitServiceModel
                = this.modelMapper
                .map(profit,ProfitServiceModel.class);
        return profitServiceModel;
    }

    @Override
    public ProfitServiceModel editProfit(String id, ProfitServiceModel profitServiceModel) {
        Profit profit = this.profitRepository
                .findById(id)
                .orElse(null);
        if (null == profit){
            throw new IllegalArgumentException("This id is not exist!");
        }
        profit.setName(profitServiceModel.getName());
        profit.setDate(LocalDateTime.now());
        profit.setMounth(LocalDateTime.now().getMonthValue());
        profit.setValue(profitServiceModel.getValue());
        return this.modelMapper
                .map(this.profitRepository.saveAndFlush(profit),ProfitServiceModel.class);
    }

    @Override
    public void deleteProfit(String id) {
        Profit profit = this.profitRepository
                .findById(id)
                .orElse(null);
        if (null == profit){
            throw new IllegalArgumentException("This id is not exist!");
        }
        this.profitRepository.delete(profit);
    }

    @Override
    public BigDecimal allSummValue(String username) {
        UserServiceModel userServiceModel = this.userService
                .findUserByUserName(username);
        User user = this.modelMapper.map(userServiceModel,User.class);
        return this.profitRepository.sumAll(user);
    }

    @Override
    public List<ProfitServiceModel> findAllByUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        return this.profitRepository.findAllByUserOrderByDateDesc(user)
                .stream()
                .map(p-> this.modelMapper.map(p,ProfitServiceModel.class))
                .collect(Collectors.toList());
    }
}
