package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewAuthorityDto;
import com.milos.numeric.entities.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewAuthorityDTOMapper
{
    PersonalInfo sourceToDestination(NewAuthorityDto source);
    NewAuthorityDto destinationToSource(PersonalInfo destination);
}
