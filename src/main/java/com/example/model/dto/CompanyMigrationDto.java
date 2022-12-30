package com.example.model.dto;

import com.poiji.annotation.ExcelCellName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyMigrationDto {

    @ExcelCellName("ID")
    private Integer id;
    @ExcelCellName("NAME")
    private String name;

    @ExcelCellName("ADDRESS")
    private String address;

    @ExcelCellName("POST_INDEX")
    private String postIndex;

    @ExcelCellName("ZIP_CODE")
    private String zipCode;
}
