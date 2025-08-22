package com.milos.numeric.mapper;

import com.milos.numeric.dto.NewPasswordDto;
import com.milos.numeric.entity.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewPasswordDTOMapper
{
    PersonalInfo sourceToDestination(NewPasswordDto source);
    NewPasswordDto destinationToSource(PersonalInfo destination);
}
