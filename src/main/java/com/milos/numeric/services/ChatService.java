package com.milos.numeric.services;

import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.Message;
import com.milos.numeric.mappers.MessageNewMessageDtoMapper;
import com.milos.numeric.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService
{
    private final ChatRepository chatRepository;
    private final MessageService messageService;

    private MessageNewMessageDtoMapper messageNewMessageDtoMapper;

    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService) {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
    }

    public boolean saveMessage(NewMessageDto newMessageDto)
    {


        Optional<Chat> optional = this.chatRepository.findById(newMessageDto.getChatId());

        if (optional.isEmpty())
        {
            return false;
        }

        Chat chat = optional.get();

        Message message = messageNewMessageDtoMapper.sourceToDestination(newMessageDto);
        message.setChat(chat);
        message.setId(newMessageDto.getSenderId());

        this.messageService.saveMessage(message);

        return true;
    }


    public Optional<Chat> findById(Long id)
    {


        return this.chatRepository.findById(id);
    }

    public boolean deleteAll()
    {
        this.chatRepository.deleteAll();
        return true;
    }
}
