package com.example.budget.service;

import com.example.budget.domein.models.service.PereodicCostServiceModel;
import com.example.budget.domein.models.service.UserServiceModel;

import java.util.List;

public interface PereodicCostService {
    PereodicCostServiceModel createPereodicCost(PereodicCostServiceModel pereodicCostServiceModel);

    List<PereodicCostServiceModel> findAllPereodicCost(UserServiceModel userServiceModel);

    PereodicCostServiceModel findPereodicCostById(String id);

    PereodicCostServiceModel editPereodicCost(String id, PereodicCostServiceModel pereodicCostServiceModel);

    void deletePereodicCost(String id);
}
