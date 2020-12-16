package com.example.budget.domein.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pereodic_co")
public class PereodicCost extends BaseEntity {
    private String name;
    private BigDecimal value;
    private Integer day;
    private User user;

    public PereodicCost() {
    }

    @Column(name = "pereodic_in_names", nullable = false)
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
    @Column(name = "days",nullable = false)
    public Integer getDay() {
        return this.day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
