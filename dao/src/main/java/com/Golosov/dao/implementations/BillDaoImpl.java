package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.entities.Bill;
import org.springframework.stereotype.Repository;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class BillDaoImpl extends AbstractDao<Bill> implements BillDao {

    private static final String FROM_BILL = "from Bill";

    public BillDaoImpl() {
        super(Bill.class, FROM_BILL);
    }

    public void setMoney(Bill bill, long money) {
        bill.setMoney(bill.getMoney() + money);
        entityManager.merge(bill);
    }
}
