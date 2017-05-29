package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.entities.Bill;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.Converter;
import com.Golosov.services.dto.dto.BillDto;
import com.Golosov.services.exceptions.IncorrectPasswordException;
import com.Golosov.services.exceptions.NotFoundException;
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

    @Override
    public void replenishBill(BillDto billDto) {
        Bill replenishableBill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            Bill currentBill = billDao.getById(replenishableBill.getId());
            if (currentBill == null) {
                logger.debug("Bill by id: " + replenishableBill.getId() + " not found!");
                throw new NotFoundException("Bill not found!");
            }
            if (replenishableBill.getPassword() != null
                    && replenishableBill.getPassword().equals(currentBill.getPassword())
                    && replenishableBill.getMoney() > 0) {
                currentBill.setMoney(currentBill.getMoney() + replenishableBill.getMoney());
                billDao.update(currentBill);
                logger.debug("Bill: " + currentBill + " successfully replenished!");
            } else {
                logger.debug("Inocorrect data entered!");
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
            billDao.delete(id);
            logger.debug("Bill by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            billDao.update(bill);
            logger.debug("Bill: " + bill + " successfully updated!");
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
            logger.debug("Bill by id: " + id + " successfully found!");
            billDto = Converter.billEntityToBillDtoConverter(bill);
        } catch (DaoException e) {
            logger.error("Error was thrown in BillServiceImpl method get: " + e);
            throw new ServiceException(e);
        }
        return billDto;
    }
}
