package com.example.rsue;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Table {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private XSSFSheet sheet;


    public Table(String location) throws IOException {
        try {
            fileInputStream = new FileInputStream(location);
            workbook = new XSSFWorkbook(fileInputStream);
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

}

