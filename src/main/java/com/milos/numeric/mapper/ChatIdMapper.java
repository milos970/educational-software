package com.milos.numeric.mapper;

import com.milos.numeric.dto.command.ChatIdCommand;
import com.milos.numeric.dto.request.ChatIdRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatIdMapper {

    ChatIdCommand toCommand(ChatIdRequest source);
}
