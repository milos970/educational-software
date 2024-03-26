package com.milos.numeric.entities;

import com.milos.numeric.validators.DateValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettings
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "allowed_absents")
    @Min(0)
    @Max(13)
    private int allowedAbsents;

    @Column(name = "number_of_students")
    @Min(0)
    @Max(500)
    private int numberOfStudents;

    @Size(min = 1, max = 13, message = "{validation.name.size.too_long}")
    private String teacher;

    @DateValid
    private String date;



}
