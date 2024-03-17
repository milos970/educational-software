package com.milos.numeric.entities;

import jakarta.persistence.*;
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
    private int allowedAbsents;

    @Column(name = "number_of_students")
    private int numberOfStudents;

    @Column(name = "uploaded_students")
    private boolean uploadedStudents;

    private Date date;

    private int week;




}
