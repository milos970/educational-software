package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.entities.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonNewPersonDTOMapper
{
    Person sourceToDestination(NewPersonDTO source);
    NewPersonDTO destinationToSource(Person destination);
}
