package com.example.budget.domein.models.binding;

import java.math.BigDecimal;

public class PereodicIncomeBindingModel {
    private String name;
    private BigDecimal value;
    private Integer day;

    public PereodicIncomeBindingModel() {
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
}
