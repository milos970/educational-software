package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.entities.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewPasswordDTOMapper
{
    PersonalInfo sourceToDestination(NewPasswordDto source);
    NewPasswordDto destinationToSource(PersonalInfo destination);
}
