package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.HistoryDao;
import com.Golosov.entities.Card;
import com.Golosov.entities.History;
import com.Golosov.exceptions.DaoException;
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
    public Set<History> getHistoriesById(long id) {
        Set<History> histories = null;
        try {
            Card card = entityManager.find(Card.class, id);
            if (card != null) {
                histories = card.getHistories();
            }
        } catch (HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return histories;
    }
}