package eu.senla.atm;

import eu.senla.atm.controller.AtmController;
import eu.senla.atm.controller.BankCardController;
import eu.senla.atm.dao.AtmDao;
import eu.senla.atm.dao.BankCardDao;
import eu.senla.atm.view.AtmView;

public class Main {
    public static void main(String[] args) {

        AtmDao atmDao = new AtmDao();
        BankCardDao bankCardDao = new BankCardDao();
        BankCardController bankCardController = new BankCardController(bankCardDao);
        AtmController atmController = new AtmController(bankCardController, atmDao);
        AtmView atmView = new AtmView(atmController);
        atmView.start();

    }
}