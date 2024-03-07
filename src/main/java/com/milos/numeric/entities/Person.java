package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name  = "person_id")
    private Integer id;

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

    @NotBlank
    @Column(unique = true, name = "personal_number")
    @Size(max = 50, message = "{validation.name.size.too_long}")
    private String personalNumber;

    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,}$",
            message = "Password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit!")
    private String password;

    @NotBlank
    private String authority;

    private boolean enabled;


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats;



}
