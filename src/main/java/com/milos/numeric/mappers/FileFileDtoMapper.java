package com.milos.numeric.mappers;

import com.milos.numeric.dtos.FileDto;
import com.milos.numeric.entities.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileFileDtoMapper
{

    File sourceToDestination(FileDto source);
    FileDto destinationToSource(File destination);


}
