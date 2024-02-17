package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.entities.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonNewPasswordDTOMapper
{
    Person sourceToDestination(NewPasswordDTO source);
    NewPasswordDTO destinationToSource(Person destination);
}
