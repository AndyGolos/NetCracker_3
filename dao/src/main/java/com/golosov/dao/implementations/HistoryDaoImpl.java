package com.golosov.dao.implementations;

import com.golosov.dao.interfaces.HistoryDao;
import com.golosov.entities.Card;
import com.golosov.entities.History;
import com.golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class HistoryDaoImpl extends AbstractDao<History> implements HistoryDao {

    private static Logger logger = Logger.getLogger(HistoryDaoImpl.class);

    private static final String FROM_HISTORY = "from Usage_history";

    public HistoryDaoImpl() {
        super(History.class, FROM_HISTORY);
    }

    @Override
    public Set<History> getHistoriesByCardId(long id) {
        Set<History> histories = null;
        try {
            Card card = entityManager.find(Card.class, id);
            if (card != null) {
                histories = card.getHistories();
                logger.debug("Card histories with id: " + id + " successfully found!");
            }
        } catch (HibernateException e) {
            logger.error("Error was thrown in HistoryDaoImpl method getHistoriesByCardId: " + e);
            throw new DaoException(e);
        }
        return histories;
    }
}