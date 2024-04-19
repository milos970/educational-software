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
    private Long id;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "person_id")
    private PersonalInfo personalInfo;


}
