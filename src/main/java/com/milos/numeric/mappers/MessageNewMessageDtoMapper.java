package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.entities.Message;
import org.mapstruct.Mapper;

@Mapper
public interface MessageNewMessageDtoMapper
{
    Message sourceToDestination(NewMessageDto source);
    NewMessageDto destinationToSource(Message destination);
}
