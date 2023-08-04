package eu.senla.atm.dao;

import eu.senla.atm.model.Atm;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AtmDao {

    private Atm atm;
    private static final String  FILE_NAME = "data.txt";


    public void addAtm(Atm atm){
        this.atm = atm;
    }
    public Atm returnAtm(){return atm;}
    public void topUpAtm(int money){
        atm.setAmountMoney(atm.getAmountMoney() + money);
    }

    public void takeOff(int money){
        atm.setAmountMoney(atm.getAmountMoney() - money);
    }
    public void save(BankCardDao bankCardDao){
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, false)) { }
        catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(FILE_NAME, true)){
            int a = atm.getAmountMoney();
            writer.write(String.valueOf(a));
            bankCardDao.save(writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public String[] download(){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_NAME))) {
            String dateFile = reader.readLine();
            String data[]  = dateFile.split(" ");
            addAtm(new Atm(Integer.parseInt(data[0])));
            int size = data.length;
            return Arrays.copyOfRange(data, 1, size);
            }
        catch (Exception e){
            return null;
        }
    }

}
