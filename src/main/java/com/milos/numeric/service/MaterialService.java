package com.milos.numeric.service;

import com.milos.numeric.dto.request.MaterialRequest;
import com.milos.numeric.entity.Material;
import com.milos.numeric.exception.FileStorageException;
import com.milos.numeric.mapper.MaterialDtoMapper;
import com.milos.numeric.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MaterialService
{
    private final MaterialRepository materialRepository;
    private final MaterialDtoMapper materialDtoMapper;

    @Value("${app.upload.dir:uploads/materials}") // nastavíš si v application.properties
    private String uploadDir;

    public MaterialService(MaterialRepository materialRepository, MaterialDtoMapper materialDtoMapper) {
        this.materialRepository = materialRepository;
        this.materialDtoMapper = materialDtoMapper;
    }

    @Transactional
    public Material createMaterial(MaterialRequest materialRequest) {
        try {

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(materialRequest.getName());
            Files.write(filePath, materialRequest.getData().getBytes());
            Material material = materialDtoMapper.sourceToDestination(materialRequest);
            material.setPath(filePath.toString());

            return materialRepository.save(material);

        } catch (IOException e) {
            throw new FileStorageException("Nepodarilo sa uložiť súbor: " + materialRequest.getName(), e);
        }
    }

    public Iterable<Material> findAll()
    {
        return this.materialRepository.findAll();
    }

    @Transactional
    public boolean delete(long id)
    {
        Material entity = this.findById(id);

        String filePath = entity.getPath();
        java.io.File fileToDelete = new java.io.File(filePath);
        fileToDelete.delete();

        this.materialRepository.deleteById(id);
        return true;

    }

    public Resource getMaterialResource(String name) {
        Resource resource = new ClassPathResource("static/materials/" + name);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
        return resource;
    }


    public Material findById(long id) {
        return this.materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));
    }


    public Material findByName(String name) {
        return this.materialRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));
    }


}
