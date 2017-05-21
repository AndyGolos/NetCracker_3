package com.Golosov.services.dto.dto;

/**
 * Created by Андрей on 18.05.2017.
 */
public class HistoryDto extends BaseDto{

    private long id;
    private long cardId;
    private String operationTime;
    private String valueChange;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getValueChange() {
        return valueChange;
    }

    public void setValueChange(String valueChange) {
        this.valueChange = valueChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryDto that = (HistoryDto) o;

        if (cardId != that.cardId) return false;
        if (operationTime != null ? !operationTime.equals(that.operationTime) : that.operationTime != null)
            return false;
        return valueChange != null ? valueChange.equals(that.valueChange) : that.valueChange == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (cardId ^ (cardId >>> 32));
        result = 31 * result + (operationTime != null ? operationTime.hashCode() : 0);
        result = 31 * result + (valueChange != null ? valueChange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "cardId=" + cardId +
                ", operationTime='" + operationTime + '\'' +
                ", valueChange='" + valueChange + '\'' +
                '}';
    }
}
