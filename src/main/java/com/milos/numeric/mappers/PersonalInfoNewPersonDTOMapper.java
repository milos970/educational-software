package com.milos.numeric.mappers;

import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewPersonDTOMapper
{
    PersonalInfo sourceToDestination(PersonalInfoDto source);
    PersonalInfoDto destinationToSource(PersonalInfo destination);
}
