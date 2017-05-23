package com.Golosov.controllers.implemetations;

import com.Golosov.controllers.abstracts.AbstractCrudController;
import com.Golosov.services.dto.dto.BillDto;
import com.Golosov.services.interfaces.BaseService;
import com.Golosov.services.interfaces.BillService;
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

    private final Logger logger = Logger.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Long> saveBill(@RequestBody BillDto billDto) {
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
}
