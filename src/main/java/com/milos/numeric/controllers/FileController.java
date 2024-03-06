package com.milos.numeric.controllers;

import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;


@Controller
public class FileController
{
    private final FileService fileService;
    private final CSVConverterUnregisteredPerson csvConverterUnregisteredPerson;


    @Autowired
    public FileController(FileService fileService, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson) {
        this.fileService = fileService;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
    }

    @PostMapping("/admin/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        try
        {
            this.fileService.store(file);
            return "redirect:/file/pdf";
        } catch (Exception e) {
            return "redirect:/file/pdf";

        }
    }

    @GetMapping("/admin/file/pdf")
    public ModelAndView getFiles()
    {
        return new ModelAndView("list-of-files", "files", this.fileService.getAllFiles());
    }

    @GetMapping("/admin/file/pdf/{id}")
    public ResponseEntity<byte[]> getSpecificFile(@PathVariable Integer id)
    {
        String filename = "pdf";
        byte[] pdf = this.fileService.getFile(id).getData();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @DeleteMapping("/admin/file/pdf/delete/{id}")
    public ResponseEntity<byte[]> removeSpecificFile(@PathVariable Integer id)
    {
        this.fileService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
