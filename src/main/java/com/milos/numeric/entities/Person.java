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
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column(unique = true, name = "personal_number")
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

    @Min(0)
    @Max(100)
    private int points;

    @Max(13)
    private int absencie;


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats;


    /*@OneToOne()
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private VerificationToken verification;*/


}
