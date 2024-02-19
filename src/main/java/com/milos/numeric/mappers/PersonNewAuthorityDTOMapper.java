package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.entities.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonNewAuthorityDTOMapper
{
    Person sourceToDestination(NewAuthorityDTO source);
    NewAuthorityDTO destinationToSource(Person destination);
}
