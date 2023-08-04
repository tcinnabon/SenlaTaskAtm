package eu.senla.atm.model;

public class BankAccount {
    private int balanceCard;

    public BankAccount(int balanceCard) {

        this.balanceCard = balanceCard;
    }

    public void topUpAccount(int money){
        balanceCard+=money;
    }

    public void withdrawMoney(int money){
        balanceCard -=money;
    }

    public int checkCardBalance(){
        return balanceCard;
    }

}
