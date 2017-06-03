package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.BaseDto;

import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface BaseService<T extends BaseDto> {

    long save(T dto);

    void delete(long id);

    void update(T dto, long id);

    List<T> getAll();

    T get(long id);

}

