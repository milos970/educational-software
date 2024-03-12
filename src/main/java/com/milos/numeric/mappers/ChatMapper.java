package com.milos.numeric.mappers;

import com.milos.numeric.dtos.NewChatDto;
import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.Message;
import org.mapstruct.Mapper;

@Mapper
public interface ChatMapper {
    ChatId sourceToDestination(NewChatDto source);
    NewChatDto destinationToSource(ChatId destination);
}
