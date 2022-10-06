package com.example.controller;

import com.example.model.Employee;
import com.example.response.ResponseMessage;
import com.example.service.ExcelFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin("http://localhost:8080")
//@Controller
@RestController
@RequestMapping("/api/v1/file")
@Api(tags = "Read/Write Files")
public class ReadExcelFileController {
    private final ExcelFileService excelFileService;

    @Autowired
    public ReadExcelFileController(ExcelFileService excelFileService) {
        this.excelFileService = excelFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestPart(value = "file",required = true) MultipartFile file) {
        excelFileService.save(file);
        return ResponseEntity.ok().body(new ResponseMessage("Successfully upload file"));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok().body(excelFileService.findAll());
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String fileName = "employees.xlsx";
        InputStreamResource file = new InputStreamResource(excelFileService.download());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
