package com.milos.numeric.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MaterialDto
{

    @NotBlank
    @Size(min = 1, max = 15, message = "{material.name}")
    private String name;

    @Size(max = 100, message = "{material.description}")
    private String description;

    private MultipartFile data;

}
