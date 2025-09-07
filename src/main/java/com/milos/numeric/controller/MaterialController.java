package com.milos.numeric.controller;

import com.milos.numeric.dto.MaterialDto;
import com.milos.numeric.entity.Material;
import com.milos.numeric.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @PostMapping("/materials")
    public ResponseEntity<Long> saveFile(@Valid @RequestBody MaterialDto materialDto) {
        Material material = materialService.createMaterial(materialDto);

        if (material != null) {
            return ResponseEntity.ok(material.getId());
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }




    @GetMapping("materials/{id}")
    public ResponseEntity<byte[]> findById(@PathVariable Long id) {

        Material material = this.materialService.findById(id);
        Path filePath = Paths.get(material.getPath());

        try {
            byte[] fileBytes = Files.readAllBytes(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(material.getMimeType()));
            headers.setContentDispositionFormData("inline", material.getName());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("materials/{id}")
    public ResponseEntity removeSpecificFile(@PathVariable Long id) {
        this.materialService.delete(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }


}
