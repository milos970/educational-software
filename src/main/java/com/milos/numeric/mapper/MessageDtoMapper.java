package com.milos.numeric.mapper;

import com.milos.numeric.dto.MessageDto;
import com.milos.numeric.entity.Message;
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
