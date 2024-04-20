package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.MessageDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.Message;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.ChatRepository;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.security.MyUserDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService
{
    private final ChatRepository chatRepository;
    private final MessageService messageService;

    private final Validator validator;
    private final PersonalInfoService personalInfoService;



    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService, Validator validator, PersonalInfoService personalInfoService)
    {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.validator = validator;
        this.personalInfoService = personalInfoService;

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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        ChatId chatId = new ChatId();



        if (this.personalInfoService.findAuthorityByUsername(username).get() == Authority.TEACHER)
        {
            chatId.setParticipantA(username);
            chatId.setParticipantB(messageDto.getReceiverUsername());
        }

        if (this.personalInfoService.findAuthorityByUsername(username).get() == Authority.STUDENT)
        {


            Optional<String> optionalUsername = this.personalInfoService.findUsernameByAuthorityTeacher();
            chatId.setParticipantA(optionalUsername.get());
            chatId.setParticipantB(username);

        }


        Optional<Chat> optionalChat = this.chatRepository.findById(chatId);

        if (optionalChat.isEmpty())
        {
            return false;
        }

        messageDto.setSenderUsername(username);

        Chat chat = optionalChat.get();

        this.messageService.saveMessage(messageDto, chat);
        return true;
    }


    public List<MessageDto> getAllMessages(Chat chat)
    {
        List<MessageDto> messageDtos = new LinkedList<>();

        List<Message> messages = chat.getMessages();

        for (int i = messages.size() - 1; i >= 0; --i) {
            MessageDto messageDto = new MessageDto();
            messageDto.setContent(messages.get(i).getContent());

            messageDto.setReceiverUsername(messages.get(i).getReceiverUsername());
            messageDto.setSenderUsername(messages.get(i).getSenderUsername());
            messageDtos.add(messageDto);

        }

        return messageDtos;
    }


    public boolean deleteAll()
    {
        this.chatRepository.deleteAll();
        return true;
    }
}
