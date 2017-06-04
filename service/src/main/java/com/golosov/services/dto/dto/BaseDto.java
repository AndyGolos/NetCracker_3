package com.golosov.services.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Андрей on 19.05.2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDto {

    private long id;

    public BaseDto() {
    }

    public BaseDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
