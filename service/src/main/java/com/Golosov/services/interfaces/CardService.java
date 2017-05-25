package com.Golosov.services.interfaces;

import com.Golosov.entities.Card;
import com.Golosov.entities.User;
import com.Golosov.services.dto.dto.CardDto;

import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface CardService extends BaseService<CardDto> {

    Set<CardDto> findUsersCards(long id);

    void unblockCard(CardDto cardDto);

    void blockCard(CardDto cardDto);

    boolean transferMoney(long cardId, String cardPassword, long cardTransferId, long summ);
}
