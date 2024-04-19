package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private Long id;

    @Min(value = 0, message = "Value is less than 0!")
    @Max(value = 100, message = "Value is more than 100!")
    private int points;

    @Min(value = 0, message = "Value is less than 0!")
    @Max(value = 13, message = "Value is more than 13!")
    private int absents;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "person_id")
    private PersonalInfo personalInfo;

}
