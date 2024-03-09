package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, name = "personal_number")
    @Size(min = 6, max = 6, message = "{validation.name.size.too_long}")
    private String personalNumber;

    @Min(0)
    @Max(100)
    private int points;

    @Min(0)
    @Max(13)
    private int absencie;

    @OneToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "person_id")
    private Person person;

}
