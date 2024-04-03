package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.ChatRepository;
import com.milos.numeric.repositories.PersonalInfoRepository;
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

    private final PersonalInfoRepository personalInfoRepository;



    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService, PersonalInfoRepository personalInfoRepository)
    {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.personalInfoRepository = personalInfoRepository;
    }


    public boolean save(String participantA, String participantB)
    {
        ChatId chatId = new ChatId(participantA, participantB);
        Chat chat = new Chat();
        chat.setChatId(chatId);
        this.chatRepository.save(chat);
        return true;
    }

    public Optional<Chat> findByChatId(String usernameA, String usernameB)
    {
        return this.chatRepository.findById(new ChatId(usernameA, usernameB));
    }


    public boolean saveMessage(MessageDto messageDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<PersonalInfo> optional = this.personalInfoRepository.findByUsername(authentication.getName());

        if (optional.isEmpty()) {
            return false;
        }

        ChatId chatId = new ChatId();

        PersonalInfo personalInfo = optional.get();

        if (personalInfo.getAuthority() == Authority.TEACHER)
        {
            chatId.setParticipantA(authentication.getName());
            chatId.setParticipantB(messageDto.getReceiverUsername());
        }

        if (personalInfo.getAuthority() == Authority.STUDENT)
        {
            chatId.setParticipantA(authentication.getName());
            chatId.setParticipantB("gabrisova");
        }








        Optional<Chat> optional = this.chatRepository.findById(chatId);

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
