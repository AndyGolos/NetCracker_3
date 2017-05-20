package com.Golosov.services.interfaces;

import com.Golosov.entities.History;
import com.Golosov.services.dto.dto.HistoryDto;

import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface HistoryService extends BaseService<HistoryDto> {

    Set<HistoryDto> findCardHistory(long cardId);
}
