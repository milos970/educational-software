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

    public Optional<Chat> findByOneParticipant(Long idA, Long idB)
    {
        return this.chatRepository.findByParticipants(idA, idB);
    }


    public boolean saveMessage(NewMessageDto newMessageDto)
    {


        Optional<Chat> optional = this.chatRepository.findByParticipants(newMessageDto.getRecipientId(), newMessageDto.getSenderId());

        if (optional.isEmpty())
        {

            return false;
        }

        Chat chat = optional.get();

        Message message = new Message();
        message.setChat(chat);
        message.setContent(newMessageDto.getContent());
        message.setSeen(false);
        message.setSender(newMessageDto.getSender());

        this.messageService.saveMessage(message);

        System.out.println(message.getContent());

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
