package com.milos.numeric.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CsvFileDto
{
    @Size(max = 5 * 1024 * 1024, message = "Veľkosť súboru musí byť najviac 5MB!")
    private MultipartFile file;
}

