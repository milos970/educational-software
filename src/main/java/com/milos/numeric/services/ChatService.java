package com.milos.numeric.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.milos.numeric.dtos.NewChatDto;
import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.Message;
import com.milos.numeric.mappers.MessageNewMessageDtoMapper;
import com.milos.numeric.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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

    public ResponseEntity saveMessage(NewMessageDto newMessageDto)
    {

        ChatId chatId = new ChatId(newMessageDto.getRecipientId(), newMessageDto.getSenderId());

        Optional<Chat> optional = this.chatRepository.findById(chatId);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Chat chat = optional.get();

        Message message = messageNewMessageDtoMapper.sourceToDestination(newMessageDto);
        message.setChat(chat);
        message.setSenderId(newMessageDto.getSenderId());
        this.messageService.saveMessage(message);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity findById(NewChatDto newChatDto)
    {

        ChatId chatId = new ChatId();
        chatId.setIdA(newChatDto.getIdA());
        chatId.setIdB(newChatDto.getIdB());

        Optional<Chat> optional = this.chatRepository.findById(chatId);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Chat chat = optional.get();
        this.chatRepository.save(chat);


        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteAll()
    {
        this.chatRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
