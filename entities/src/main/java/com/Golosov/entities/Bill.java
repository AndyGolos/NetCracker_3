package com.Golosov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("0")
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
        return password != null ? password.equals(bill.password) : bill.password == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (money ^ (money >>> 32));
        result = 31 * result + (password != null ? password.hashCode() : 0);
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
