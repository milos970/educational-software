package com.milos.numeric.mapper;

import com.milos.numeric.dto.command.MessageCommand;
import com.milos.numeric.dto.request.MessageRequest;
import com.milos.numeric.dto.response.MessageResponse;
import com.milos.numeric.entity.Message;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-09T17:53:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MessageDtoMapperImpl implements MessageDtoMapper {

    @Override
    public MessageCommand toCommand(MessageRequest source) {
        if ( source == null ) {
            return null;
        }

        MessageCommand messageCommand = new MessageCommand();

        messageCommand.setContent( source.content() );
        messageCommand.setSenderUsername( source.senderUsername() );
        messageCommand.setReceiverUsername( source.receiverUsername() );

        return messageCommand;
    }

    @Override
    public MessageResponse toResponse(Message source) {
        if ( source == null ) {
            return null;
        }

        String senderUsername = null;
        String receiverUsername = null;

        senderUsername = source.getSenderUsername();
        receiverUsername = source.getReceiverUsername();

        MessageResponse messageResponse = new MessageResponse( senderUsername, receiverUsername );

        return messageResponse;
    }
}
