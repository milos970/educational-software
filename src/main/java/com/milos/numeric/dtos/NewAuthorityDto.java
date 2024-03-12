package com.milos.numeric.dtos;

import com.milos.numeric.Authority;
import com.milos.numeric.validators.ValueOfEnumAuthority;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAuthorityDto
{
    @ValueOfEnumAuthority(anyOf = {Authority.EMPLOYEE, Authority.STUDENT, Authority.TEACHER})
    @NotNull
    private String newAuthority;
}
