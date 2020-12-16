package com.example.budget.domein.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseServiceModel extends BaseServiceModel{
    private String name;
    private BigDecimal value;
    private LocalDateTime date;
    private Integer mounth;
    private UserServiceModel user;

    public ExpenseServiceModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getMounth() {
        return this.mounth;
    }

    public void setMounth(Integer mounth) {
        this.mounth = mounth;
    }

    public UserServiceModel getUser() {
        return this.user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
