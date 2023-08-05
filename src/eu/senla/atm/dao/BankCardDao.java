package eu.senla.atm.dao;

import eu.senla.atm.model.BankCard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BankCardDao {

    private final ArrayList<BankCard> bankCards;
    public BankCardDao(){
        bankCards = new ArrayList<>();
    }
    public void addCards(BankCard bankCard){
        bankCards.add(bankCard);
    }
    public BankCard getCardByNum(String numberCard){
        for (BankCard bankCard : bankCards) {
            if (Objects.equals(bankCard.getNumberCard(), numberCard)) {
                return bankCard;
            }

        }
        return  null;

    }
    public void save(FileWriter writer ) throws IOException {
        for (BankCard bankCard : bankCards) {
            writer.write(" " + bankCard.getNumberCard());
            writer.write(" " + bankCard.getPinCod());
            writer.write(" " + bankCard.getBankAccount().getBalanceCard());
            writer.write(" " + bankCard.getDateLocked());
        }
    }
    public void load(String[] date) {
        for(int i =0; i < date.length; ){
            addCards(new BankCard(date[i],
                    date[i+1],Integer.parseInt(date[i+2]),
                    Long.parseLong(date[i+3])));
            i += 4;
        }
    }
}
