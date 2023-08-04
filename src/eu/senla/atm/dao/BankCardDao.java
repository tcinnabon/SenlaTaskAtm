package eu.senla.atm.dao;

import eu.senla.atm.model.BankCard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankCardDao {

    private ArrayList<BankCard> bankCards;
    public BankCardDao(){
        bankCards = new ArrayList<>();
    }
    public void add(BankCard bankCard){
        bankCards.add(bankCard);
    }
    public BankCard getCardByNum(String numberCard){
        for(int i =0; i < bankCards.size(); i++){
            if(Objects.equals(bankCards.get(i).getNumberCard(), numberCard)){
                return bankCards.get(i);
            }

        }
        return  null;

    }
    public ArrayList<BankCard> save(FileWriter writer ) throws IOException {
        for (int i =0; i < bankCards.size(); i++){
            writer.write(" " + bankCards.get(i).getNumberCard());
            writer.write(" " + bankCards.get(i).getPinCod());
            writer.write(" " + bankCards.get(i).getBalanceCardFromBankAccaount());
            writer.write(" " + bankCards.get(i).getDateLocked());
        }

        return bankCards;
    }
    public void download(){ }



}
