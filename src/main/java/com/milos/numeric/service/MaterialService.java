package com.milos.numeric.service;

import com.milos.numeric.dto.MaterialDto;
import com.milos.numeric.entity.Material;
import com.milos.numeric.exception.FileStorageException;
import com.milos.numeric.mapper.MaterialDtoMapper;
import com.milos.numeric.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public Material createMaterial(MaterialDto materialDto) {
        try {

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(materialDto.getName());
            Files.write(filePath, materialDto.getData().getBytes());
            Material material = materialDtoMapper.sourceToDestination(materialDto);
            material.setPath(filePath.toString());

            return materialRepository.save(material);

        } catch (IOException e) {
            throw new FileStorageException("Nepodarilo sa uložiť súbor: " + materialDto.getName(), e);
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


    public Material findById(long id) {
        return this.materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));
    }


}
