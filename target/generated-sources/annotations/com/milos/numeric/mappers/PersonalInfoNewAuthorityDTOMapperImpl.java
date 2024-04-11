package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewAuthorityDto;
import com.milos.numeric.entities.PersonalInfo;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-11T22:11:59+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class PersonalInfoNewAuthorityDTOMapperImpl implements PersonalInfoNewAuthorityDTOMapper {

    @Override
    public PersonalInfo sourceToDestination(NewAuthorityDto source) {
        if ( source == null ) {
            return null;
        }

        PersonalInfo personalInfo = new PersonalInfo();

        return personalInfo;
    }

    @Override
    public NewAuthorityDto destinationToSource(PersonalInfo destination) {
        if ( destination == null ) {
            return null;
        }

        NewAuthorityDto newAuthorityDto = new NewAuthorityDto();

        return newAuthorityDto;
    }
}
