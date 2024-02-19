package com.milos.numeric.controllers;

import com.milos.numeric.converters.CSVConverter;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.entities.Pdf;
import com.milos.numeric.entities.UnregisteredPerson;
import com.milos.numeric.services.FileService;
import com.milos.numeric.services.UnregisteredPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
public class FileUploadController
{


    @Autowired
    private FileService fileService;

    @Autowired
    private UnregisteredPersonService unregisteredPersonService;


    @Autowired
    private CSVConverterUnregisteredPerson csvConverterUnregisteredPerson;


    @PostMapping("/file/upload-csv")
    public void uploadCSV(@RequestParam("csv") MultipartFile csv) throws IOException
    {

        List<UnregisteredPerson> list = this.csvConverterUnregisteredPerson.convert(csv);

        this.unregisteredPersonService.create(list);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        try
        {
            this.fileService.store(file);
            return "redirect:/file/pdf";
        } catch (Exception e) {
            return "redirect:/file/pdf";

        }
    }

    @GetMapping("/file/pdf")
    public String getFiles(Model model)
    {
        for (Pdf item : this.fileService.getAllFiles())
        {
            System.out.println(item.getName());
        }
        model.addAttribute("pdf", this.fileService.getAllFiles());
        return "list";
    }

    @GetMapping("/file/pdf/{id}")
    public ResponseEntity<byte[]> getPDF1(@PathVariable int id)
    {
        String filename = "pdf1.pdf";
        byte[] pdf = this.fileService.getFile(id).getData();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }


}
