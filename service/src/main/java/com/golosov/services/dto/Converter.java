package com.golosov.services.dto;

import com.golosov.entities.*;
import com.golosov.services.dto.dto.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Created by Андрей on 18.05.2017.
 */
public class Converter {

    public static UserDto userEntityToUserDtoConverter(User user) {
        UserDto userDto = null;
        if (user != null) {
            userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setSurname(user.getSurname());
            userDto.setLastname(user.getLastname());
            userDto.setEmail(user.getEmail());
            userDto.setBirth(localDateToStringConverter(user.getdateOfBirth()));
            userDto.setRegistration(localDateToStringConverter(user.getRegistration()));
        }
        return userDto;
    }

    public static TypeDto typeEntityToTypeDtoConverter(Type type) {
        TypeDto typeDto = null;
        if (type != null) {
            typeDto = new TypeDto();
            typeDto.setId(type.getId());
            typeDto.setType(type.getType());
        }
        return typeDto;
    }

    public static RoleDto roleEntityToRoleDtoConverter(Role role) {
        RoleDto roleDto = null;
        if (role != null) {
            roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setRole(role.getRole());
        }
        return roleDto;
    }

    public static BillDto billEntityToBillDtoConverter(Bill bill) {
        BillDto billDto = null;
        if (bill != null) {
            billDto = new BillDto();
            billDto.setId(bill.getId());
            billDto.setMoney(bill.getMoney());
//            billDto.setPassword(bill.getPassword());
        }
        return billDto;
    }

    public static HistoryDto historyEntityToHistoryDtoConverter(History history) {
        HistoryDto historyDto = null;
        if (history != null) {
            historyDto = new HistoryDto();
            historyDto.setId(history.getId());
            historyDto.setCardId(history.getCard().getId());
            historyDto.setOperationTime(calendarToStringConverter(history.getOperationTime()));
            historyDto.setValueChange(history.getValueChange());
        }
        return historyDto;
    }

    public static CardDto cardEntityToCardDtoConverter(Card card) {
        CardDto cardDto = null;
        if (card != null) {
            cardDto = new CardDto();
            cardDto.setId(card.getId());
            cardDto.setBillId(card.getBill().getId());
            cardDto.setUserId(card.getUser().getId());
            cardDto.setActive(card.isStatus());
            cardDto.setType(card.getType().getId());
            LocalDate registration = card.getRegistration();
            cardDto.setRegistration(localDateToStringConverter(registration));
            LocalDate validation = card.getValidity();
            cardDto.setValidity(localDateToStringConverter(validation));
        }
        return cardDto;
    }


    public static Type typeDtoToTypeEntityConverter(TypeDto typeDto) {
        Type type = new Type();
        type.setId(typeDto.getId());
        type.setType(typeDto.getType());
        return type;
    }

    public static User userDtoToTypeEntityConverter(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setdateOfBirth(stringToLocalDateConverter(userDto.getBirth()));
        user.setRegistration(stringToLocalDateConverter(userDto.getRegistration()));
        return user;
    }

    public static Role roleDtoToRoleEntityConverter(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setRole(roleDto.getRole());
        return role;
    }

    public static History historyDtoToHistoryEntityConverter(HistoryDto historyDto) {
        History history = new History();
        history.setId(historyDto.getId());
        history.setValueChange(historyDto.getValueChange());
        Card card = new Card();
        card.setId(historyDto.getCardId());
        history.setCard(card);
        return history;
    }

    public static Bill billDtoToBillEntityConverter(BillDto billDto) {
        Bill bill = new Bill();
        bill.setId(billDto.getId());
        bill.setMoney(billDto.getMoney());
        bill.setPassword(billDto.getPassword());
        return bill;
    }

    public static Card cardDtoToCardEntityConverter(CardDto cardDto) {
        Card card = new Card();
        Bill bill = new Bill(cardDto.getBillId());
        card.setBill(bill);
        User user = new User(cardDto.getUserId());
        card.setUser(user);
        Type type = new Type(cardDto.getType());
        card.setType(type);
        card.setId(cardDto.getId());
        card.setPassword(cardDto.getPassword());
        card.setStatus(cardDto.isActive());
        card.setRegistration(stringToLocalDateConverter(cardDto.getRegistration()));
        card.setValidity(stringToLocalDateConverter(cardDto.getValidity()));
        return card;
    }

    private static LocalDate stringToLocalDateConverter(String date) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (date != null) {
            return LocalDate.parse(date, timeFormatter);
        } else
            return null;
    }

    private static String calendarToStringConverter(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if (calendar != null) {
            return dateFormat.format(calendar.getTime());
        } else
            return null;
    }

    private static String localDateToStringConverter(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (localDate != null) {
            return localDate.format(formatter);
        } else
            return null;
    }
}
