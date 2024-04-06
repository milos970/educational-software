package com.milos.numeric.mappers;

import com.milos.numeric.dtos.FileDto;
import com.milos.numeric.entities.File;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-07T00:38:34+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class FileDtoMapperImpl implements FileDtoMapper {

    @Override
    public File sourceToDestination(FileDto source) {
        if ( source == null ) {
            return null;
        }

        File file = new File();

        file.setName( source.getName() );
        file.setDescription( source.getDescription() );

        file.setUploadedBy( "gabrisova.lydia" );
        file.setSize( source.getData().getSize() );
        file.setMimeType( source.getData().getContentType() );

        return file;
    }

    @Override
    public FileDto destinationToSource(File destination) {
        if ( destination == null ) {
            return null;
        }

        FileDto fileDto = new FileDto();

        fileDto.setName( destination.getName() );
        fileDto.setDescription( destination.getDescription() );

        return fileDto;
    }
}
