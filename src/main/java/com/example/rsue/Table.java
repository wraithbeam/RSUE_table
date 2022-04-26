package com.example.rsue;


import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class Table {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private XSSFSheet sheet;


    public Table(String location) throws IOException, FileNotFoundException {
        fileInputStream = new FileInputStream(location);
        workbook = new XSSFWorkbook(fileInputStream);
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
    public ArrayList<String> getRows(int indexSheet){
        XSSFSheet sheet = workbook.getSheetAt(indexSheet);
        return getRows(sheet);
    }
    public ArrayList<String> getRows(String nameSheet){
        XSSFSheet sheet = workbook.getSheet(nameSheet);
        return getRows(sheet);
    }

    private ArrayList<String> getRows(XSSFSheet sheet) {
        ArrayList<String> rows = new ArrayList<String>();
        try
        {
            for (short rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++){
                XSSFRow row = sheet.getRow(rowIndex);
                for (short cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++){
                    XSSFCell cell = row.getCell(cellIndex);
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            rows.add(String.valueOf((int)cell.getNumericCellValue()));
                            break;
                        case STRING:
                            rows.add(cell.getStringCellValue());
                            break;
                    }
                }
                rows.add("\n");
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

