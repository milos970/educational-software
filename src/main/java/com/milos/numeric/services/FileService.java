package com.milos.numeric.services;

import com.milos.numeric.dtos.FileDto;
import com.milos.numeric.entities.File;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.mappers.FileDtoMapper;
import com.milos.numeric.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileService
{
    private final FileRepository fileRepository;

    private final FileDtoMapper fileDtoMapper;


    @Autowired
    public FileService(FileRepository fileRepository, FileDtoMapper fileDtoMapper) {
        this.fileRepository = fileRepository;
        this.fileDtoMapper = fileDtoMapper;
    }


    public boolean existsByName(String name)
    {
        return this.fileRepository.existsByName(name);
    }

    public Optional<File> findByName(String name)
    {
        return this.fileRepository.findByName(name);
    }


    public Long save(FileDto fileDto)
    {

        String fileName = fileDto.getData().getOriginalFilename();

        Path filePath = Paths.get("src", "main", "resources", "static", "materials", fileName);
        String uri = filePath.toString();

        byte[] fileBytes= new byte[0];
        try
        {
            fileBytes = fileDto.getData().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try
        {
            Files.write(filePath, fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        File file = this.fileDtoMapper.sourceToDestination(fileDto);

        file.setPath(uri);
        File entity = this.fileRepository.save(file);

        entity.setName(entity.getName() + ":" + entity.getId());
        return entity.getId();
    }

    public List<File> findAll()
    {
        return this.fileRepository.findAll();
    }


    public Optional<File> findById(Long id)
    {
        return this.fileRepository.findById(id);
    }


    public Object store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        return null;
    }





    public boolean delete(String name)
    {
        Optional<File> optional = this.fileRepository.findByName(name);

        if (optional.isEmpty())
        {
            return false;
        }

        String filePath = optional.get().getPath();
        java.io.File fileToDelete = new java.io.File(filePath);
        fileToDelete.delete();

        this.fileRepository.deleteById(optional.get().getId());
        return true;

    }
}
