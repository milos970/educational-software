package com.milos.numeric.controllers;

import com.milos.numeric.dtos.NewChatDto;
import com.milos.numeric.dtos.NewMessageDto;
import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.Message;
import com.milos.numeric.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController
{
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping("/person/sendMessage")
    public ResponseEntity saveMessage(@RequestBody @Valid NewMessageDto newMessageDto)
    {
        this.chatService.saveMessage(newMessageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/person/conversation/{id}")
    @ResponseBody
    public List<NewMessageDto> findById(@PathVariable Long id)
    {

        Optional<Chat> optional = this.chatService.findById(id);

        if (optional.isEmpty())
        {

        }

        Chat chat = optional.get();
        List<Message> messages = chat.getMessages();
        List<NewMessageDto> newMessageDtos = new LinkedList<>();

        for(Message item : messages)
        {
            NewMessageDto messageDto = new NewMessageDto();
            messageDto.setContent(item.getContent());
            messageDto.setSender(item.getSender());

            newMessageDtos.add(messageDto);
        }



        return newMessageDtos;
    }


    @DeleteMapping("/admin/conversation")
    public ResponseEntity deleteAll()
    {
        this.chatService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
