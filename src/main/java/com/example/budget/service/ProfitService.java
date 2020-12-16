package com.example.budget.service;

import com.example.budget.domein.models.service.ProfitServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProfitService {
    ProfitServiceModel createProfit(ProfitServiceModel profitServiceModel);

    List<ProfitServiceModel> findAllProfits();

    ProfitServiceModel findProfitById(String id);

    ProfitServiceModel editProfit(String id, ProfitServiceModel profitServiceModel);

    void deleteProfit(String id);

    BigDecimal allSummValue(String username);

    List<ProfitServiceModel>findAllByUser(UserServiceModel userServiceModel);
}
