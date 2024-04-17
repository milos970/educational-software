package com.milos.numeric.dtos;

import com.milos.numeric.validators.DateValid;
import com.milos.numeric.validators.EmployeeAvailability;
import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemSettingsDto
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EmployeeAvailability
    private String employee;


    @Nullable
    @Min(0)
    @Max(13)
    private int allowedAbsents;


    @Nullable
    @DateValid
    private String classDate;


}
