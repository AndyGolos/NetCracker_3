package com.golosov.services.dto.exceptionDto;

/**
 * Created by Андрей on 01.06.2017.
 */
public class FailedResponseDto {

    private String massage;
    private String status;

    public FailedResponseDto() {
    }

    public FailedResponseDto(String massage, String status) {
        this.massage = massage;
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
