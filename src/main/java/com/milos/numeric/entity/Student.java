package com.milos.numeric.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
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
    private int absences;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "personal_info_id")
    private PersonalInfo personalInfo;

}
