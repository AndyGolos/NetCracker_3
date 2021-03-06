package com.golosov.dao.interfaces;

import com.golosov.entities.Card;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface CardDao extends BaseDao<Card> {

    List<Card> getAllCardsByUserId(long id);

    void blockCard(Card card);

    void unblockCard(Card card);
}
