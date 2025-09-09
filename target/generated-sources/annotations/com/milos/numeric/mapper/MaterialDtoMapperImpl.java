package com.milos.numeric.mapper;

import com.milos.numeric.dto.request.MaterialRequest;
import com.milos.numeric.entity.Material;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-09T17:53:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MaterialDtoMapperImpl implements MaterialDtoMapper {

    @Override
    public Material sourceToDestination(MaterialRequest source) {
        if ( source == null ) {
            return null;
        }

        Material material = new Material();

        material.setName( source.getName() );
        material.setDescription( source.getDescription() );

        material.setUploadedBy( source.getUploadedBy() );
        material.setSize( source.getData().getSize() );
        material.setMimeType( source.getData().getContentType() );

        return material;
    }

    @Override
    public MaterialRequest destinationToSource(Material destination) {
        if ( destination == null ) {
            return null;
        }

        MaterialRequest materialRequest = new MaterialRequest();

        materialRequest.setName( destination.getName() );
        materialRequest.setDescription( destination.getDescription() );
        materialRequest.setUploadedBy( destination.getUploadedBy() );

        return materialRequest;
    }
}
