package com.milos.numeric.controllers;

import com.milos.numeric.dtos.NewChatDto;
import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChatController
{
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping("/person/sendMessage")
    public ResponseEntity saveMessage(@Valid NewMessageDto newMessageDto)
    {
        return this.chatService.saveMessage(newMessageDto);
    }


    @GetMapping("/person/conversation")
    public ResponseEntity findById(NewChatDto newChatDto)
    {
        return this.chatService.findById(newChatDto);
    }


    @DeleteMapping("/admin/conversation")
    public ResponseEntity deleteAll() {
        return this.chatService.deleteAll();
    }





}
