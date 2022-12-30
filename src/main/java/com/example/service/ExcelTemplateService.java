package com.example.service;

import com.example.mapper.CompanyMapper;
import com.example.mapper.EmployeeMapper;
import com.example.model.Company;
import com.example.model.Employee;
import com.example.model.dto.CompanyMigrationDto;
import com.example.model.dto.EmployeeMigrationDto;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.type.ApplicationType;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelTemplateService {

    private final Map<ApplicationType, String[]> pattern;

    {
        pattern = new HashMap<>();

        pattern.put(ApplicationType.EMPLOYEE, new String[]{"ID", "NAME", "SALARY", "DESIGNATION"});
        pattern.put(ApplicationType.COMPANY, new String[]{"ID", "NAME", "ADDRESS", "POST_INDEX", "ZIP_CODE"});
    }

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CompanyMapper companyMapper;

    public ExcelTemplateService(CompanyRepository companyRepository, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.companyMapper = companyMapper;
    }


    public ByteArrayResource getTemplate(ApplicationType type) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook();) {
            Sheet sheet = workbook.createSheet(type.toString());
            Row header = sheet.createRow(0);
            Cell headerCell;
            String[] headerName = pattern.get(type);
            for (int i = 0; i < headerName.length; i++) {
                headerCell = header.createCell(i);
                headerCell.setCellValue(headerName[i]);
            }
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    public void saveEntity(MultipartFile file, ApplicationType type) throws IOException {

        switch (type) {
            case COMPANY:
                saveCompany(file);
            case EMPLOYEE:
                saveEmployee(file);
        }
    }

    private void saveEmployee(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();
        Poiji.fromExcel(new ByteArrayInputStream(file.getBytes()), PoijiExcelType.XLSX, EmployeeMigrationDto.class).forEach(
                dto -> {
                    Employee employee = employeeMapper.employeeMapper(dto);
                    employees.add(employee);

                }
        );
        employeeRepository.saveAll(employees);

    }

    private void saveCompany(MultipartFile file) throws IOException {
        List<Company> companies = new ArrayList<>();
        Poiji.fromExcel(new ByteArrayInputStream(file.getBytes()), PoijiExcelType.XLSX, CompanyMigrationDto.class).forEach(
                dto -> {
                    Company company = companyMapper.companyMapper(dto);
                    companies.add(company);
                });
        companyRepository.saveAll(companies);
    }
}