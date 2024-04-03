package com.milos.numeric.services;

import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.Message;
import com.milos.numeric.mappers.MessageDtoMapper;
import com.milos.numeric.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;
    private final PersonalInfoService personalInfoService;

    private MessageDtoMapper messageDtoMapper;



    @Autowired
    public MessageService(MessageRepository messageRepository, PersonalInfoService personalInfoService) {
        this.messageRepository = messageRepository;
        this.personalInfoService = personalInfoService;
    }


    public void saveMessage(MessageDto messageDto, Chat chat)
    {
        Message message = this.messageDtoMapper.sourceToDestination(messageDto);
        message.setChat(chat);
        this.messageRepository.save(message);
    }

    public void deleteAllMessages()
    {
        this.messageRepository.deleteAll();
    }



}
