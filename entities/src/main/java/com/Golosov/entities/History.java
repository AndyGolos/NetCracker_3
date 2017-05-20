package com.Golosov.entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by Андрей on 16.05.2017.
 */
@Entity(name = "Usage_history")
public class History extends BaseEntity {

    @Column(name = "operation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar operationTime;
    public Calendar getOperationTime() {
        return operationTime;
    }
    public void setOperationTime(Calendar operationTime) {
        this.operationTime = operationTime;
    }

    @Column(name = "value_change", nullable = false)
    private String valueChange;
    public String getValueChange() {
        return valueChange;
    }
    public void setValueChange(String valueChange) {
        this.valueChange = valueChange;
    }

    @ManyToOne
    @JoinColumn (name = "card_id", nullable = false)
    private Card card;
    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        History history = (History) o;

        if (!operationTime.equals(history.operationTime)) return false;
        if (!valueChange.equals(history.valueChange)) return false;
        return card.equals(history.card);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + operationTime.hashCode();
        result = 31 * result + valueChange.hashCode();
        result = 31 * result + card.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "History{" +
                "operationTime=" + operationTime +
                ", valueChange='" + valueChange + '\'' +
                ", card=" + card +
                '}';
    }
}
