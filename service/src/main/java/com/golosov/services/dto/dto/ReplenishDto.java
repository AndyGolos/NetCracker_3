package com.golosov.services.dto.dto;

/**
 * Created by Андрей on 04.06.2017.
 */
public class ReplenishDto {

    private long billId;
    private long cardId;
    private long money;
    private String password;

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReplenishDto that = (ReplenishDto) o;

        if (billId != that.billId) return false;
        if (cardId != that.cardId) return false;
        if (money != that.money) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (billId ^ (billId >>> 32));
        result = 31 * result + (int) (cardId ^ (cardId >>> 32));
        result = 31 * result + (int) (money ^ (money >>> 32));
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReplenishDto{" +
                "billId=" + billId +
                ", cardId=" + cardId +
                ", money=" + money +
                ", password='" + password + '\'' +
                '}';
    }
}
