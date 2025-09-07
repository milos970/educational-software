package com.milos.numeric.mapper;

import com.milos.numeric.dto.MaterialDto;
import com.milos.numeric.entity.Material;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T17:53:33+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MaterialDtoMapperImpl implements MaterialDtoMapper {

    @Override
    public Material sourceToDestination(MaterialDto source) {
        if ( source == null ) {
            return null;
        }

        String name = null;
        String description = null;

        name = source.getName();
        description = source.getDescription();

        long id = 0L;
        String path = null;
        String uploadedBy = source.getUploadedBy();
        long size = source.getData().getSize();
        String mimeType = source.getData().getContentType();

        Material material = new Material( id, path, name, mimeType, description, uploadedBy, size );

        return material;
    }

    @Override
    public MaterialDto destinationToSource(Material destination) {
        if ( destination == null ) {
            return null;
        }

        MaterialDto materialDto = new MaterialDto();

        materialDto.setName( destination.getName() );
        materialDto.setDescription( destination.getDescription() );
        materialDto.setUploadedBy( destination.getUploadedBy() );

        return materialDto;
    }
}
