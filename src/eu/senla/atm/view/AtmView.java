package eu.senla.atm.view;

import eu.senla.atm.controller.AtmController;
import eu.senla.atm.exception.*;
import eu.senla.atm.model.BankCard;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtmView {

    private static final Scanner scanner= new Scanner(System.in);
    private static AtmController atmController;

    public AtmView(AtmController atmController) {
        AtmView.atmController = atmController;
    }

    public void start() {
        if (atmController.load())
        {
            while (true) {
                try {
                    cardNumberEntry();
                    cardPinEntry();
                    mainMenuRun();
                } catch (NotAuthorizedException | BankCardBlocked e) {
                    System.out.println(e.getMessage());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                atmController.update();
            }
        }else{
            System.out.println("Возникла ошибка чтения из файла");
        }
    }
    private void cardNumberEntry(){
        boolean flagRun = false;
        do {
            try {
                System.out.println("    Введите номер карты");
                String numberCard = scanner.next();
                if(!checkCardNumber(numberCard)){
                    throw new IncorrectNumberException();
                }
                if (atmController.cardNumberCheck(new BankCard(numberCard))) {
                    flagRun = true;
                }
            } catch (IncorrectNumberException e) {
                System.out.println(e.getMessage());
            }
        } while (!flagRun);
    }

    private void cardPinEntry(){
        boolean flagRun = false;
        do {
            try {
                System.out.println("    Введите пинкод карты");
                String pinCod = scanner.next();
                if (!checkCardPinCod(pinCod)) {
                   throw new IncorrectPinException();
                   }
                if (atmController.authorize(pinCod)) {
                    flagRun = true;
                }
            } catch (IncorrectPinException | WrongPincodeException e) {
                System.out.println(e.getMessage());
            }
        } while (!flagRun);
    }

    private void mainMenuRun(){
        String menuSelection;
        while (true) {
            System.out.println("    1 - просмотреть баланс");
            System.out.println("    2 - пополнить баланс");
            System.out.println("    3 - снять деньги с карты ");
            menuSelection = scanner.next();
            switch (menuSelection) {

                case "1":
                    getCheckBalance();
                    break;
                case "2":
                    topUpAccount();
                    break;
                case "3":
                    withdrawMoney();
                    break;
                default:
                    break;
            }
            atmController.update();
        }
    }
    private void withdrawMoney() {
        try {
            String money;
            System.out.print(" Введите сумму  ");
            money = scanner.next();
            if(Integer.parseInt(money)<= 0) {
                throw new CashWithdrawalException();
            }
            if(atmController.withdrawMoney(Integer.parseInt(money))){
                System.out.println("Денги успешно сняты");
            }
        } catch (NumberFormatException | InputMismatchException e){
            System.out.println("Некорректная денежная сумма");
        }catch (CashWithdrawalException | NotEnoughMoneyException | NotEnoughMoneyAtmException e){
            System.out.println(e.getMessage());
        }
    }

    private void topUpAccount() {
        try {
            String money;
            System.out.print(" Введите сумму  ");
            money = scanner.next();
            if(Integer.parseInt(money) <= 0){
                throw new AmountMoneyException();
            }
            if (atmController.topUpAccount(Integer.parseInt(money))) {
                System.out.println("Денги внесены успешно");
            }
        }catch (AmountMoneyException e){
            System.out.println(e.getMessage());
        }catch (NumberFormatException | InputMismatchException e){
            System.out.println("Некорректная денежная сумма");
        }
    }

    private void getCheckBalance() {
            System.out.println("Текущий бананс : " +  atmController.checkCardBalance());
    }


    private boolean checkCardNumber(String numberCard){
        String templateCard = "^[0-9]{4}[\\-][0-9]{4}[\\-][0-9]{4}[\\-][0-9]{4}";
        Matcher m = Pattern.compile(templateCard).matcher(numberCard);
        return m.find();


    }

    private static boolean checkCardPinCod(String pinCodCard){
        String templatePinCod = "^[0-9]{4}$";
        Matcher m = Pattern.compile(templatePinCod).matcher(pinCodCard);
        return m.find();

    }
}
