package com.golosov.dao.interfaces;

import com.golosov.entities.History;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface HistoryDao extends BaseDao<History> {

    List<History> getHistoriesByCardId(long id);
}
