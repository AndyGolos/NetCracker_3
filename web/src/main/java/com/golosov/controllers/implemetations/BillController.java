package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.BillDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/api/bills")
public class BillController extends AbstractCrudController<BillDto> {

    private BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> saveBill(@RequestBody BillDto billDto) {
        return save(billDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateBill(@RequestBody BillDto billDto, @PathVariable long id) {
        return update(billDto, id);
    }

    @Override
    protected BaseService<BillDto> getService() {
        return billService;
    }
}
