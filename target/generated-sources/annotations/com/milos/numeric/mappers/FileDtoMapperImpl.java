package com.milos.numeric.mappers;

import com.milos.numeric.dtos.MaterialDto;
import com.milos.numeric.entities.Material;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-18T01:08:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class FileDtoMapperImpl implements FileDtoMapper {

    @Override
    public Material sourceToDestination(MaterialDto source) {
        if ( source == null ) {
            return null;
        }

        Material material = new Material();

        material.setName( source.getName() );
        material.setDescription( source.getDescription() );

        material.setUploadedBy( "gabrisova.lydia" );
        material.setSize( source.getData().getSize() );
        material.setMimeType( source.getData().getContentType() );

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

        return materialDto;
    }
}
