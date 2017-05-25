package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.entities.Bill;
import com.Golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class BillDaoImpl extends AbstractDao<Bill> implements BillDao {

    private final Logger logger = Logger.getLogger(BillDaoImpl.class);
    private static final String FROM_BILL = "from Bill";

    public BillDaoImpl() {
        super(Bill.class, FROM_BILL);
    }

    public void setMoney(Bill bill, long money) {
        try {
            bill.setMoney(bill.getMoney() + money);
            entityManager.merge(bill);
            logger.debug("Money: "+ money + " on bill: " + bill + " successfully seted!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in billDaoImpl method setMoney: " + e);
            throw new DaoException(e);
            /*"Cant set money on bill! " + */
        }
    }
}
