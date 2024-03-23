package com.milos.numeric.services;

import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.Message;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.mappers.MessageNewMessageDtoMapper;
import com.milos.numeric.repositories.ChatRepository;
import com.milos.numeric.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService
{
    private final ChatRepository chatRepository;
    private final MessageService messageService;

    private final PersonalInfoService personalInfoService;

    private MessageNewMessageDtoMapper messageNewMessageDtoMapper;

    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService, PersonalInfoService personalInfoService) {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.personalInfoService = personalInfoService;
    }

    public Optional<Chat> findByOneParticipant(Long idA, Long idB)
    {
        return this.chatRepository.findByParticipants(idA, idB);
    }


    public boolean saveMessage(NewMessageDto newMessageDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(myUserDetails.getUsername());

        Optional<Chat> optional = null;
        if (myUserDetails.getAuthority().equals("STUDENT"))
        {
            optional = this.chatRepository.findByParticipants(0L,optionalPersonalInfo.get().getId());
        }


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
