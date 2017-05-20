package com.Golosov.builders;

import com.Golosov.entities.Card;
import com.Golosov.entities.Role;
import com.Golosov.entities.User;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public class UserBuilder {

    private UserBuilder() {
    }

    private final User user = new User();

    public static class UserEntityBuilder {

        private User user = new User();

        public UserEntityBuilder id(long id) {
            this.user.setId(id);
            return this;
        }

        public UserEntityBuilder name(String name) {
            this.user.setName(name);
            return this;
        }

        public UserEntityBuilder surname(String surname) {
            this.user.setSurname(surname);
            return this;
        }

        public UserEntityBuilder lastname(String lastname) {
            this.user.setLastname(lastname);
            return this;
        }

        public UserEntityBuilder email(String email) {
            this.user.setEmail(email);
            return this;
        }

        public UserEntityBuilder password(String password) {
            this.user.setPassword(password);
            return this;
        }

        public UserEntityBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.user.setdateOfBirth(dateOfBirth);
            return this;
        }

        public UserEntityBuilder registration(LocalDate registration) {
            this.user.setRegistration(registration);
            return this;
        }

        public UserEntityBuilder roles(Set<Role> roles) {
            this.user.setRoles(roles);
            return this;
        }

        public UserEntityBuilder cards(Set<Card> cards) {
            this.user.setCards(cards);
            return this;
        }

        public User build() {
            return new UserBuilder().createUser(this);
        }
    }

    private User createUser(UserEntityBuilder builder) {
        this.user.setId(builder.user.getId());
        this.user.setName(builder.user.getName());
        this.user.setSurname(builder.user.getSurname());
        this.user.setLastname(builder.user.getLastname());
        this.user.setEmail(builder.user.getEmail());
        this.user.setPassword(builder.user.getPassword());
        this.user.setdateOfBirth(builder.user.getdateOfBirth());
        this.user.setRegistration(builder.user.getRegistration());
        this.user.setRoles(builder.user.getRoles());
        this.user.setCards(builder.user.getCards());
        return this.user;
    }
}
