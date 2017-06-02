package com.golosov.services.implementations;

import com.golosov.builders.HistoryBuilder;
import com.golosov.dao.interfaces.BillDao;
import com.golosov.dao.interfaces.CardDao;
import com.golosov.dao.interfaces.HistoryDao;
import com.golosov.entities.Bill;
import com.golosov.entities.Card;
import com.golosov.entities.History;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.dto.dto.TransferDto;
import com.golosov.services.exceptions.IncorrectPasswordException;
import com.golosov.services.exceptions.NotEnoughMoneyException;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.exceptions.ServiceException;
import com.golosov.services.interfaces.CardService;
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
        card.setStatus(true);
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
            Card card = cardDao.getById(id);
            if (card == null) {
                throw new NotFoundException("Card not found!");
            }
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
        Card currentCard = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            Card card = cardDao.getById(currentCard.getId());
            if (card == null) {
                throw new NotFoundException("Card not found!");
            }
            cardDao.update(currentCard);
            logger.debug("Card: " + currentCard + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockCard(CardDto cardDto) {
        Card currentCard = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            Card card = cardDao.getById(currentCard.getId());
            if (card == null) {
                throw new NotFoundException("Card not found!");
            }
            cardDao.unblockCard(currentCard);
            logger.debug("Card: " + currentCard + " successfully unblocked!");
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
            if (card == null) {
                throw new NotFoundException("Card not found!");
            }
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
        Card currentCard = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            Card card = cardDao.getById(cardDto.getId());
            if (card == null) {
                throw new NotFoundException("Card not found!");
            }
            cardDao.blockCard(currentCard);
            logger.debug("Card: " + currentCard + " successfully blocked!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card block: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean transferMoney(TransferDto transferDto) {
        try {
            Card fromCard = cardDao.getById(transferDto.getFromCardId());
            if (fromCard == null) {
                throw new NotFoundException("Bill not found!");
            }
            logger.debug("Card: " + fromCard + " successfully found!");

            Bill fromBill = billDao.getById(fromCard.getBill().getId());
            logger.debug("Bill: " + fromBill + " successfully found!");

            Card toCard = cardDao.getById(transferDto.getToCardId());
            if (toCard == null) {
                throw new NotFoundException("Bill not found!");
            }
            logger.debug("Card: " + toCard + " successfully found!");
            Bill toBill = billDao.getById(toCard.getBill().getId());
            logger.debug("Bill: " + toBill + " successfully found!");

            if (fromCard.getPassword().equals(transferDto.getFromCardPassword())) {
                if (fromBill.getMoney() - transferDto.getAmountOfMoney() <= 0) {
                    throw new NotEnoughMoneyException("Not enough money on card!");
                } else {
                    fromBill.setMoney(fromBill.getMoney() - transferDto.getAmountOfMoney());
                    billDao.update(fromBill);
                    logger.debug("Money on the card: " + fromCard + " successfully updated!");
                    History fromHistory = new HistoryBuilder
                            .HistoryEntityBuilder()
                            .card(fromCard)
                            .operationTime(Calendar.getInstance())
                            .valueChange("-" + transferDto.getAmountOfMoney())
                            .build();
                    historyDao.save(fromHistory);
                    logger.debug("History: " + fromHistory + " successfully saved!");

                    toBill.setMoney(toBill.getMoney() + transferDto.getAmountOfMoney());
                    billDao.update(toBill);
                    logger.debug("Money on the card: " + toCard + " successfully updated!");
                    History toHistory = new HistoryBuilder
                            .HistoryEntityBuilder()
                            .card(toCard)
                            .operationTime(Calendar.getInstance())
                            .valueChange("+" + transferDto.getAmountOfMoney())
                            .build();
                    historyDao.save(toHistory);
                    logger.debug("History: " + fromHistory + " successfully saved!");
                    logger.debug("Money transferred!");
                    return true;
                }
            } else {
                throw new IncorrectPasswordException("Incorrect password entered!");
            }
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card block: " + e);
            throw new ServiceException(e);
        }
    }
}
