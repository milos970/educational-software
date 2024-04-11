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
    @Min(0)
    @Max(13)
    private int allowedAbsents;

    @Column(name = "number_of_students")
    @Min(0)
    @Max(100)
    private int numberOfStudents;

    @OneToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "employee_id")
    private Employee employee;

    @DateValid
    @Column(name = "class_date")
    private String classDate;

    @Column(name = "number_of_days")
    private int numberOfDays;

    @Column(name = "school_week")
    @Min(1)
    @Max(13)
    private int schoolWeek;


}
