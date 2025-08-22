package com.milos.numeric.mapper;

import com.milos.numeric.dto.MaterialDto;
import com.milos.numeric.entity.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaterialDtoMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "uploadedBy", expression = "java(source.getUploadedBy())")
    @Mapping(target = "size", expression = "java(source.getData().getSize())")
    @Mapping(target = "mimeType", expression = "java(source.getData().getContentType())")
    Material sourceToDestination(MaterialDto source);
    MaterialDto destinationToSource(Material destination);

}
