package com.golosov.services.dto.dto;

/**
 * Created by Андрей on 01.06.2017.
 */
public class TransferDto {

    private long fromCardId;
    private String fromCardPassword;
    private long toCardId;
    private long amountOfMoney;

    public TransferDto() {
    }

    public TransferDto(long fromCardId, String fromCardPassword, long toCardId, long amountOfMoney) {
        this.fromCardId = fromCardId;
        this.fromCardPassword = fromCardPassword;
        this.toCardId = toCardId;
        this.amountOfMoney = amountOfMoney;
    }

    public long getFromCardId() {
        return fromCardId;
    }

    public void setFromCardId(long fromCardId) {
        this.fromCardId = fromCardId;
    }

    public String getFromCardPassword() {
        return fromCardPassword;
    }

    public void setFromCardPassword(String fromCardPassword) {
        this.fromCardPassword = fromCardPassword;
    }

    public long getToCardId() {
        return toCardId;
    }

    public void setToCardId(long toCardId) {
        this.toCardId = toCardId;
    }

    public long getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(long amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransferDto that = (TransferDto) o;

        if (fromCardId != that.fromCardId) return false;
        if (toCardId != that.toCardId) return false;
        if (amountOfMoney != that.amountOfMoney) return false;
        return fromCardPassword != null ? fromCardPassword.equals(that.fromCardPassword) : that.fromCardPassword == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (fromCardId ^ (fromCardId >>> 32));
        result = 31 * result + (fromCardPassword != null ? fromCardPassword.hashCode() : 0);
        result = 31 * result + (int) (toCardId ^ (toCardId >>> 32));
        result = 31 * result + (int) (amountOfMoney ^ (amountOfMoney >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "TransferDto{" +
                "fromCardId=" + fromCardId +
                ", fromCardPassword='" + fromCardPassword + '\'' +
                ", toCardId=" + toCardId +
                ", amountOfMoney=" + amountOfMoney +
                '}';
    }
}
