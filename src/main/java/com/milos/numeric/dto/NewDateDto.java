package com.milos.numeric.dto;

import com.milos.numeric.validator.DateValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDateDto
{
    @DateValid
    private String date;
}
