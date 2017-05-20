package com.Golosov.builders;

import com.Golosov.entities.Card;
import com.Golosov.entities.History;

import java.util.Calendar;

/**
 * Created by Андрей on 16.05.2017.
 */
public class HistoryBuilder {

    private HistoryBuilder(){
    }

    private final History history = new History();

    public static class HistoryEntityBuilder{

        private History history = new History();

        public HistoryEntityBuilder id(long id){
            this.history.setId(id);
            return this;
        }

        public HistoryEntityBuilder operationTime(Calendar calendar){
            this.history.setOperationTime(calendar);
            return this;
        }

        public HistoryEntityBuilder valueChange(String valueChange){
            this.history.setValueChange(valueChange);
            return this;
        }

        public HistoryEntityBuilder card(Card card){
            this.history.setCard(card);
            return this;
        }

        public History build(){
            return new HistoryBuilder().createHistory(this);
        }
    }

    private History createHistory(HistoryEntityBuilder builder){
        this.history.setId(builder.history.getId());
        this.history.setCard(builder.history.getCard());
        this.history.setValueChange(builder.history.getValueChange());
        this.history.setOperationTime(builder.history.getOperationTime());
        return this.history;
    }
}
