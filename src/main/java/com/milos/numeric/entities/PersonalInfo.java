package com.milos.numeric.entities;

import com.milos.numeric.Authority;
import com.milos.numeric.Gender;
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
@Table(name = "personal_info")
public class PersonalInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 1, message = "PersonalInfo name is less than 1 character long!")
    @Size(max = 50, message = "PersonalInfo name is more than 50 characters long!")
    private String name;

    @NotBlank
    @Size(min = 1, message = "PersonalInfo surname is less than 1 character long!")
    @Size(max = 50, message = "PersonalInfo surname is more than 50 characters long!")
    private String surname;

    @NotBlank
    @Column(unique = true)
    @Size(min = 1, message = "PersonalInfo username is less than 1 character long!")
    @Size(max = 50, message = "PersonalInfo username is more than 50 characters long!")
    private String username;

    @Column(unique = true, name = "personal_number")
    @Size(min = 5, max = 6, message = "PersonalInfo pin length is not between 5 and 6 characters!")
    private String personalNumber;

    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\\\\@%.#&\\-()\\[\\]\\-_{}\\]:;'\",?/*~$^+=<>]).{8,64}$",
            message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Enumerated(EnumType.STRING)
    private Authority authority;

    private boolean enabled;


}
