package com.example.budget.repository;

import com.example.budget.domein.entities.Profit;
import com.example.budget.domein.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfitRepository extends JpaRepository<Profit,String> {
 Optional<Profit>findByName(String name);
 List<Profit>findAllByUserOrderByDateDesc(User user);
 @Query("select sum(p.value) from Profit as p where p.user =:user")
 BigDecimal sumAll(@Param("user") User user);

 List<Profit> findAllByOrderByDateAsc();

}
