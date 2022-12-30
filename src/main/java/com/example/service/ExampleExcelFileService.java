package com.example.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class ExampleExcelFileService {
    public static Path path = Paths.get("C:\\Users\\USER\\Downloads\\git-projects\\poi-project\\src\\main\\resources\\file\\newFile.xlsx");

    public static void main(String[] args) throws Exception {

        writeExcelFile();
        readExcelFile();

    }

    private static void writeExcelFile() throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Info employees");

            XSSFRow row;

            Map<String, Object[]> empInfo = new TreeMap<>();

            empInfo.put("1", new Object[]{"EMP_ID", "EMP_NAME", "SALARY", "DESIGNATION"});
            empInfo.put("2", new Object[]{"tp01", "John", 6000.500, "CEO"});
            empInfo.put("3", new Object[]{"tp02", "Sarah", 5000.400, "Project Manager"});
            empInfo.put("4", new Object[]{"tp03", "Mark", 3000.200, "Programmer"});
            empInfo.put("5", new Object[]{"tp04", "Bob", 3000.200, "Programmer"});
            empInfo.put("6", new Object[]{"tp05", "Lucy", 3000.200, "Programmer"});


            Set<String> keyId = empInfo.keySet();
            int rowId = 0;

            for (String key : keyId) {
                row = sheet.createRow(rowId++);
                Object[] infoArray = empInfo.get(key);
                int cellId = 0;


                for (Object obj : infoArray) {
                    Cell cell = row.createCell(cellId++);
                    cell.setCellValue(String.valueOf(obj));
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


}
