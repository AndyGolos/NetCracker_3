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

    private static final Logger LOG = Logger.getLogger(BillServiceImpl.class);

    @Autowired
    private BillDao billDao;

    @Override
    public void replenishBill(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            //TODO Метод доделать
            throw new UnsupportedOperationException();
//            billDao.update(bill);
        } catch (DaoException dao) {
            LOG.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public long save(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            billDao.save(bill);
            return bill.getId();
        } catch (DaoException dao) {
            LOG.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void delete(long id) {
        try {
            billDao.delete(id);
        } catch (DaoException dao) {
            LOG.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void update(BillDto billDto) {
        Bill bill = Converter.billDtoToBillEntityConverter(billDto);
        try {
            billDao.update(bill);
        } catch (DaoException dao) {
            LOG.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }

    }

    @Override
    public List<BillDto> getAll() {
        List<BillDto> billDtos = new ArrayList<>();
        try {
            List<Bill> bills = billDao.getAll();
            bills.forEach(bill -> {
                BillDto billDto = Converter.billEntityToBillDtoConverter(bill);
                billDtos.add(billDto);
            });
        } catch (DaoException dao) {
            LOG.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return billDtos;
    }

    @Override
    public BillDto get(long id) {
        BillDto billDto;
        try {
            Bill bill = billDao.getById(id);
            billDto = Converter.billEntityToBillDtoConverter(bill);
        } catch (DaoException dao) {
            LOG.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return billDto;
    }
}
