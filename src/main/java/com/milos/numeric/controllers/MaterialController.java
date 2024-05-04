package com.milos.numeric.controllers;

import com.milos.numeric.dtos.MaterialDto;
import com.milos.numeric.entities.Material;
import com.milos.numeric.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@Controller
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @PostMapping("admin/material/upload")
    public ResponseEntity saveFile(@Valid MaterialDto materialDto)
    {

        Material material = this.materialService.save(materialDto);

        if (material!= null)
        {
            return new ResponseEntity<>(material.getId(), HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @GetMapping("person/material/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {

        Optional<Material> optional = this.materialService.findById(id);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Material material = optional.get();
        Path filePath = Paths.get(material.getPath());

        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String base64EncodedFile = Base64.getEncoder().encodeToString(fileBytes);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(material.getMimeType()));

        headers.add("content-disposition", "inline;filename=" + material.getName());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(base64EncodedFile, headers, HttpStatus.OK);
    }

    @DeleteMapping("admin/material/delete/{id}")
    public ResponseEntity removeSpecificFile(@PathVariable Long id) {
        this.materialService.delete(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }


}
