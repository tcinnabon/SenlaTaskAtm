package eu.senla.atm.controller;

import eu.senla.atm.dao.BankCardDao;
import eu.senla.atm.exception.BankCardBlocked;
import eu.senla.atm.exception.NotAuthorizedException;
import eu.senla.atm.model.BankCard;

import java.util.Date;
import java.util.Objects;

public class BankCardController {

    private BankCardDao bankCardDao;

    public BankCardDao getBankCardDao() {
        return bankCardDao;
    }

    public BankCardController(BankCardDao bankCardDao) {
        this.bankCardDao = bankCardDao;
    }

    public void addCard(String data[]){
        for(int i =0; i < data.length; ){
            bankCardDao.add(new BankCard(data[i],
                    data[i+1],Integer.parseInt(data[i+2]),
                    Long.parseLong(data[i+3])));
            i += 4;
        }
    }

    public BankCard getCard(BankCard bankCard){
        BankCard card = bankCardDao.getCardByNum(bankCard.getNumberCard());
        if(card==null){
            throw new NotAuthorizedException();
        }
        return card;
    }

    public boolean authorize(BankCard currentCard, String pinCod){
        if(currentCard.getDateLocked()!=0) {
            if(!CardIsBlock(currentCard)) {
                throw new BankCardBlocked();
            }
        }
        if (Objects.equals(currentCard.getPinCod(), pinCod)) {
                if (currentCard.getDateLocked() != 0) {
                    throw new BankCardBlocked();
                }

        } else {
            currentCard.setNumberFailed(currentCard.getNumberFailed() + 1);
            if(currentCard.getNumberFailed()>=3){
                Date date = new Date();
                currentCard.setDateLocked(date.getTime());
                currentCard.resetNumberFailed();
                throw new BankCardBlocked();
            }
            return false;
        }

        return true;

    }
    public boolean CardIsBlock(BankCard currentCard){
        long oneDayMls = 86_400_000;
        Date date = new Date();
        if (date.getTime() - currentCard.getDateLocked() >= oneDayMls) {
            currentCard.resetDataLocked();
            return true;
        }
        return false;
    }
}
