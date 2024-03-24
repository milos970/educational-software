package com.milos.numeric.mappers;

import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Message;
import org.mapstruct.Mapper;


@Mapper
public interface MessageNewMessageDtoMapper
{
    Message sourceToDestination(MessageDto source);
    MessageDto destinationToSource(Message destination);
}
