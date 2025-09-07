package com.milos.numeric.mapper;

import com.milos.numeric.dto.command.MessageCommand;
import com.milos.numeric.dto.request.MessageRequest;
import com.milos.numeric.dto.response.MessageResponse;
import com.milos.numeric.entity.Message;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MessageDtoMapper
{

    MessageCommand toCommand(MessageRequest source);

    MessageResponse toResponse(Message source);
}
