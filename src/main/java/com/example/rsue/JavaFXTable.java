package com.example.rsue;


import javafx.beans.Observable;
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
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println(e);
        }
    }

    /**
     * Выводит информацию с разных листов Excel в таблицу FX
     */
    public ObservableList<Row> getRowsAll(){
        ObservableList<Row> finalArray =  FXCollections.observableArrayList();
        try {
            ArrayList<ArrayList<String>> clients = table.getRows("Клиенты");
            ArrayList<ArrayList<String>> buys= table.getRows("Подписки");
            ArrayList<ArrayList<String>> products = table.getRows("Продукты");
            System.out.println("Log: AllTable | ");

            for (ArrayList<String> buy: buys) {
                for (ArrayList<String> client : clients) {
                    for (ArrayList<String> product : products) {
                        if ((Integer.parseInt( buy.get(0)) == Integer.parseInt(client.get(0))) && (Integer.parseInt(buy.get(1)) == Integer.parseInt(product.get(0)))){
                            Row row = new Row();
                            System.out.println(client.get(1) + ' ' +
                                    client.get(2).charAt(0) + '.' +
                                    client.get(3).charAt(0) + ". " +
                                    product.get(1) + " \"" +
                                    product.get(2) + "\" " +
                                    client.get(5) + "/" + client.get(4));

                            row.setFio(client.get(1) + ' ' +
                                    client.get(2).charAt(0) + '.' +
                                    client.get(3).charAt(0) + ". ");
                            row.setType(product.get(1));
                            row.setTitle("\"" + product.get(2) + "\"");
                            row.setTime( client.get(5) + "/" + client.get(4));
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

}
