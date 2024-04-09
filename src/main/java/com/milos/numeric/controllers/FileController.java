package com.milos.numeric.controllers;

import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.entities.File;
import com.milos.numeric.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@Controller
public class FileController {
    private final FileService fileService;
    private final CSVConverterUnregisteredPerson csvConverterUnregisteredPerson;

    @Autowired
    public FileController(FileService fileService, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson) {
        this.fileService = fileService;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
    }

    @PostMapping("/admin/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        System.out.println(45);
        try {
            this.fileService.store(file);
            return "redirect:/file/pdf";
        } catch (Exception e) {
            return "redirect:/file/pdf";

        }
    }


    @GetMapping("/person/material/file/{id}")
    public ResponseEntity<String> getSpecificFile(@PathVariable Long id) {
        Optional<File> optional = this.fileService.findById(id);

        if (optional.isEmpty()) {

        }

        File file = optional.get();
        Path filePath = Paths.get(file.getPath());

        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String base64EncodedFile = Base64.getEncoder().encodeToString(fileBytes);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(file.getMimeType()));

        headers.add("content-disposition", "inline;filename=" + file.getName());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(base64EncodedFile, headers, HttpStatus.OK);
    }

    @DeleteMapping("/admin/file/delete/{id}")
    public ResponseEntity<byte[]> removeSpecificFile(@PathVariable Long id) {
        this.fileService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
