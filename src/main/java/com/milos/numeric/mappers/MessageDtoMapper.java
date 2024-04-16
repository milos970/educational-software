package com.milos.numeric.mappers;

import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MessageDtoMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chat", ignore = true)
    Message sourceToDestination(MessageDto source);
    MessageDto destinationToSource(Message destination);
}
