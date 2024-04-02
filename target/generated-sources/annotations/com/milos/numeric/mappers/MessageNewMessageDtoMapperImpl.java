package com.milos.numeric.mappers;

import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Message;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-03T01:05:38+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class MessageNewMessageDtoMapperImpl implements MessageNewMessageDtoMapper {

    @Override
    public Message sourceToDestination(MessageDto source) {
        if ( source == null ) {
            return null;
        }

        Message message = new Message();

        message.setContent( source.getContent() );
        message.setSenderId( source.getSenderId() );
        message.setReceiverId( source.getReceiverId() );

        return message;
    }

    @Override
    public MessageDto destinationToSource(Message destination) {
        if ( destination == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setContent( destination.getContent() );
        messageDto.setSenderId( destination.getSenderId() );
        messageDto.setReceiverId( destination.getReceiverId() );

        return messageDto;
    }
}
