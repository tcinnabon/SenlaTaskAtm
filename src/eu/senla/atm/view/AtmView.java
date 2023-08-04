package eu.senla.atm.view;

import eu.senla.atm.controller.AtmController;
import eu.senla.atm.exception.*;
import eu.senla.atm.model.BankCard;

import java.util.Scanner;

public class AtmView {

    private static final Scanner scanner= new Scanner(System.in);
    private static AtmController atmController;

    public AtmView(AtmController atmController) {
        this.atmController = atmController;
    }

    public void start() {
        if (atmController.download())
        {
            while (true) {
                try {
                    boolean flagRun = false;
                    do {
                        try {
                            System.out.println("    Введите номер карты");
                            if (atmController.cardNumberCheck(new BankCard(scanner.next()))) {
                                flagRun = true;
                            }
                        } catch (IncorrectNumberException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (!flagRun);
                    flagRun = false;
                    do {
                        try {
                            System.out.println("    Введите пинкод карты");
                            if (atmController.authorize(scanner.next())) {
                                flagRun = true;
                            }
                        } catch (IncorrectPinException e) {
                            System.out.println(e.getMessage());
                        } catch (WrongPincodeException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (!flagRun);
                    String menuSelection = "";
                    do {
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
                        }
                        atmController.update();
                    } while (true);
                } catch (NotAuthorizedException e) {
                    System.out.println(e.getMessage());

                } catch (BankCardBlocked e) {
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

    private void withdrawMoney() {
        try {
            String money = "";
            System.out.print(" Введите сумму  ");
            money = scanner.next();
            if(atmController.withdrawMoney(money)){
                System.out.println("Денги успешно сняты");
            }
        } catch (NumberFormatException e){
            System.out.println("Некорректная денежная сумма");
        }catch (CashWithdrawalException e){
            System.out.println(e.getMessage());
        }catch (NotEnoughMoneyException e){
            System.out.println(e.getMessage());
        }catch (NotEnoughMoneyAtmException e){
            System.out.println(e.getMessage());
        }
    }

    private void topUpAccount() {
        try {
            String money = "";
            System.out.print(" Введите сумму  ");
            money = scanner.next();
            if (atmController.topUpAccount(money)) {
                System.out.println("Денги внесены успешно");
            }
        }catch (AmountMoneyException e){
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Некорректная денежная сумма");
        }
    }

    private void getCheckBalance() {
            System.out.println("Текущий бананс : " +  atmController.checkCardBalance());
    }

}
