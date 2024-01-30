package com.milos.numeric.controllers;

import com.milos.numeric.CsvConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class FileUploadController
{
    @Autowired
    private CsvConverter converter;

    @PostMapping("/post-file")
    public void addCSV(@RequestParam("file") MultipartFile file) throws IOException {
        converter.convert(file);
    }
}
