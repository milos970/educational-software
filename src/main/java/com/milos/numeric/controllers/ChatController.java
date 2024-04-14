package com.milos.numeric.controllers;

import com.milos.numeric.dtos.MessageDto;
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
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping("/person/message")
    public ResponseEntity saveMessage(@RequestBody @Valid MessageDto messageDto)
    {
        if (this.chatService.saveMessage(messageDto))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }


    @GetMapping("/person/conversation")
    @ResponseBody
    public List<MessageDto> findById(@RequestParam("param") String param) {

        Optional<Chat> optional = this.chatService.findByChatId("gab", param);
        List<MessageDto> messageDtos = new LinkedList<>();


        Chat chat = optional.get();
        List<Message> messages = chat.getMessages();


        for (int i = messages.size() - 1; i >= 0; --i) {
            MessageDto messageDto = new MessageDto();
            messageDto.setContent(messages.get(i).getContent());
            messageDto.setSenderUsername(messages.get(i).getSenderUsername());
            messageDto.setReceiverUsername(messages.get(i).getReceiverUsername());
            messageDtos.add(messageDto);

        }


        return messageDtos;
    }


    @DeleteMapping("/admin/conversation")
    public ResponseEntity deleteAll()
    {
        if (this.chatService.deleteAll())
        {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
