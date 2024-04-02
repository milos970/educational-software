package com.milos.numeric.mappers;

import com.milos.numeric.dtos.FileDto;
import com.milos.numeric.entities.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileFileDtoMapper
{
    FileFileDtoMapper INSTANCE = Mappers.getMapper(FileFileDtoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "uploadedBy", constant = "gabrisova.lydia")
    @Mapping(target = "size", expression = "java(source.getData().getSize())")
    @Mapping(target = "mimeType", expression = "java(source.getData().getContentType())")
    File sourceToDestination(FileDto source);
    FileDto destinationToSource(File destination);


}
