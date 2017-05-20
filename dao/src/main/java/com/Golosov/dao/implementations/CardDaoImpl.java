package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.CardDao;
import com.Golosov.entities.Card;
import com.Golosov.entities.User;
import com.Golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class CardDaoImpl extends AbstractDao<Card> implements CardDao {

    private static Logger logger = Logger.getLogger(CardDaoImpl.class);

    private static final String FROM_CARD = "from Card";

    public CardDaoImpl() {
        super(Card.class, FROM_CARD);
    }


    public Set<Card> getAllCardsByUserId(long id) {
        Set<Card> cards = null;
        try {
            User user  = entityManager.find(User.class, id);
            if (user!=null){
                cards = user.getCards();
            }
        }catch (HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return cards;
    }

    public void blockCard(Card card) {
        card.setStatus(false);
        entityManager.merge(card);
    }

    public void unblockCard(Card card) {
        card.setStatus(true);
        entityManager.merge(card);
    }
}