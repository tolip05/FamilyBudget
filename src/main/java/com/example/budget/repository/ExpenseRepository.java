package com.example.budget.repository;

import com.example.budget.domein.entities.Expense;
import com.example.budget.domein.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,String> {
 Optional<Expense>findByName(String name);
 List<Expense>findAllByUserOrderByDateDesc(User user);
 @Query("select sum(e.value) from Expense as e where e.user =:user")
 BigDecimal sumAll(@Param("user") User user);

 List<Expense> findByOrderByValue();
}
