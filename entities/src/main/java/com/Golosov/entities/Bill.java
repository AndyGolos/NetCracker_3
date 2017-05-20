package com.Golosov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Entity
public class Bill extends BaseEntity {

    public Bill() {
        super();
    }

    @Column
    private long money;
    public long getMoney() {
        return money;
    }
    public void setMoney(long money) {
        this.money = money;
    }

    @Column
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Card> cards = new HashSet<>();
    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Bill bill = (Bill) o;

        if (money != bill.money) return false;
        return password.equals(bill.password);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (money ^ (money >>> 32));
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "money=" + money +
                ", password='" + password + '\'' +
                '}';
    }
}
