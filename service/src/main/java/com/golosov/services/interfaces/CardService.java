package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.dto.dto.TransferDto;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface CardService extends BaseService<CardDto> {

    List<CardDto> findUsersCards(long id);

    void unblockCard(long id);

    void blockCard(long id);

    boolean transferMoney(TransferDto transferDto);
}
