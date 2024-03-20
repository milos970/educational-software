package com.milos.numeric.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SystemSettingsDto
{

    @Size(min = 1, max = 13, message = "{validation.name.size.too_long}")
    private int allowedAbsents;

    private MultipartFile multipartFile;

    @Size(min = 1, max = 50, message = "{validation.name.size.too_long}")
    private String teacher;


    private String date;
}
