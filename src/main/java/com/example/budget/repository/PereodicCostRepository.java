package com.example.budget.repository;

import com.example.budget.domein.entities.PereodicCost;
import com.example.budget.domein.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PereodicCostRepository extends JpaRepository<PereodicCost,String> {
    List<PereodicCost>findAllByUser(User user);
    List<PereodicCost>findAllByDay(Integer day);
}
