package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.services.dto.dto.BaseDto;
import com.golosov.services.dto.dto.BillDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.BillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/bills")
public class BillController extends AbstractCrudController<BillDto> {

    @Autowired
    private BillService billService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<BillDto> saveBill(@RequestBody BillDto billDto) {
        return save(billDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateBill(@RequestBody BillDto billDto) {
        return update(billDto);
    }

    @Override
    protected BaseService<BillDto> getService() {
        return billService;
    }

    @Override
    protected BillDto getResponseDto() {
        return new BillDto();
    }
}
