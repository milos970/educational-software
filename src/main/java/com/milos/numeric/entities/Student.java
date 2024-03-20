package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private Long id;

    @Min(0)
    @Max(100)
    private int points;

    @Min(0)
    @Max(13)
    private int absents;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "person_id")
    private PersonalInfo personalInfo;

}
