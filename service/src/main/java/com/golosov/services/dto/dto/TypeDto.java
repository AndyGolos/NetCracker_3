package com.golosov.services.dto.dto;

/**
 * Created by Андрей on 18.05.2017.
 */
public class TypeDto extends BaseDto{

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeDto typeDto = (TypeDto) o;

        return type != null ? type.equals(typeDto.type) : typeDto.type == null;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TypeDto{" +
                "type='" + type + '\'' +
                '}';
    }
}
