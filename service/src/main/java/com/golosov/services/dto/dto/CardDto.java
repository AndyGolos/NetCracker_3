package com.golosov.services.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Андрей on 18.05.2017.
 */
public class CardDto extends BaseDto {

    private long billId;
    private long userId;
    private long type;
    private String password;
    private boolean isActive;
    private String registration;
    private String validity;

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDto cardDto = (CardDto) o;

        if (billId != cardDto.billId) return false;
        if (userId != cardDto.userId) return false;
        if (type != cardDto.type) return false;
        if (isActive != null ? !isActive.equals(cardDto.isActive) : cardDto.isActive != null) return false;
        if (registration != null ? !registration.equals(cardDto.registration) : cardDto.registration != null)
            return false;
        return validity != null ? validity.equals(cardDto.validity) : cardDto.validity == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (billId ^ (billId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (type ^ (type >>> 32));
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (registration != null ? registration.hashCode() : 0);
        result = 31 * result + (validity != null ? validity.hashCode() : 0);
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDto cardDto = (CardDto) o;

        if (billId != cardDto.billId) return false;
        if (userId != cardDto.userId) return false;
        if (type != cardDto.type) return false;
        if (isActive != cardDto.isActive) return false;
        if (registration != null ? !registration.equals(cardDto.registration) : cardDto.registration != null)
            return false;
        return validity != null ? validity.equals(cardDto.validity) : cardDto.validity == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (billId ^ (billId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (type ^ (type >>> 32));
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (registration != null ? registration.hashCode() : 0);
        result = 31 * result + (validity != null ? validity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "billId=" + billId +
                ", userId=" + userId +
                ", type=" + type +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", registration='" + registration + '\'' +
                ", validity='" + validity + '\'' +
                '}';
    }
}
