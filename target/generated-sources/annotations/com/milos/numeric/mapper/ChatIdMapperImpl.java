package com.milos.numeric.mapper;

import com.milos.numeric.dto.command.ChatIdCommand;
import com.milos.numeric.dto.request.ChatIdRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-09T17:53:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ChatIdMapperImpl implements ChatIdMapper {

    @Override
    public ChatIdCommand toCommand(ChatIdRequest source) {
        if ( source == null ) {
            return null;
        }

        ChatIdCommand chatIdCommand = new ChatIdCommand();

        chatIdCommand.setSenderUsername( source.senderUsername() );
        chatIdCommand.setReceiverUsername( source.receiverUsername() );

        return chatIdCommand;
    }
}
