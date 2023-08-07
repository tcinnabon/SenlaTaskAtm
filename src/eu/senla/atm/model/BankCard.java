package eu.senla.atm.model;

public class BankCard {
    private final String numberCard;
    private String pinCod;

    private BankAccount bankAccount;
    private int numberFailed;
    private long dateLocked;

    public BankCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public BankCard(String numberCard, String pinCod, int balanceCard, long dateLocked) {
        this.numberCard = numberCard;
        this.pinCod = pinCod;
        this.bankAccount = new BankAccount(balanceCard);
        this.numberFailed = 0;
        this.dateLocked = dateLocked;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setNumberFailed(int numberFailed) {
        this.numberFailed = numberFailed;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public String getPinCod() {
        return pinCod;
    }

    public int getNumberFailed() {
        return this.numberFailed;
    }

    public void setDateLocked(long dateLocked) {
        this.dateLocked = dateLocked;
    }

    public long getDateLocked() {
        return dateLocked;
    }


}