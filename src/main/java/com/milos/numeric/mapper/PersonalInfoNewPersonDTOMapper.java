package com.milos.numeric.mapper;

import com.milos.numeric.dto.PersonalInfoDto;
import com.milos.numeric.entity.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper
public interface PersonalInfoNewPersonDTOMapper
{
    PersonalInfo sourceToDestination(PersonalInfoDto source);
    PersonalInfoDto destinationToSource(PersonalInfo destination);
}
