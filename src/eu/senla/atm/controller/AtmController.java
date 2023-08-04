package eu.senla.atm.controller;

import eu.senla.atm.dao.AtmDao;
import eu.senla.atm.exception.*;
import eu.senla.atm.model.BankCard;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtmController {


    private BankCardController bankCardController;
    private AtmDao atmDao;
    private BankCard currentCard;

    public AtmController(BankCardController bankCardController, AtmDao atmDao) {
        this.bankCardController = bankCardController;
        this.atmDao = atmDao;
    }

    public boolean authorize(String pinCod){

       if (!checkCardPinCod(pinCod)) {
            throw new IncorrectPinException();
        }
        if(!bankCardController.authorize(currentCard, pinCod)){
         throw new WrongPincodeException();
        }
        return true;

    }
    public boolean cardNumberCheck(BankCard bankCard) {
        if(!checkCardNumber(bankCard.getNumberCard())){
            throw new IncorrectNumberException();
        }
        BankCard authorizedBankCard = bankCardController.getCard(bankCard);
        currentCard = authorizedBankCard;
        return true;

    }
    public boolean download(){
        String data[] = atmDao.download();
        if(data!=null) {
            bankCardController.addCard(data);
            return true;
        }
        return false;
    }

    private boolean checkCardNumber(String numberCard){
        String templateCard = "^[0-9]{4}[\\-][0-9]{4}[\\-][0-9]{4}[\\-][0-9]{4}";
        Matcher m = Pattern.compile(templateCard).matcher(numberCard);
        if (m.find( )) {
            return true;
        }else {
            return false;
        }


    }

    private static boolean checkCardPinCod(String pinCodCard){
        String templatePinCod = "^[0-9]{4}$";
        Matcher m = Pattern.compile(templatePinCod).matcher(pinCodCard);
        if (m.find( )) {
            return true;
        }else {
            return false;
        }

    }

    public void update(){
        atmDao.save(bankCardController.getBankCardDao());
        //fileUpdate(atmDao.returnAtm(), (ArrayList<BankCard>) bankCardController.save());

    }
//    public void fileUpdate(Atm atm, ArrayList<BankCard> bankCards) {
//        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, false)) { }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (FileWriter writer = new FileWriter(FILE_NAME, true)){
//            int a = atm.getAmountMoney();
//            writer.write(String.valueOf(a));
//            for (int i =0; i < bankCards.size(); i++){
//                writer.write(" " + bankCards.get(i).getNumberCard());
//                writer.write(" " + bankCards.get(i).getPinCod());
//                writer.write(" " + bankCards.get(i).getBalanceCardFromBankAccaount());
//                writer.write(" " + bankCards.get(i).getDateLocked());
//            }
//            writer.close();
//
//        } catch (IOException e) {
//            System.out.println("Возникла ошибка во время записи в файл");
//        }
//    }


    public int checkCardBalance() {
        return currentCard.getBalanceCardFromBankAccaount();
    }

    public boolean topUpAccount(String money){
            if(Integer.parseInt(money) < 1_000_000){
                if(Integer.parseInt(money) >0){
                    currentCard.topUpBankAccount(Integer.parseInt(money));
                    atmDao.topUpAtm(Integer.parseInt(money));
                    return true;
                }
                throw new AmountMoneyException();
            }
            throw new AmountMoneyException();

    }

    public boolean withdrawMoney(String money) {
        if(Integer.parseInt(money)< 0) {
            throw new CashWithdrawalException();
        }
        else if(Integer.parseInt(money)> currentCard.getBalanceCardFromBankAccaount()){
            throw new NotEnoughMoneyException();
        }else if (Integer.parseInt(money)> atmDao.returnAtm().getAmountMoney()){
            throw new NotEnoughMoneyAtmException();
        }else {
            currentCard.withdrawMoneyBankAccount(Integer.parseInt(money));
            atmDao.takeOff(Integer.parseInt(money));
           return true;
        }
    }


}
