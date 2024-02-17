package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.entities.Person;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface PersonNewAuthorityDTOMapper
{
    Person sourceToDestination(NewAuthorityDTO source);
    NewAuthorityDTO destinationToSource(Person destination);
}
