package com.example.budget.repository;

import com.example.budget.domein.entities.PereodicIncome;
import com.example.budget.domein.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PereodicIncomeRepository extends JpaRepository<PereodicIncome,String> {
    List<PereodicIncome>findAllByUser(User user);
    List<PereodicIncome>findAllByDay(Integer day);
}
