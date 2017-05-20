package com.Golosov.services.interfaces;

import com.Golosov.entities.Bill;
import com.Golosov.services.dto.dto.BillDto;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface BillService extends BaseService<BillDto>{

    void  replenishBill(BillDto billDto);
}
