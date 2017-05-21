package com.Golosov.services.implementations;

import com.Golosov.builders.HistoryBuilder;
import com.Golosov.dao.interfaces.*;
import com.Golosov.entities.Bill;
import com.Golosov.entities.Card;
import com.Golosov.entities.History;
import com.Golosov.entities.Type;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.CardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Андрей on 17.05.2017.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class CardServiceImpl implements CardService {

    private static Logger logger = Logger.getLogger(CardServiceImpl.class);

    @Autowired
    private CardDao cardDao;
    @Autowired
    private HistoryDao historyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private BillDao billDao;

    @Override
    public long save(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.save(card);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return card.getId();
    }

    @Override
    public void delete(long id) {
        try {
            cardDao.delete(id);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public Set<CardDto> findUsersCards(long id) {
        Set<CardDto> cardDtos = new HashSet<>();
        try {
            Set<Card> userCards = cardDao.getAllCardsByUserId(id);
            userCards.forEach(card -> {
                CardDto cardDto = Converter.cardEntityToCardDtoConverter(card);
                cardDtos.add(cardDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return cardDtos;
    }

    @Override
    public void update(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.update(card);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void unblockCard(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.unblockCard(card);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public List<CardDto> getAll() {
        List<CardDto> cardDtos = new ArrayList<>();
        try {
            List<Card> cards = cardDao.getAll();
            cards.forEach(card -> {
                CardDto cardDto = Converter.cardEntityToCardDtoConverter(card);
                cardDtos.add(cardDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return cardDtos;
    }

    @Override
    public CardDto get(long id) {
        CardDto cardDto;
        try {
            Card card = cardDao.getById(id);
            cardDto = Converter.cardEntityToCardDtoConverter(card);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return cardDto;
    }

    @Override
    public void blockCard(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.blockCard(card);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    //TODO поправить метод
    @Override
    public boolean replenishCard(long cardId, String cardPassword, long cardTransferId, long summ) {
        Card card = cardDao.getById(cardId);
        if (card.getPassword().equals(cardPassword)) {
            Card transferCard = cardDao.getById(cardTransferId);
            Bill bill = billDao.getById(card.getBill().getId());
            if (bill.getMoney() - summ < 0) {
                return false;
            } else {
                bill.setMoney(bill.getMoney() - summ);
                cardDao.update(card);

                History history = new HistoryBuilder
                        .HistoryEntityBuilder()
                        .card(card)
                        .operationTime(Calendar.getInstance())
                        .valueChange("-" + summ)
                        .build();
                historyDao.save(history);

                Bill billTransferCard = billDao.getById(transferCard.getBill().getId());
                billTransferCard.setMoney(billTransferCard.getMoney() + summ);
                cardDao.update(transferCard);

                History historytransfer = new HistoryBuilder
                        .HistoryEntityBuilder()
                        .card(transferCard)
                        .operationTime(Calendar.getInstance())
                        .valueChange("+" + summ)
                        .build();
                historyDao.save(historytransfer);
                return true;
            }
        } else
            return false;
    }
}
