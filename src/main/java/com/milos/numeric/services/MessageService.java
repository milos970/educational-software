package com.milos.numeric.services;

import com.milos.numeric.entities.Message;
import com.milos.numeric.entities.Person;
import com.milos.numeric.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;
    private final PersonService personService;

    @Autowired
    public MessageService(MessageRepository messageRepository, PersonService personService) {
        this.messageRepository = messageRepository;
        this.personService = personService;
    }


    public void createMessage(Message message, Person person)
    {

    }

    public void deleteAllMessages()
    {
        this.messageRepository.deleteAll();
    }



}
