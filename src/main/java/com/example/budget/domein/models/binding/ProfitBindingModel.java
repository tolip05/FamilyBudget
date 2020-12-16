package com.example.budget.domein.models.binding;

import java.math.BigDecimal;

public class ProfitBindingModel {
    private String name;
    private BigDecimal value;

    public ProfitBindingModel() {
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
}
