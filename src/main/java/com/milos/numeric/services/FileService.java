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


    public Long save(FileDto fileDto)
    {
        String fileName = fileDto.getData().getOriginalFilename();

        Path filePath = Paths.get("src", "main", "resources", "static", "materials", fileName);
        String uri = filePath.toString();

        byte[] fileBytes= new byte[0];
        try {
            fileBytes = fileDto.getData().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            Files.write(filePath, fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        File file = this.fileDtoMapper.sourceToDestination(fileDto);

        file.setPath(uri);
        this.fileRepository.save(file);

        Optional<File> optionalFile = this.fileRepository.findTopByOrderByIdDesc();

        System.out.println("sdafdf" + optionalFile.get().getId());

        return optionalFile.get().getId();
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


    public List<PersonalInfo> convert(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        return null;
    }

    public Object getFile(Long id) {
        return null;
    }




    public void remove(Long id)
    {
        this.fileRepository.deleteById(id);
    }
}
