package com.golosov.controllers.responses;

/**
 * Created by Андрей on 04.06.2017.
 */
public class SuccessResponse {

    private long id;
    private String httpStatus;

    public SuccessResponse() {
    }

    public SuccessResponse(long id, String httpStatus) {
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
