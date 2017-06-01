package com.golosov.builders;

import com.golosov.entities.Card;
import com.golosov.entities.Type;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public class TypeBuilder {

    private TypeBuilder() {
    }

    private final Type type = new Type();

    public static class TypeEntityBuilder {

        private Type type = new Type();

        public TypeEntityBuilder id(long id) {
            this.type.setId(id);
            return this;
        }

        public TypeEntityBuilder type(String type) {
            this.type.setType(type);
            return this;
        }

        public TypeEntityBuilder cards(Set<Card> cards) {
            this.type.setCards(cards);
            return this;
        }

        public Type build() {
            return new TypeBuilder().createType(this);
        }
    }

    private Type createType(TypeEntityBuilder builder) {
        this.type.setId(builder.type.getId());
        this.type.setType(builder.type.getType());
        this.type.setCards(builder.type.getCards());
        return this.type;
    }
}
