package com.milos.numeric.services;

import com.milos.numeric.dtos.MaterialDto;
import com.milos.numeric.entities.Material;
import com.milos.numeric.mappers.MaterialDtoMapper;
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
    private final MaterialDtoMapper materialDtoMapper;
    private final MaterialRepository materialRepository;


    @Autowired
    public MaterialService(MaterialDtoMapper materialDtoMapper, MaterialRepository materialRepository)
    {
        this.materialDtoMapper = materialDtoMapper;
        this.materialRepository = materialRepository;
    }



    public Material save(MaterialDto materialDto)
    {

        Path filePath = Paths.get("src", "main", "resources", "static", "materials", materialDto.getName());


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


        Material material = this.materialDtoMapper.sourceToDestination(materialDto);

        material.setPath(uri);


        try
        {
            Material savedMaterial = this.materialRepository.save(material);
            return savedMaterial;
        } catch (Exception e)
        {
            return null;
        }

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
