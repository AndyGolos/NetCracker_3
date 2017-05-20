package com.Golosov.services.dto.dto;

/**
 * Created by Андрей on 18.05.2017.
 */
public class BillDto extends BaseDto{

    private long id;
    private long money;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
