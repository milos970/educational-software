package com.milos.numeric.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name  = "employee_id")
    private Integer id;

    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "person_id")
    private Person person;


}
