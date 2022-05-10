package com.example.rsue;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JavaFXTable {
    private Table table;

    public JavaFXTable(File file){
        try {
            table = new Table(file.getPath());
        } catch (IOException e) {
            table = null;
            e.printStackTrace();
        } catch (NullPointerException e){
            table = null;
            System.out.println(e);
        }
    }

    /**
     * Объединяет информацию с разных листов в массив строк
     */
    public ObservableList<Row> getRowsAll(){
        ObservableList<Row> finalArray =  FXCollections.observableArrayList();
        try {
            ArrayList<ArrayList<String>> clients = table.getRows("Клиенты");
            ArrayList<ArrayList<String>> buys= table.getRows("Подписки");
            ArrayList<ArrayList<String>> products = table.getRows("Продукты");
            ArrayList<ArrayList<String>> izdateli = table.getRows("Издательство");
            System.out.println("Log: AllTable | Read");

            for (ArrayList<String> buy: buys) {
                for (ArrayList<String> client : clients) {
                    for (ArrayList<String> product : products) {
                        if ((Integer.parseInt( buy.get(0)) == Integer.parseInt(client.get(0))) && (Integer.parseInt(buy.get(1)) == Integer.parseInt(product.get(0)))){
                            Row row = new Row();

                            row.setFio(client.get(1) + ' ' +
                                    client.get(2).charAt(0) + '.' +
                                    client.get(3).charAt(0) + ". ");
                            row.setSurname(client.get(1));
                            row.setName(client.get(2));
                            row.setOtchestvo(client.get(3));
                            row.setIndex(client.get(0));
                            row.setAddress(client.get(6));

                            row.setArticle(product.get(0));
                            row.setType(product.get(1));
                            row.setTitle("\"" + product.get(2) + "\"");
                            row.setIzdatel(product.get(3));

                            //Заполнение адресса
                            for (ArrayList<String> izdatel: izdateli) {
                                if (product.get(3) == izdatel.get(0))
                                    row.setAddressCompany(izdatel.get(1));
                            }

                            row.setTime( client.get(5) + "/" + client.get(4));
                            row.setTimeInYear(client.get(5));
                            row.setTimeAtAll(client.get(4));
                            finalArray.add(row);
                        }
                    }
                }
            }
            return finalArray;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public void deleteRow(Row row){
        ArrayList<ArrayList<String>> buys= table.getRows("Подписки");
        short i = 1;
        for (ArrayList<String> buy: buys) {
            if ((Integer.parseInt(buy.get(0)) == Integer.parseInt(row.getIndex())) && (Integer.parseInt(buy.get(1)) == Integer.parseInt(row.getArticle()))) {
               table.deleteRow(i);
               return;
            }
            i++;
        }
    }

    public void addRow(Row row){
        table.addRow(row);
    }
    public void editRow(Row row){
        table.editRow(row);
    }

    public void save(){
        table.save();
    }

    public ArrayList<String> getColumnAtSheet(String nameSheet, int indexColumn){
        return table.getColumnAtSheet(nameSheet, indexColumn);
    }


    public boolean isTableOpen(){
        if (table != null)
            return true;
        else
            return false;
    }


}
