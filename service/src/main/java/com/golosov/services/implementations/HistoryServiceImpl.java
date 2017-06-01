package com.golosov.services.implementations;

import com.golosov.dao.interfaces.HistoryDao;
import com.golosov.entities.History;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.HistoryDto;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.exceptions.ServiceException;
import com.golosov.services.interfaces.HistoryService;
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
            logger.debug("History: " + history + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history save: " + e);
            throw new ServiceException(e);
        }
        return history.getId();
    }

    @Override
    //TODO сделать только для авторизированнго пользователя?
    public Set<HistoryDto> findCardHistory(long cardId) {
        Set<HistoryDto> historyDtos = new HashSet<>();
        try {
            Set<History> histories = historyDao.getHistoriesByCardId(cardId);
            if(histories == null){
                throw new NotFoundException("Histories not found!");
            }
            logger.debug("All histories card with id: " + cardId + " successfully found!");
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
            History history = historyDao.getById(id);
            if(history == null){
                throw new NotFoundException("History not found!");
            }
            historyDao.delete(id);
            logger.debug("History by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(HistoryDto historyDto) {
        History currentHistory = Converter.historyDtoToHistoryEntityConverter(historyDto);
        try {
            History history = historyDao.getById(currentHistory.getId());
            if(history == null){
                throw new NotFoundException("History not found!");
            }
            historyDao.update(currentHistory);
            logger.debug("History: " + currentHistory + " successfully updated!");
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
            logger.debug("All histories successfully found!");
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
            if(history == null){
                throw new NotFoundException("History not found!");
            }
            System.out.println(history);
            logger.debug("History by id: " + id + " successfully found!");
            historyDto = Converter.historyEntityToHistoryDtoConverter(history);
        } catch (DaoException e) {
            logger.error("Error was thrown in history service method history get: " + e);
            throw new ServiceException(e);
        }
        return historyDto;
    }
}
