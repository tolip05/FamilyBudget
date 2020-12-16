package com.example.budget.domein.models.service;

import java.math.BigDecimal;

public class PereodicIncomeServiceModel extends BaseServiceModel {
    private String name;
    private BigDecimal value;
    private Integer day;
    private UserServiceModel user;

    public PereodicIncomeServiceModel() {
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

    public Integer getDay() {
        return this.day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public UserServiceModel getUser() {
        return this.user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
