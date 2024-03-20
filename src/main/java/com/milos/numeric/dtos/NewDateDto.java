package com.milos.numeric.dtos;

import com.milos.numeric.validators.DateValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDateDto
{
    @DateValid
    private String date;
}
