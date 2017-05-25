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
import com.Golosov.services.exceptions.NotFoundException;
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
            logger.debug("Card: " + card + " successfully saved!");
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
            logger.debug("Card by id: " + id + " successfully deleted!");
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
            logger.debug("All user's cards with id: " + id + " successfully found!");
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
            logger.debug("Card: " + card + " successfully updated!");
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
            logger.debug("Card: " + card + " successfully unblocked!");
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
            logger.debug("All cards successfully found!");
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
            logger.debug("Card by id: " + id + " successfully found!");
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
            logger.debug("Card: " + card + " successfully blocked!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card block: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean transferMoney(long fromCardId, String fromCardPassword, long toCardId, long amountOfMoney) {
        try {
            Card fromCard = cardDao.getById(fromCardId);
            if (fromCard == null) {
                logger.debug("Card by id: " + fromCardId + " not found!");
                throw new NotFoundException("Bill not found!");
            }
            logger.debug("Card: " + fromCard + " successfully found!");

            Bill fromBill = billDao.getById(fromCard.getBill().getId());
            logger.debug("Bill: " + fromBill + " successfully found!");

            Card toCard = cardDao.getById(toCardId);
            if (toCard == null) {
                logger.debug("Card by id: " + toCardId + " not found!");
                throw new NotFoundException("Bill not found!");
            }
            logger.debug("Card: " + toCard + " successfully found!");
            Bill toBill = billDao.getById(toCard.getBill().getId());
            logger.debug("Bill: " + toBill + " successfully found!");

            if (fromCard.getPassword().equals(fromCardPassword)) {
                if (fromBill.getMoney() - amountOfMoney < 0) {
                    logger.debug("There are not enough money on the bill!");
                    return false;
                } else {
                    fromBill.setMoney(fromBill.getMoney() - amountOfMoney);
                    billDao.update(fromBill);
                    logger.debug("Money on the card: " + fromCard + " successfully updated!");
                    History fromHistory = new HistoryBuilder
                            .HistoryEntityBuilder()
                            .card(fromCard)
                            .operationTime(Calendar.getInstance())
                            .valueChange("-" + amountOfMoney)
                            .build();
                    historyDao.save(fromHistory);
                    logger.debug("History: " + fromHistory + " successfully saved!");

                    toBill.setMoney(toBill.getMoney() + amountOfMoney);
                    billDao.update(toBill);
                    logger.debug("Money on the card: " + toCard + " successfully updated!");
                    History toHistory = new HistoryBuilder
                            .HistoryEntityBuilder()
                            .card(toCard)
                            .operationTime(Calendar.getInstance())
                            .valueChange("+" + amountOfMoney)
                            .build();
                    historyDao.save(toHistory);
                    logger.debug("History: " + fromHistory + " successfully saved!");
                    logger.debug("Money transferred!");
                    return true;
                }
            } else {
                logger.debug("Card password is not equals to entered password!");
                return false;
            }
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card block: " + e);
            throw new ServiceException(e);
        }
    }
}
