package com.milos.numeric.entities;

import com.milos.numeric.validators.DateValid;
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
@Table(name = "system_settings")
public class SystemSettings
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "allowed_absents")
    @Min(value = 0, message = "Value is less than 0!")
    @Max(value = 13, message = "Value is more than 13!")
    private int allowedAbsents;


    @OneToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @DateValid
    @Column(name = "class_date")
    private String classDate;

    @Column(name = "number_of_days")
    @Min(value = 0, message = "Value is less than 0!")
    @Max(value = 365, message = "Value is more than 365!")
    private int numberOfDays;


    @Column(name = "uploaded_file")
    private boolean successfull;




}
