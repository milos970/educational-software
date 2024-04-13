package com.milos.numeric.services;

import com.milos.numeric.dtos.MaterialDto;
import com.milos.numeric.entities.Material;
import com.milos.numeric.mappers.FileDtoMapper;
import com.milos.numeric.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService
{
    private final FileDtoMapper fileDtoMapper;
    private final MaterialRepository materialRepository;


    @Autowired
    public MaterialService(FileDtoMapper fileDtoMapper, MaterialRepository materialRepository)
    {
        this.fileDtoMapper = fileDtoMapper;
        this.materialRepository = materialRepository;
    }


    public boolean existsByName(String name)
    {
        return this.materialRepository.countByName(name) > 0;
    }



    public Long save(MaterialDto materialDto)
    {

        String fileName = materialDto.getData().getOriginalFilename();

        Path filePath = Paths.get("src", "main", "resources", "static", "materials", fileName);
        String uri = filePath.toString();

        byte[] fileBytes= new byte[0];
        try
        {
            fileBytes = materialDto.getData().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try
        {
            Files.write(filePath, fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Material material = this.fileDtoMapper.sourceToDestination(materialDto);

        material.setPath(uri);
        Material entity = this.materialRepository.save(material);

        entity.setName(entity.getName() + ":" + entity.getId());
        this.materialRepository.save(entity);
        return entity.getId();
    }

    public List<Material> findAll()
    {
        return this.materialRepository.findAll();
    }


    public Optional<Material> findById(Long id)
    {
        return this.materialRepository.findById(id);
    }


    public boolean delete(Long id)
    {
        Optional<Material> optional = this.materialRepository.findById(id);

        if (optional.isEmpty())
        {
            return false;
        }

        String filePath = optional.get().getPath();
        java.io.File fileToDelete = new java.io.File(filePath);
        fileToDelete.delete();

        this.materialRepository.deleteById(id);
        return true;

    }
}
