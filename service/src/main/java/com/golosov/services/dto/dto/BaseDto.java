package com.golosov.services.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Андрей on 19.05.2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDto {

    private long id;
    private String httpStatus;

    public BaseDto() {
    }

    public BaseDto(long id, String httpStatus) {
        this.id = id;
        this.httpStatus = httpStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
}
