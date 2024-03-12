package com.milos.numeric.services;

import com.milos.numeric.dtos.FileDto;
import com.milos.numeric.dtos.NewChatDto;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.File;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.mappers.FileFileDtoMapper;
import com.milos.numeric.repositories.FileRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private  FileFileDtoMapper fileFileDtoMapper;


    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public boolean save(FileDto fileDto)
    {

        String uri = "/src/main/resources/static/materials/numeric/" + fileDto.getName() + ".pdf";
        try {
            FileUtils.writeByteArrayToFile(new java.io.File(uri), fileDto.getData().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File();

        file.setFileName(fileDto.getName());
        file.setPath(uri);
        file.setDescription(fileDto.getDescription());
        file.setSize(fileDto.getData().getSize());
        file.setMimeType("application/pdf");
        file.setUploadedBy("Lýdia");
        this.fileRepository.save(file);


        return true;
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
