package com.golosov.services.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Андрей on 18.05.2017.
 */
public class BillDto extends BaseDto {

    //TODO поменять значение на LONG. Исправить тесты!
    private long money;
    private String password;

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

        BillDto billDto = (BillDto) o;

        if (money != billDto.money) return false;
        return password != null ? password.equals(billDto.password) : billDto.password == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (money ^ (money >>> 32));
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BillDto{" +
                "money=" + money +
                ", password='" + password + '\'' +
                '}';
    }
}
