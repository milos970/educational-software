package com.milos.numeric.entity;


import com.milos.numeric.Role;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personal_info")
public class PersonalInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50, message = "PersonalInfo name must be at most 50 characters long!")
    private String name;

    @NotBlank
    @Column(unique = true)
    @Size(max = 50, message = "PersonalInfo surname must be at most 50 characters long!")
    private String surname;

    @NotBlank
    @Column(unique = true)
    @Size(max = 50, message = "PersonalInfo username must be at most 50 characters long!")
    private String username;

    @Column(unique = true, name = "personal_number")
    @Pattern(regexp = "\\d{5,6}", message = "PersonalInfo pin must be 5 or 6 digits!")
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
    private Role role;

    private boolean enabled;


}
