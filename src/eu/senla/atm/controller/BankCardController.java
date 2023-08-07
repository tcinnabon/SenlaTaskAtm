package eu.senla.atm.controller;

import eu.senla.atm.dao.BankCardDao;
import eu.senla.atm.exception.BankCardBlocked;
import eu.senla.atm.exception.NotAuthorizedException;
import eu.senla.atm.model.BankCard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class BankCardController {

    private final BankCardDao bankCardDao;

    public BankCardController(BankCardDao bankCardDao) {
        this.bankCardDao = bankCardDao;
    }

    public BankCard getCard(BankCard bankCard) {
        BankCard card = bankCardDao.getCardByNum(bankCard.getNumberCard());
        if (card == null) {
            throw new NotAuthorizedException();
        }
        return card;
    }

    public boolean authorize(BankCard currentCard, String pinCod) {
        if (currentCard.getDateLocked() != 0) {
            if (!CardIsNotBlock(currentCard)) {
                throw new BankCardBlocked();
            }
        }
        if (Objects.equals(currentCard.getPinCod(), pinCod)) {
            if (currentCard.getDateLocked() != 0) {
                throw new BankCardBlocked();
            }

        } else {
            currentCard.setNumberFailed(currentCard.getNumberFailed() + 1);
            if (currentCard.getNumberFailed() >= 3) {
                Date date = new Date();
                currentCard.setDateLocked(date.getTime());
                currentCard.setNumberFailed(0);
                throw new BankCardBlocked();
            }
            return false;
        }

        return true;

    }

    public boolean CardIsNotBlock(BankCard currentCard) {
        long oneDayMls = 86_400_000;
        Date date = new Date();
        if (date.getTime() - currentCard.getDateLocked() >= oneDayMls) {
            currentCard.setDateLocked(0);
            return true;
        }
        return false;
    }

    public void load(String dateString) {
        String[] date = dateString.split(" ");
        bankCardDao.load(date);
    }

    public void save(FileWriter writer) throws IOException {
        bankCardDao.save(writer);
    }
}
