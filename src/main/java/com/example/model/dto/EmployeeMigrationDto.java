package com.example.model.dto;

import com.poiji.annotation.ExcelCellName;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class EmployeeMigrationDto {

    @ExcelCellName("ID")
    private Integer id;
    @ExcelCellName("NAME")
    private String name;
    @ExcelCellName("SALARY")
    private Double salary;
    @ExcelCellName("DESIGNATION")
    private String designation;
}
