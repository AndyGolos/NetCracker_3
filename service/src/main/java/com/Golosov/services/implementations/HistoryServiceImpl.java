package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.HistoryDao;
import com.Golosov.entities.History;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.HistoryDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.HistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class HistoryServiceImpl implements HistoryService {

    private static Logger logger = Logger.getLogger(HistoryServiceImpl.class);

    @Autowired
    private HistoryDao historyDao;

    @Override
    public long save(HistoryDto historyDto) {
        History history = Converter.historyDtoToHistoryEntityConverter(historyDto);
        try {
            historyDao.save(history);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return history.getId();
    }

    @Override
    public Set<HistoryDto> findCardHistory(long cardId) {
        Set<HistoryDto> historyDtos = new HashSet<>();
        try {
            Set<History> histories = historyDao.getHistoriesByCardId(cardId);
            histories.forEach(history -> {
                HistoryDto historyDto = Converter.historyEntityToHistoryDtoConverter(history);
                historyDtos.add(historyDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return historyDtos;
    }

    @Override
    public void delete(long id) {
        try {
            historyDao.delete(id);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void update(HistoryDto historyDto) {
        History history = Converter.historyDtoToHistoryEntityConverter(historyDto);
        try {
            historyDao.update(history);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public List<HistoryDto> getAll() {
        List<HistoryDto> historyDtos = new ArrayList<>();
        try {
            List<History> histories = historyDao.getAll();
            histories.forEach(history -> {
                HistoryDto historyDto = Converter.historyEntityToHistoryDtoConverter(history);
                historyDtos.add(historyDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return historyDtos;
    }

    @Override
    public HistoryDto get(long id) {
        HistoryDto historyDto;
        try {
            History history = historyDao.getById(id);
            historyDto = Converter.historyEntityToHistoryDtoConverter(history);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return historyDto;
    }
}
