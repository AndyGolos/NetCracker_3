package com.golosov.builders;


import com.golosov.entities.Bill;
import com.golosov.entities.Card;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public class BillBuilder {

    private BillBuilder() {
    }

    private final Bill bill = new Bill();

    public static class BillEntityBuilder {

        private Bill bill = new Bill();

        public BillEntityBuilder id(long id) {
            this.bill.setId(id);
            return this;
        }

        public BillEntityBuilder password(String password) {
            this.bill.setPassword(password);
            return this;
        }

        public BillEntityBuilder money(long money) {
            this.bill.setMoney(money);
            return this;
        }

        public BillEntityBuilder cards(Set<Card> cards) {
            this.bill.setCards(cards);
            return this;
        }

        public Bill build() {
            return new BillBuilder().createBill(this);
        }
    }

    private Bill createBill(BillEntityBuilder builder) {
        this.bill.setId(builder.bill.getId());
        this.bill.setMoney(builder.bill.getMoney());
        this.bill.setPassword(builder.bill.getPassword());
        this.bill.setCards(builder.bill.getCards());
        return this.bill;
    }


}
