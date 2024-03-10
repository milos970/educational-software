package com.milos.numeric.services;

import com.milos.numeric.entities.Message;
import com.milos.numeric.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;
    private final PersonalInfoService personalInfoService;

    @Autowired
    public MessageService(MessageRepository messageRepository, PersonalInfoService personalInfoService) {
        this.messageRepository = messageRepository;
        this.personalInfoService = personalInfoService;
    }


    public void saveMessage(Message message)
    {
        this.messageRepository.save(message);
    }

    public void deleteAllMessages()
    {
        this.messageRepository.deleteAll();
    }



}
