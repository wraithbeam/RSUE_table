package com.example.rsue;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class Table {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private String location;


    public Table(String location) throws IOException {
        try {
            fileInputStream = new FileInputStream(location);
            workbook = new XSSFWorkbook(fileInputStream);
            this.location = location;
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public XSSFWorkbook getWorkBook() {
        return workbook;
    }

    public ArrayList<String> getTitles(int indexSheet){
        XSSFSheet sheet = workbook.getSheetAt(indexSheet);
        return getTitles(sheet);
    }
    public ArrayList<String> getTitles(String nameSheet){
        XSSFSheet sheet = workbook.getSheet(nameSheet);
        return getTitles(sheet);
    }
    public ArrayList<ArrayList<String>> getRows(int indexSheet){
        XSSFSheet sheet = workbook.getSheetAt(indexSheet);
        return getRows(sheet);
    }
    public ArrayList<ArrayList<String>> getRows(String nameSheet){
        XSSFSheet sheet = workbook.getSheet(nameSheet);
        return getRows(sheet);
    }

    private ArrayList<ArrayList<String>> getRows(XSSFSheet sheet) {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        try
        {
            for (short rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++){
                XSSFRow row = sheet.getRow(rowIndex);
                ArrayList<String> oneRow = new ArrayList<>();
                for (short cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++){
                    XSSFCell cell = row.getCell(cellIndex);
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            oneRow.add(String.valueOf((int)cell.getNumericCellValue()));
                            break;
                        case STRING:
                            oneRow.add(cell.getStringCellValue());
                            break;
                    }
                }
                rows.add(oneRow);
            }
            fileInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return rows;
    }
    private ArrayList<String> getTitles(XSSFSheet sheet) {
        ArrayList<String> titles = new ArrayList<String>();
        try {
            XSSFRow row = sheet.getRow(0);
            for (short cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                XSSFCell cell = row.getCell(cellIndex);
                switch (cell.getCellType()) {
                    case NUMERIC:
                        titles.add(String.valueOf((int) cell.getNumericCellValue()));
                        break;
                    case STRING:
                        titles.add(cell.getStringCellValue());
                        break;
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }


    public void deleteRow(int rowIndex){
        try {
            XSSFSheet sheet = workbook.getSheet("Подписки");
            int lastRowNum = sheet.getLastRowNum();
            if (rowIndex >= 0 && rowIndex < lastRowNum) {
                sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
            }
            if (rowIndex == lastRowNum) {
                XSSFRow removingRow = sheet.getRow(rowIndex);
                if (removingRow != null) {
                    sheet.removeRow(removingRow);
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void save(){
        try {
            File outWB = new File(location);
            OutputStream out = new FileOutputStream(outWB);
            workbook.write(out);
            out.flush();
            out.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
    public void addRow(Row row){
        try {
            XSSFSheet clients = workbook.getSheet("Клиенты");
            XSSFSheet products = workbook.getSheet("Продукты");
            XSSFSheet buys = workbook.getSheet("Подписки");
            XSSFSheet izdatelstva = workbook.getSheet("Издательство");

            XSSFRow xRow = clients.createRow(clients.getLastRowNum() + 1);
            xRow.createCell(0).setCellValue(Integer.parseInt(row.getIndex()));
            xRow.createCell(1).setCellValue(row.getSurname());
            xRow.createCell(2).setCellValue(row.getName());
            xRow.createCell(3).setCellValue(row.getOtchestvo());
            xRow.createCell(4).setCellValue(row.getTimeAtAll());
            xRow.createCell(5).setCellValue(row.getTimeInYear());
            xRow.createCell(6).setCellValue(row.getAddress());

            if (!isThisProductInListYet(row, products)){
                xRow = products.createRow(products.getLastRowNum() + 1);
                xRow.createCell(0).setCellValue(Integer.parseInt(row.getArticle()));
                xRow.createCell(1).setCellValue(row.getType());
                xRow.createCell(2).setCellValue(row.getTitle());
                xRow.createCell(3).setCellValue(row.getIzdatel());

                if (!isThisIzdatelInListYet(row, izdatelstva)){
                    xRow = izdatelstva.createRow(izdatelstva.getLastRowNum() + 1);
                    xRow.createCell(0).setCellValue(row.getIzdatel());
                    xRow.createCell(1).setCellValue(row.getAddressCompany());

                }
            }

            xRow = buys.createRow(buys.getLastRowNum() + 1);
            xRow.createCell(0).setCellValue(row.getIndex());
            xRow.createCell(1).setCellValue(row.getArticle());

            save();
        }catch (Exception e){
            System.out.println("Ошибка добавления в Эксель | " + e);
        }
    }
    public void editRow(Row row){
        XSSFSheet clients = workbook.getSheet("Клиенты");
        XSSFSheet products = workbook.getSheet("Продукты");

        for (short rowIndex = 1; rowIndex < clients.getPhysicalNumberOfRows(); rowIndex++) {
            XSSFRow xRow = clients.getRow(rowIndex);
            XSSFCell cell = xRow.getCell(0);
            if (cell.getNumericCellValue() == Integer.parseInt(row.getIndex())) {
                xRow.getCell(1).setCellValue(row.getSurname());
                xRow.getCell(2).setCellValue(row.getName());
                xRow.getCell(3).setCellValue(row.getOtchestvo());
                xRow.getCell(4).setCellValue(row.getTimeAtAll());
                xRow.getCell(5).setCellValue(row.getTimeInYear());
                xRow.getCell(6).setCellValue(row.getAddress());
            }
        }
        for (short rowIndex = 1; rowIndex < products.getPhysicalNumberOfRows(); rowIndex++) {
            XSSFRow xRow = products.getRow(rowIndex);
            XSSFCell cell = xRow.getCell(0);
            if (cell.getNumericCellValue() == Integer.parseInt(row.getArticle())) {
                xRow.getCell(0).setCellValue(Integer.parseInt(row.getArticle()));
                xRow.getCell(1).setCellValue(row.getType());
                xRow.getCell(2).setCellValue(row.getTitle());
                xRow.getCell(3).setCellValue(row.getIzdatel());
            }
        }

    }

    private boolean isThisProductInListYet(Row row, XSSFSheet products){
        for (int rowIndex = 1; rowIndex < products.getPhysicalNumberOfRows(); rowIndex++){
            if (products.getRow(rowIndex).getCell(0).getNumericCellValue() == Integer.parseInt(row.getArticle())) {
                return true;
            }
        }
        return false;
    }

    private boolean isThisIzdatelInListYet(Row row, XSSFSheet izdatel){
        for (int rowIndex = 1; rowIndex < izdatel.getPhysicalNumberOfRows(); rowIndex++){
            if (izdatel.getRow(rowIndex).getCell(0).getStringCellValue() == row.getIzdatel()) {
                return true;
            }
        }
        return false;
    }

}

