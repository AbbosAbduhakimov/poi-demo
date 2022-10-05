package com.example.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class ReadExcelFileService {
    public static Path path = Paths.get("C:\\Users\\USER\\Downloads\\git-projects\\poi-project\\src\\main\\resources\\file\\newFile.xlsx");

    public static void main(String[] args) throws Exception {

        writeExcelFile();
//        fontExcelFile();

        readExcelFile();

    }
    private static void writeExcelFile() throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Info employees");

            XSSFRow row;

            Map<String, Object[]> empInfo = new TreeMap<>();

            empInfo.put("1", new Object[]{"EMP ID", "EMP NAME", "DESIGNATION"});
            empInfo.put("2", new Object[]{"tp01", "John", "Technical Manager"});
            empInfo.put("3", new Object[]{"tp02", "Sarah", "Proof Reader"});
            empInfo.put("4", new Object[]{"tp03", "Mark", "Technical Writer"});
            empInfo.put("5", new Object[]{"tp04", "Bob", "Technical Writer"});
            empInfo.put("6", new Object[]{"tp05", "Lucy", "Technical Writer"});

            Set<String> keyId = empInfo.keySet();
            int rowId = 0;

            for (String key : keyId) {
                row = sheet.createRow(rowId++);
                Object[] infoArray = empInfo.get(key);
                int cellId = 0;


                for (Object obj : infoArray) {
                    Cell cell = row.createCell(cellId++);
                    cell.setCellValue((String) obj);
                }

                FileOutputStream out = new FileOutputStream(path.toString());
                workbook.write(out);
                out.close();
            }
        }
    }


    private static void readExcelFile() throws IOException {

        try (FileInputStream input = new FileInputStream(path.toString());
             XSSFWorkbook workbook = new XSSFWorkbook(input)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cell.getCellType() == CellType.NUMERIC) {
                        System.out.print(
                                cell.getNumericCellValue() + "\t\t "
                        );
                    }

                    if (cell.getCellType() == CellType.STRING) {
                        System.out.print(
                                cell.getStringCellValue() + "\t\t "
                        );
                    }
                }
                System.out.println();
            }

        }
    }

//    private static void fontExcelFile() throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet spreadsheet = workbook.createSheet("Fontstyle");
//        XSSFRow row = spreadsheet.createRow(2);
//
//        XSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints((short) 30);
//        font.setFontName("IMPACT");
//        font.setItalic(true);
//        font.setBold(true);
//        font.setColor(IndexedColors.DARK_RED.index);
//
//        XSSFCellStyle style = workbook.createCellStyle();
//        style.setFont(font);
//
//        XSSFCell cell = row.createCell(1);
//        cell.setCellValue("Font Style");
//        cell.setCellStyle(style);
//
//        FileOutputStream out = new FileOutputStream("fontstyle.xlsx");
//        workbook.write(out);
//        out.close();
//    }

}
