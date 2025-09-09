package com.milos.numeric.service;

import com.milos.numeric.dto.command.MessageCommand;
import com.milos.numeric.entity.Message;
import com.milos.numeric.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Transactional
    public void createMessage(MessageCommand command)
    {
        Message entity = new Message();
        entity.setContent(command.getContent());
        entity.setSenderUsername(command.getSenderUsername());
        entity.setReceiverUsername(command.getReceiverUsername());
        entity.setChat(command.getChat());
        this.messageRepository.save(entity);
    }

    public void deleteAllMessages()
    {
        this.messageRepository.deleteAll();
    }
}
