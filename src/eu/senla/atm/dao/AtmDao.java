package eu.senla.atm.dao;

import eu.senla.atm.model.Atm;

import java.io.FileWriter;
import java.io.IOException;

public class AtmDao {

    private Atm atm;

    public void setAtm(Atm atm){
        this.atm = atm;
    }
    public Atm getAtm(){return atm;}

    public void save(FileWriter writer) throws IOException {
        int a = atm.getAmountMoney();
        writer.write(String.valueOf(a));
    }

    public void load(int atmAmountMoney) {
        setAtm(new Atm(atmAmountMoney));
    }
}
