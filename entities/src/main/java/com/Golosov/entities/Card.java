package com.Golosov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Entity
public class Card extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "bill_id" , nullable = false)
    private Bill bill;
    public Bill getBill() {
        return bill;
    }
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @ManyToOne
    @JoinColumn(name = "card_type_id", nullable = false)
    private Type type;
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<History> histories = new HashSet<>();
    public Set<History> getHistories() {
        return histories;
    }
    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

    @Column
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    @ColumnDefault("true")
    private boolean status;
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column
    private LocalDate registration;
    public LocalDate getRegistration() {
        return registration;
    }
    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    @Column
    private LocalDate validity;
    public LocalDate getValidity() {
        return validity;
    }
    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Card card = (Card) o;

        if (status != card.status) return false;
        if (!user.equals(card.user)) return false;
        if (!bill.equals(card.bill)) return false;
        if (!type.equals(card.type)) return false;
        if (!password.equals(card.password)) return false;
        if (!registration.equals(card.registration)) return false;
        return validity.equals(card.validity);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + bill.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + registration.hashCode();
        result = 31 * result + validity.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "user=" + user +
                ", bill=" + bill +
                ", type=" + type +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", registration=" + registration +
                ", validity=" + validity +
                '}';
    }
}

