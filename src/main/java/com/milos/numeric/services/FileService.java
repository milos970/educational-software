package com.milos.numeric.services;

import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService
{
    private final FileRepository fileRepository;


    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;

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

    public List<Object> getAllFiles() {
        return null;
    }


    public void remove(Long id)
    {
        this.fileRepository.deleteById(id);
    }
}
