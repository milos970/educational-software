package com.milos.numeric.controller;

import com.milos.numeric.dto.request.MaterialRequest;
import com.milos.numeric.entity.Material;
import com.milos.numeric.service.MaterialService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;


    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> uploadFile(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("data") MultipartFile file,
            @RequestParam("uploadedBy") String uploadedBy) {

        MaterialRequest dto = new MaterialRequest();
        dto.setName(name);
        dto.setDescription(description);
        dto.setData(file);
        dto.setUploadedBy(uploadedBy);
        this.materialService.createMaterial(dto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("{id}")
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

    @GetMapping("{name}")
    public ResponseEntity<Resource> findByName(@PathVariable String name) {
        Material material = materialService.findByName(name);
        Resource pdfResource = materialService.getMaterialResource(material.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getMimeType()));
        headers.setContentDispositionFormData("inline", material.getName());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfResource, headers, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity removeSpecificFile(@PathVariable Long id) {
        this.materialService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
