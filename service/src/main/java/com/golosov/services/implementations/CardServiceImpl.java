package com.golosov.services.implementations;

import com.golosov.builders.HistoryBuilder;
import com.golosov.dao.interfaces.*;
import com.golosov.entities.*;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.dto.dto.TransferDto;
import com.golosov.services.exceptions.*;
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
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public long save(CardDto cardDto) {
        Card card = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            Bill bill = billDao.getById(card.getBill().getId());
            Type type = typeDao.getById(card.getType().getId());
            User user = userDao.getById(card.getUser().getId());
            if (bill == null) {
                throw new NotFoundException("Bill not found!");
            } else if (type == null) {
                throw new NotFoundException("Type not found!");
            } else if (user == null) {
                throw new NotFoundException("User not found!");
            }
            card.setStatus(true);
            card.setRegistration(LocalDate.now());
            card.setValidity(card.getRegistration().plusYears(5));
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
    public List<CardDto> findUsersCards(long id) {
        List<CardDto> cardDtos = new ArrayList<>();
        try {
            List<Card> userCards = cardDao.getAllCardsByUserId(id);
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
    public void update(CardDto cardDto, long id) {
        Card currentCard = Converter.cardDtoToCardEntityConverter(cardDto);
        try {
            Card card = cardDao.getById(id);
            if (card == null) {
                throw new NotFoundException("Card not found!");
            }
            currentCard.setId(id);
            cardDao.update(currentCard);
            logger.debug("Card: " + currentCard + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in card service method card update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockCard(long id) {
        try {
            Card card = cardDao.getById(id);
            if (card == null) {
                throw new NotFoundException("Card not found!");
            } else if(card.isStatus()){
                throw new CardStatusException("Card is already active!");
            }
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
    public void blockCard(long id) {
        try {
            Card card = cardDao.getById(id);
            if (card == null) {
                throw new NotFoundException("Card not found!");
            } else if(!card.isStatus()){
                throw new CardStatusException("Card is already blocked!");
            }
            cardDao.blockCard(card);
            logger.debug("Card: " + card + " successfully blocked!");
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
                throw new NotFoundException("Card by id " + transferDto.getFromCardId() + " not found!");
            } else if (!fromCard.isStatus()) {
                throw new CardStatusException("Card by id " + transferDto.getFromCardId() + " is blocked!");
            }
            logger.debug("Card: " + fromCard + " successfully found!");

            Bill fromBill = billDao.getById(fromCard.getBill().getId());
            logger.debug("Bill: " + fromBill + " successfully found!");

            Card toCard = cardDao.getById(transferDto.getToCardId());
            if (toCard == null) {
                throw new NotFoundException("Card by id " + transferDto.getToCardId() + " not found!");
            } else if (!toCard.isStatus()) {
                throw new CardStatusException("Card by id " + transferDto.getToCardId() + " is blocked!");
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
