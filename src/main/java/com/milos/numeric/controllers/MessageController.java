package com.milos.numeric.controllers;

import com.milos.numeric.entities.Message;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController
{

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/add/message")
    public void createMessage(@AuthenticationPrincipal MyUserDetails myUserDetails, Message message)
    {
        this.messageService.createMessage(message, myUserDetails.getPerson()); // prerobit
    }
}
