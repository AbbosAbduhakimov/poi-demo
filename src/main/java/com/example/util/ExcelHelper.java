package com.example.util;

import com.example.exception.ProjectBadRequestException;
import com.example.exception.ProjectParseException;
import com.example.model.Employee;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@UtilityClass
public class ExcelHelper {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET_NAME = "Employees";
    static String HEADRs[] = {"EMP_ID", "EMP_NAME", "SALARY", "DESIGNATION"};


    public static void hasExcelFile(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            throw new ProjectBadRequestException("invalid contentType");
        }
    }

    public static Iterable<Employee> excelFileToEmployee(InputStream inputStream) {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);

            Iterator<Row> rowIterator = sheet.iterator();

            List<Employee> employees = new ArrayList<>();

            int rowIndex = 0;
            while (rowIterator.hasNext()) {
                Row currentROw = rowIterator.next();

                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentROw.cellIterator();

                Employee employee = new Employee();


                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            employee.setId(currentCell.getStringCellValue());
                            break;
                        case 1:
                            employee.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            employee.setSalary(Double.valueOf(currentCell.getStringCellValue()));
                            break;
                        case 3:
                            employee.setDesignation(currentCell.getStringCellValue());
                        default:
                            break;
                    }
                    cellIndex++;
                }
                employees.add(employee);
            }
            return employees;
        } catch (IOException e) {
            throw new ProjectParseException("Conflict on parsing file " + e.getMessage());
        }

    }


    public static ByteArrayInputStream dbEmployeeInfoToExcelFile(List<Employee> employees) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            XSSFSheet sheet = workbook.createSheet(SHEET_NAME);

            // header
            Row headerRow = sheet.createRow(0);

            for (int column = 0; column < HEADRs.length; column++) {
                Cell cell = headerRow.createCell(column);
                cell.setCellValue(HEADRs[column]);
            }

            // skipped headerRow
            int rowIndex = 1;
            for (Employee employee : employees) {
                Row currentRow = sheet.createRow(rowIndex++);

                currentRow.createCell(0).setCellValue(employee.getId());
                currentRow.createCell(1).setCellValue(employee.getName());
                currentRow.createCell(2).setCellValue(employee.getSalary());
                currentRow.createCell(3).setCellValue(employee.getDesignation());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new ProjectParseException("Conflict on parsing file " + e.getMessage());
        }

    }
}





