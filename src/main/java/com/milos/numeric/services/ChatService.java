package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.ChatRepository;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.security.MyUserDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
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

    private final Validator validator;

    private final PersonalInfoRepository personalInfoRepository;



    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService, Validator validator, PersonalInfoRepository personalInfoRepository)
    {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.validator = validator;
        this.personalInfoRepository = personalInfoRepository;
    }


    public boolean create(String participantA, String participantB)
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
        Set<ConstraintViolation<MessageDto>> violations = validator.validate(messageDto);
        if (!violations.isEmpty())
        {
            return false;
        }

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


        Optional<Chat> optionalChat = this.chatRepository.findById(chatId);

        if (optionalChat.isEmpty())
        {

            return false;
        }


        Chat chat = optionalChat.get();
        this.messageService.saveMessage(messageDto, chat);
        return true;
    }


    public boolean deleteAll()
    {
        this.chatRepository.deleteAll();
        return true;
    }
}
