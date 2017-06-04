package com.golosov.services.implementations;

import com.golosov.builders.HistoryBuilder;
import com.golosov.dao.interfaces.BillDao;
import com.golosov.dao.interfaces.CardDao;
import com.golosov.dao.interfaces.HistoryDao;
import com.golosov.entities.Bill;
import com.golosov.entities.Card;
import com.golosov.entities.History;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.BillDto;
import com.golosov.services.dto.dto.ReplenishDto;
import com.golosov.services.exceptions.IncorrectPasswordException;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.exceptions.ServiceException;
import com.golosov.services.interfaces.BillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Андрей on 16.05.2017.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class BillServiceImpl implements BillService {

    private final Logger logger = Logger.getLogger(BillServiceImpl.class);

    @Autowired
    private BillDao billDao;
    @Autowired
    private HistoryDao historyDao;
    @Autowired
    private CardDao cardDao;

    @Override
    public void replenishBill(ReplenishDto replenishDto) {
        try {
            Bill currentBill = billDao.getById(replenishDto.getBillId());
            Card currentCard = cardDao.getById(replenishDto.getCardId());
            if (currentBill == null) {
                throw new NotFoundException("Bill by id: " + replenishDto.getBillId() + " not found!");
            } else if (currentCard == null) {
                throw new NotFoundException("Card by id: " + replenishDto.getCardId() + " not found!");
            }
            if (replenishDto.getPassword() != null
                    && replenishDto.getPassword().equals(currentCard.getPassword())
                    && replenishDto.getMoney() > 0) {
                currentBill.setMoney(currentBill.getMoney() + replenishDto.getMoney());
                billDao.update(currentBill);
                logger.debug("Bill: " + currentBill + " successfully replenished!");
                History history = new HistoryBuilder
                        .HistoryEntityBuilder()
                        .card(new Card(replenishDto.getCardId()))
                        .operationTime(Calendar.getInstance())
                        .valueChange("+" + replenishDto.getMoney())
                        .build();
                historyDao.save(history);
                logger.debug("History: " + history + " successfully saved!");
            } else {
                throw new IncorrectPasswordException("Incorrect password entered!");
            }
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method replenish: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public long save(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            billDao.save(bill);
            logger.debug("Bill: " + bill + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method save: " + e);
            throw new ServiceException(e);
        }
        return bill.getId();
    }

    @Override
    public void delete(long id) {
        try {
            Bill bill = billDao.getById(id);
            if (bill == null) {
                throw new NotFoundException("Bill not found!");
            }
            billDao.delete(id);
            logger.debug("Bill by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(BillDto billDto, long id) {
        Bill currentBill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            Bill bill = billDao.getById(id);
            if (bill == null) {
                throw new NotFoundException("Bill not found!");
            }
            currentBill.setId(id);
            billDao.update(currentBill);
            logger.debug("Bill: " + currentBill + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BillDto> getAll() {
        List<BillDto> billDtos = new ArrayList<>();
        try {
            List<Bill> bills = billDao.getAll();
            logger.debug("All bills successfully found!");
            bills.forEach(bill -> {
                BillDto billDto = Converter.billEntityToBillDtoConverter(bill);
                billDtos.add(billDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method getAll: " + e);
            throw new ServiceException(e);
        }
        return billDtos;
    }

    @Override
    public BillDto get(long id) {
        BillDto billDto;
        try {
            Bill bill = billDao.getById(id);
            if (bill == null) {
                throw new NotFoundException("Bill not found!");
            }
            logger.debug("Bill by id: " + id + " successfully found!");
            billDto = Converter.billEntityToBillDtoConverter(bill);
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method get: " + e);
            throw new ServiceException(e);
        }
        return billDto;
    }
}
