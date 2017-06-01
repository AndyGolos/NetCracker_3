package com.golosov.dao.interfaces;

import com.golosov.entities.Bill;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface BillDao extends BaseDao<Bill> {

    void setMoney(Bill bill, long money);
}
