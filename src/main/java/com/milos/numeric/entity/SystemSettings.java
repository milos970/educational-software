package com.milos.numeric.entity;

import com.milos.numeric.validator.DateValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "system_settings")
public class SystemSettings
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allowed_absences")
    @Min(value = 0, message = "Value is less than 0!")
    @Max(value = 13, message = "Value is more than 13!")
    private int allowedAbsences;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    @Column(name = "class_date")
    private LocalDate classDate;

    @Column(name = "uploaded_file")
    private boolean successful;

}
