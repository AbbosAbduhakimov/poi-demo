package com.example.service;

import com.example.exception.ProjectNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelFileService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ExcelFileService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public void save(MultipartFile file) {
        ExcelHelper.hasExcelFile(file);
        try {
            Iterable<Employee> employees = ExcelHelper.excelFileToEmployee(file.getInputStream());
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ProjectNotFoundException("No content");
        }
        return employees;
    }


    public ByteArrayInputStream download(){
        List<Employee> employees = employeeRepository.findAll();
        return ExcelHelper.dbEmployeeInfoToExcelFile(employees);
    }

}
