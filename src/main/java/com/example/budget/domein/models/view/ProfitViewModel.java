package com.example.budget.domein.models.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProfitViewModel {
    private String id;
    private String name;
    private BigDecimal value;
    private LocalDateTime date;

    public ProfitViewModel() {
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

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
