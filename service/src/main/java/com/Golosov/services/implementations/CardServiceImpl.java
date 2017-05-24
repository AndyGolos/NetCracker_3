package com.Golosov.services.implementations;

import com.Golosov.builders.HistoryBuilder;
import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.dao.interfaces.CardDao;
import com.Golosov.dao.interfaces.HistoryDao;
import com.Golosov.entities.Bill;
import com.Golosov.entities.Card;
import com.Golosov.entities.History;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.CardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private BillDao billDao;

    @Override
    public long save(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        card.setRegistration(LocalDate.now());
        card.setValidity(LocalDate.now().plusYears(5));
        try {
            cardDao.save(card);
            logger.info("Card: " + card + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card save: " + e);
            throw new ServiceException(e);
        }
        return card.getId();
    }

    @Override
    public void delete(long id) {
        try {
            cardDao.delete(id);
            logger.info("Card by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<CardDto> findUsersCards(long id) {
        Set<CardDto> cardDtos = new HashSet<>();
        try {
            Set<Card> userCards = cardDao.getAllCardsByUserId(id);
            logger.info("All user's cards with id: " + id + " successfully found!");
            userCards.forEach(card -> {
                CardDto cardDto = Converter.cardEntityToCardDtoConverter(card);
                cardDtos.add(cardDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card findUsersCards: " + e);
            throw new ServiceException(e);
        }
        return cardDtos;
    }

    @Override
    public void update(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.update(card);
            logger.info("Card: " + card + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockCard(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.unblockCard(card);
            logger.info("Card: " + card + " successfully unblocked!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card unblock: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CardDto> getAll() {
        List<CardDto> cardDtos = new ArrayList<>();
        try {
            List<Card> cards = cardDao.getAll();
            logger.info("All cards successfully found!");
            cards.forEach(card -> {
                CardDto cardDto = Converter.cardEntityToCardDtoConverter(card);
                cardDtos.add(cardDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card getAll: " + e);
            throw new ServiceException(e);
        }
        return cardDtos;
    }

    @Override
    public CardDto get(long id) {
        CardDto cardDto;
        try {
            Card card = cardDao.getById(id);
            logger.info("Card by id: " + id + " successfully found!");
            cardDto = Converter.cardEntityToCardDtoConverter(card);
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card get: " + e);
            throw new ServiceException(e);
        }
        return cardDto;
    }

    @Override
    public void blockCard(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            cardDao.blockCard(card);
            logger.info("Card: " + card + " successfully blocked!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card block: " + e);
            throw new ServiceException(e);
        }
    }

    //TODO поправить метод
    //TODO логгирование
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
