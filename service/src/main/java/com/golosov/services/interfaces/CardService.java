package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.dto.dto.TransferDto;

import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface CardService extends BaseService<CardDto> {

    Set<CardDto> findUsersCards(long id);

    void unblockCard(CardDto cardDto);

    void blockCard(CardDto cardDto);

    boolean transferMoney(TransferDto transferDto);
}
