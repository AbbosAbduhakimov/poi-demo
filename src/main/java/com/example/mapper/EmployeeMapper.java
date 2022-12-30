package com.example.mapper;

import com.example.model.Employee;
import com.example.model.dto.EmployeeMigrationDto;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface EmployeeMapper  {
    Employee employeeMapper(EmployeeMigrationDto dto);
}
