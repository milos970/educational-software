package com.milos.numeric.mappers;

import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Message;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T10:10:04+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MessageDtoMapperImpl implements MessageDtoMapper {

    @Override
    public Message sourceToDestination(MessageDto source) {
        if ( source == null ) {
            return null;
        }

        Message message = new Message();

        message.setContent( source.getContent() );
        message.setSenderUsername( source.getSenderUsername() );
        message.setReceiverUsername( source.getReceiverUsername() );

        return message;
    }

    @Override
    public MessageDto destinationToSource(Message destination) {
        if ( destination == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setContent( destination.getContent() );
        messageDto.setReceiverUsername( destination.getReceiverUsername() );
        messageDto.setSenderUsername( destination.getSenderUsername() );

        return messageDto;
    }
}
