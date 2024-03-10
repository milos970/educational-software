package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewPersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewPersonDTOMapper
{
    PersonalInfo sourceToDestination(NewPersonalInfoDto source);
    NewPersonalInfoDto destinationToSource(PersonalInfo destination);
}
