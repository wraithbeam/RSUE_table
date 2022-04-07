package com.example.rsue;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class Table {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private XSSFSheet sheet;

    Table(String location) throws IOException, FileNotFoundException {
        fileInputStream = new FileInputStream(location);
        workbook = new XSSFWorkbook(fileInputStream);
    }

    XSSFWorkbook getWorkBook() {
        return workbook;
    }

    String getRows(int numberSheet, int numberRow) {

        XSSFSheet sheet = workbook.getSheetAt(numberSheet);

        for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            Row row = sheet.getRow(i);

        }

        return "popa";
    }
}

