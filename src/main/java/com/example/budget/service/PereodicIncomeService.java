package com.example.budget.service;

import com.example.budget.domein.models.service.PereodicCostServiceModel;
import com.example.budget.domein.models.service.PereodicIncomeServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;

import java.util.List;

public interface PereodicIncomeService {
    PereodicIncomeServiceModel createPereodicIncome(PereodicIncomeServiceModel model);

    List<PereodicIncomeServiceModel> findAllPereodicIncomes(UserServiceModel userServiceModel);

    PereodicIncomeServiceModel findPereodicIncomeById(String id);

    PereodicIncomeServiceModel editPereodicIncome(String id, PereodicIncomeServiceModel model);

    void deletePereodicIncome(String id);

}
