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
    @Column(name  = "person_id")
    private Integer id;

    @Min(0)
    @Max(100)
    private int points;

    @Min(0)
    @Max(13)
    private int absencie;

    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "person_id")
    private Person person;


}
