package com.milos.numeric.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SystemSettingsDto
{
    private int prezencky; //translate
    private MultipartFile file;

}
