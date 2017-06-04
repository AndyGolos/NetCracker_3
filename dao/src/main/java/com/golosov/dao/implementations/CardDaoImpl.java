package com.golosov.dao.implementations;

import com.golosov.dao.interfaces.CardDao;
import com.golosov.entities.Card;
import com.golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
@SuppressWarnings("unchecked")
public class CardDaoImpl extends AbstractDao<Card> implements CardDao {

    private static Logger logger = Logger.getLogger(CardDaoImpl.class);

    private static final String FROM_CARD = "from Card";
    private static final String FROM_CARD_BY_USER_ID = "from Card where user.id = :id";

    public CardDaoImpl() {
        super(Card.class, FROM_CARD);
    }

    public List<Card> getAllCardsByUserId(long id) {
        List<Card> userCards;
        try {
            userCards = entityManager
                    .createQuery(FROM_CARD_BY_USER_ID)
                    .setParameter("id", id)
                    .getResultList();
            logger.debug("User cards with id: " + id + " successfully found!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in CardDaoImpl method getAllCardsByUserId: " + e);
            throw new DaoException(e);
        }
        return userCards;
    }

    public void blockCard(Card card) {
        try {
            card.setStatus(false);
            entityManager.merge(card);
            logger.debug("Card: " + card + " successfully blocked!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in CardDaoImpl method blockCard: " + e);
            throw new DaoException(e);
        }

    }

    public void unblockCard(Card card) {
        try {
            card.setStatus(true);
            entityManager.merge(card);
            logger.debug("Card: " + card + " successfully unblocked!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in CardDaoImpl method unblockCard: " + e);
            throw new DaoException(e);
        }
    }
}
