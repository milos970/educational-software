package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "nam") //zmenit!!!!!
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 50, message = "{validation.name.size.too_long}")
    private String name;

    @NotBlank
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 50, message = "{validation.name.size.too_long}")
    private String surname;

    @NotBlank
    @Column(unique = true)
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 50, message = "{validation.name.size.too_long}")
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,}$",
            message = "Password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit!")
    private String password;


    @NotBlank
    private String authority;

    private boolean enabled;




}
