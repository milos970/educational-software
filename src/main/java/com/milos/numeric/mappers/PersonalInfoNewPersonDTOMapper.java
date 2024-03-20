package com.milos.numeric.mappers;

import com.milos.numeric.dtos.AddPersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewPersonDTOMapper
{
    PersonalInfo sourceToDestination(AddPersonalInfoDto source);
    AddPersonalInfoDto destinationToSource(PersonalInfo destination);
}
