package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.entities.Bill;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.BillDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.BillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //TODO Метод доделать
    @Override
    public void replenishBill(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {

            throw new UnsupportedOperationException();
//            billDao.update(bill);
//            logger.info("Bill: " + bill + " successfully replenished!");
        } catch (DaoException dao) {
            logger.error("Error was thrown in BillServiceImpl method replenish: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public long save(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            billDao.save(bill);
            logger.info("Bill: " + bill + " successfully saved!");
        } catch (DaoException dao) {
            logger.error("Error was thrown in BillServiceImpl method save: " + dao);
            throw new ServiceException();
        }
        return bill.getId();
    }

    @Override
    public void delete(long id) {
        try {
            billDao.delete(id);
            logger.info("Bill by id: " + id + " successfully deleted!");
        } catch (DaoException dao) {
            logger.error("Error was thrown in BillServiceImpl method delete: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void update(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            billDao.update(bill);
            logger.info("Bill: " + bill + " successfully updated!");
        } catch (DaoException dao) {
            logger.error("Error was thrown in BillServiceImpl method update: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public List<BillDto> getAll() {
        List<BillDto> billDtos = new ArrayList<>();
        try {
            List<Bill> bills = billDao.getAll();
            logger.info("All bills successfully found!");
            bills.forEach(bill -> {
                BillDto billDto = Converter.billEntityToBillDtoConverter(bill);
                billDtos.add(billDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in BillServiceImpl method getAll: " + dao);
            throw new ServiceException();
        }
        return billDtos;
    }

    @Override
    public BillDto get(long id) {
        BillDto billDto;
        try {
            Bill bill = billDao.getById(id);
            logger.info("Bill by id: " + id + " successfully found!");
            billDto = Converter.billEntityToBillDtoConverter(bill);
        } catch (DaoException dao) {
            logger.error("Error was thrown in BillServiceImpl method get: " + dao);
            throw new ServiceException();
        }
        return billDto;
    }
}
