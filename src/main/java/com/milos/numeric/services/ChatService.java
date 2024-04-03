package com.milos.numeric.services;

import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.ChatRepository;
import com.milos.numeric.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService
{
    private final ChatRepository chatRepository;
    private final MessageService messageService;
    private final PersonalInfoService personalInfoService;



    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService, PersonalInfoService personalInfoService)
    {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.personalInfoService = personalInfoService;
    }

    public Optional<Chat> findByChatId(Long parA, Long parB)
    {
        return this.chatRepository.findById(new ChatId(parA, parB));
    }


    public boolean saveMessage(MessageDto messageDto)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        Optional<PersonalInfo> optionalSender = this.personalInfoService.findByUsername(myUserDetails.getUsername());

        PersonalInfo sender = optionalSender.get();

        if (sender.getId() != messageDto.getSenderId())
        {
            System.out.println("1");
            return false;
        }


        ChatId chatId = new ChatId();

        if (sender.getAuthority().equals("STUDENT"))
        {
            chatId.setParticipantA(0L);
            chatId.setParticipantB(sender.getId());
        } else {
            chatId.setParticipantA(0L);
            chatId.setParticipantB(messageDto.getReceiverId());
        }


        Optional<Chat> optional = this.chatRepository.findById(new ChatId(0L, messageDto.getSenderId()));

        if (optional.isEmpty())
        {

            return false;
        }


        Chat chat = optional.get();

        this.messageService.saveMessage(messageDto, chat);
        return true;
    }


    public boolean deleteAll()
    {
        this.chatRepository.deleteAll();
        return true;
    }
}
