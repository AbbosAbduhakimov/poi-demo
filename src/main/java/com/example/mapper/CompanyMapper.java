package com.example.mapper;

import com.example.model.Company;
import com.example.model.dto.CompanyMigrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company companyMapper(CompanyMigrationDto dto);
}
