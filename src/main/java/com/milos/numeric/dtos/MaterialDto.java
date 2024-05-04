package com.milos.numeric.dtos;

import com.milos.numeric.validators.MaterialSizeValid;
import com.milos.numeric.validators.MaterialTypeValid;
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
    @Size(min = 1, max = 15, message = "Názov materiálu musí byť v rozmedzí 1 až 15 znakov!")
    private String name;

    @Size(max = 100, message = "Počet znakov obsahu musí byť najviac 100 znakov!")
    private String description;

    @MaterialTypeValid
    @MaterialSizeValid
    private MultipartFile data;

    @NotBlank
    private String uploadedBy;

}

