package com.milos.numeric.service;

import com.milos.numeric.Role;
import com.milos.numeric.dto.command.ChatIdCommand;
import com.milos.numeric.dto.command.MessageCommand;
import com.milos.numeric.dto.request.ChatIdRequest;
import com.milos.numeric.entity.Chat;
import com.milos.numeric.entity.ChatId;
import com.milos.numeric.entity.Message;
import com.milos.numeric.repository.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final PersonalInfoService personalInfoService;



    @Autowired
    public ChatService(ChatRepository chatRepository, MessageService messageService, Validator validator, PersonalInfoService personalInfoService)
    {
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.validator = validator;
        this.personalInfoService = personalInfoService;

    }



    public void createChat(String participantA, String participantB)
    {
        ChatId chatId = new ChatId(participantA, participantB);
        Chat chat = new Chat();
        chat.setId(chatId);
        this.chatRepository.save(chat);
    }


    public Chat findByChatId(ChatId chatId) {
        return this.chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
    }

    public void saveMessage(MessageCommand command)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ROLE_TEACHER.name()));

        String username = authentication.getName();
        ChatId chatId = new ChatId();

        if (isTeacher)
        {
            chatId.setParticipantA(username);
            chatId.setParticipantB(command.getReceiverUsername());
        } else {
            String teacherUsername = personalInfoService.findUsernameByAuthority(Role.ROLE_TEACHER);
            chatId.setParticipantA(teacherUsername);
            chatId.setParticipantB(username);
        }

        Chat chat = this.findByChatId(chatId);
        command.setChat(chat);
        this.messageService.createMessage(command);
    }


    public Iterable<Message> allMessages(ChatIdCommand command) {
        ChatId chatId = new ChatId();
        chatId.setParticipantA(command.getSenderUsername());
        chatId.setParticipantB(command.getReceiverUsername());
        Chat chat = this.findByChatId(chatId);
        List<Message> messages = chat.getMessages();
        Collections.reverse(messages);
        return messages;
    }


    public void deleteAll()
    {
        this.chatRepository.deleteAll();
    }
}
