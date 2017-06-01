package com.golosov.builders;

import com.golosov.entities.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public class CardBuilder {

    private CardBuilder(){
    }

    private final Card card = new Card();

    public static class CardEntityBuilder {

        private Card card = new Card();

        public CardEntityBuilder id(long id) {
            this.card.setId(id);
            return this;
        }

        public CardEntityBuilder user(User user) {
            this.card.setUser(user);
            return this;
        }

        public CardEntityBuilder bill(Bill bill) {
            this.card.setBill(bill);
            return this;
        }

        public CardEntityBuilder type(Type type) {
            this.card.setType(type);
            return this;
        }

        public CardEntityBuilder status(boolean status) {
            this.card.setStatus(status);
            return this;
        }

        public CardEntityBuilder password(String password) {
            this.card.setPassword(password);
            return this;
        }

        public CardEntityBuilder validity(LocalDate validity) {
            this.card.setValidity(validity);
            return this;
        }

        public CardEntityBuilder registration(LocalDate registration) {
            this.card.setRegistration(registration);
            return this;
        }

        public CardEntityBuilder histories(Set<History> histories) {
            this.card.setHistories(histories);
            return this;
        }

        public Card build() {
            return new CardBuilder().createCard(this);
        }

    }

    private Card createCard(CardEntityBuilder builder) {
        this.card.setId(builder.card.getId());
        this.card.setUser(builder.card.getUser());
        this.card.setBill(builder.card.getBill());
        this.card.setType(builder.card.getType());
        this.card.setStatus(builder.card.isStatus());
        this.card.setPassword(builder.card.getPassword());
        this.card.setRegistration(builder.card.getRegistration());
        this.card.setValidity(builder.card.getValidity());
        this.card.setHistories(builder.card.getHistories());
        return this.card;
    }
}
