package com.example.budget.domein.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "profits")
public class Profit extends BaseEntity{
    private String name;
    private BigDecimal value;
    private LocalDateTime date;
    private Integer mounth;
    private User user;

    public Profit() {
    }
    @Column(name = "profits_names", nullable = false)
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
    @Column(name = "dates")
    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    @Column(name = "mounths")
    public Integer getMounth() {
        return this.mounth;
    }

    public void setMounth(Integer mounth) {
        this.mounth = mounth;
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
