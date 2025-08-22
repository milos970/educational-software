package com.milos.numeric.service;

import com.milos.numeric.dto.MessageDto;
import com.milos.numeric.entity.Chat;
import com.milos.numeric.entity.Message;
import com.milos.numeric.mapper.MessageDtoMapper;
import com.milos.numeric.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;
    private final MessageDtoMapper messageDtoMapper;


    public MessageService(MessageRepository messageRepository, MessageDtoMapper messageDtoMapper) {
        this.messageRepository = messageRepository;
        this.messageDtoMapper = messageDtoMapper;
    }


    public void createMessage(MessageDto messageDto, Chat chat)
    {
        Message message = this.messageDtoMapper.sourceToDestination(messageDto);
        message.setSenderUsername(messageDto.getSenderUsername());
        message.setChat(chat);
        this.messageRepository.save(message);
    }

    public void deleteAllMessages()
    {
        this.messageRepository.deleteAll();
    }



}
