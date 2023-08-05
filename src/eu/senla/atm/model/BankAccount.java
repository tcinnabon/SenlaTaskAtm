package eu.senla.atm.model;

public class BankAccount {
    private int balanceCard;

    public BankAccount(int balanceCard) {

        this.balanceCard = balanceCard;
    }

    public int getBalanceCard() {
        return balanceCard;
    }

    public void setBalanceCard(int balanceCard) {
        this.balanceCard = balanceCard;
    }


}
