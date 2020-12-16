package com.example.budget.domein.models.view;

import java.math.BigDecimal;

public class PereodicIncomeViewModel {
    private String id;
    private String name;
    private BigDecimal value;
    private Integer day;

    public PereodicIncomeViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
