package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.HistoryDto;

import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface HistoryService extends BaseService<HistoryDto> {

    Set<HistoryDto> findCardHistory(long cardId);
}
