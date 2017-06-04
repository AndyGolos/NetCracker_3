package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.BillDto;
import com.golosov.services.dto.dto.ReplenishDto;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface BillService extends BaseService<BillDto>{

    void  replenishBill(ReplenishDto replenishDto);
}
