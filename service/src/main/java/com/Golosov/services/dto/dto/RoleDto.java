package com.Golosov.services.dto.dto;

/**
 * Created by Андрей on 18.05.2017.
 */
public class RoleDto extends BaseDto{

    private long id;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
