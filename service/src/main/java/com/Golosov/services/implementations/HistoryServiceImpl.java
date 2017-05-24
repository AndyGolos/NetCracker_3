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

import java.util.*;

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
        history.setOperationTime(Calendar.getInstance());
        try {
            historyDao.save(history);
            logger.info("History: " + history + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history save: " + e);
            throw new ServiceException(e);
        }
        return history.getId();
    }

    @Override
    public Set<HistoryDto> findCardHistory(long cardId) {
        Set<HistoryDto> historyDtos = new HashSet<>();
        try {
            Set<History> histories = historyDao.getHistoriesByCardId(cardId);
            logger.info("All histories card with id: " + cardId + " successfully found!");
            histories.forEach(history -> {
                HistoryDto historyDto = Converter.historyEntityToHistoryDtoConverter(history);
                historyDtos.add(historyDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history findCardHistory: " + e);
            throw new ServiceException(e);
        }
        return historyDtos;
    }

    @Override
    public void delete(long id) {
        try {
            historyDao.delete(id);
            logger.info("History by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(HistoryDto historyDto) {
        History history = Converter.historyDtoToHistoryEntityConverter(historyDto);
        try {
            historyDao.update(history);
            logger.info("History: " + history + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<HistoryDto> getAll() {
        List<HistoryDto> historyDtos = new ArrayList<>();
        try {
            List<History> histories = historyDao.getAll();
            logger.info("All histories successfully found!");
            histories.forEach(history -> {
                HistoryDto historyDto = Converter.historyEntityToHistoryDtoConverter(history);
                historyDtos.add(historyDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history getAll: " + e);
            throw new ServiceException(e);
        }
        return historyDtos;
    }

    @Override
    public HistoryDto get(long id) {
        HistoryDto historyDto;
        try {
            History history = historyDao.getById(id);
            logger.info("History by id: " + id + " successfully found!");
            historyDto = Converter.historyEntityToHistoryDtoConverter(history);
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history get: " + e);
            throw new ServiceException(e);
        }
        return historyDto;
    }
}
