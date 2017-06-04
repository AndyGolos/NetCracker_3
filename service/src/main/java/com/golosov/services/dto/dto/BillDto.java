package com.golosov.services.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Андрей on 18.05.2017.
 */
public class BillDto extends BaseDto {

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

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillDto billDto = (BillDto) o;

        return money == billDto.money;
    }

    @Override
    public int hashCode() {
        return (int) (money ^ (money >>> 32));
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillDto billDto = (BillDto) o;

        return money == billDto.money;
    }

    @Override
    public int hashCode() {
        return (int) (money ^ (money >>> 32));
    }

    @Override
    public String toString() {
        return "BillDto{" +
                "money=" + money +
                ", password='" + password + '\'' +
                '}';
    }
}
