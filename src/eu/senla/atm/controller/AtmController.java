package eu.senla.atm.controller;

import eu.senla.atm.dao.AtmDao;
import eu.senla.atm.exception.*;

import eu.senla.atm.model.BankCard;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AtmController {

    private static final String FILE_NAME = "data.txt";

    private final BankCardController bankCardController;
    private final AtmDao atmDao;
    private BankCard currentCard;

    public AtmController(BankCardController bankCardController, AtmDao atmDao) {
        this.bankCardController = bankCardController;
        this.atmDao = atmDao;
    }

    public boolean authorize(String pinCod) {

        if (!bankCardController.authorize(currentCard, pinCod)) {
            throw new WrongPincodeException();
        }
        return true;

    }

    public boolean cardNumberCheck(BankCard bankCard) {
        currentCard = bankCardController.getCard(bankCard);
        if (currentCard.getDateLocked() != 0) {
            if (!bankCardController.CardIsNotBlock(currentCard)) {
                throw new BankCardBlocked();
            } else {
                update();
            }


        }
        return true;
    }

    public boolean load() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_NAME))) {
            String dateFile = reader.readLine();
            String s = dateFile.substring(0, dateFile.indexOf(" "));
            atmDao.load(Integer.parseInt(s));
            bankCardController.load(dateFile.substring(dateFile.indexOf(" ") + 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void update() {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            atmDao.save(writer);
            bankCardController.save(writer);
        } catch (Exception e) {
            throw new UpdateFileException();
        }
    }

    public int checkCardBalance() {
        return currentCard.getBankAccount().getBalanceCard();
    }

    public boolean topUpAccount(int money) {
        if (money < 1_000_000) {
            currentCard.getBankAccount().setBalanceCard(currentCard.getBankAccount().getBalanceCard() + money);
            atmDao.getAtm().setAmountMoney(atmDao.getAtm().getAmountMoney() + money);
            return true;
        }
        throw new AmountMoneyException();

    }

    public boolean withdrawMoney(int money) {

        if (money > currentCard.getBankAccount().getBalanceCard()) {
            throw new NotEnoughMoneyException();
        } else if (money > atmDao.getAtm().getAmountMoney()) {
            throw new NotEnoughMoneyAtmException();
        } else {
            currentCard.getBankAccount().setBalanceCard(currentCard.getBankAccount().getBalanceCard() - money);
            atmDao.getAtm().setAmountMoney(atmDao.getAtm().getAmountMoney() - money);
            return true;
        }
    }


}
