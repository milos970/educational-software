package com.milos.numeric.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MaterialDto
{

    @Column(name = "file_name")
    @Size(min = 1, max = 15, message = "{validation.name.size.too_long}")
    private String name;

    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String description;

    private MultipartFile data;

}
