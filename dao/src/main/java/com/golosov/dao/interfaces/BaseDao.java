package com.golosov.dao.interfaces;

import java.util.List;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface BaseDao<T> {

    long save(T entity);

    List<T> getAll();

    T getById(long id);

    void update(T entity);

    void delete(long id);

}
