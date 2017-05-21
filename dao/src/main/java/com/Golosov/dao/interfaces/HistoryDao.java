package com.Golosov.dao.interfaces;

import com.Golosov.entities.History;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface HistoryDao extends BaseDao<History> {

    Set<History> getHistoriesByCardId(long id);
}
