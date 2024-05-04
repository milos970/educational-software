package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 5, max = 100, message = "{material.path}")
    @Column(unique = true)
    private String path;

    @NotBlank
    @Column(unique = true)
    @Size(min = 1, max = 15, message = "{material.name}")
    private String name;

    @NotBlank
    @Column(name = "mime_type")
    private String mimeType;

    @Size(max = 100, message = "{material.description}")
    private String description;

    @NotBlank
    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Max(value = 5 * 1024 * 1024, message = "{material.size}")
    private Long size;


}
