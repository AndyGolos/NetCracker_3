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
}
