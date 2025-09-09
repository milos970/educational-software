package com.milos.numeric.controller;

import com.milos.numeric.dto.command.ChatIdCommand;
import com.milos.numeric.dto.command.MessageCommand;
import com.milos.numeric.dto.request.ChatIdRequest;
import com.milos.numeric.dto.request.MessageRequest;
import com.milos.numeric.dto.response.MessageResponse;
import com.milos.numeric.entity.Message;
import com.milos.numeric.mapper.ChatIdMapper;
import com.milos.numeric.mapper.MessageDtoMapper;
import com.milos.numeric.service.ChatService;
import com.milos.numeric.service.PersonalInfoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final PersonalInfoService personalInfoService;
    private final MessageDtoMapper messageDtoMapper;
    private final ChatIdMapper chatIdMapper;


    public ChatController(ChatService chatService, PersonalInfoService personalInfoService, MessageDtoMapper messageDtoMapper, ChatIdMapper chatIdMapper) {
        this.chatService = chatService;
        this.personalInfoService = personalInfoService;
        this.messageDtoMapper = messageDtoMapper;
        this.chatIdMapper = chatIdMapper;
    }


    @PostMapping("messages")
    @ResponseBody
    public ResponseEntity<Void> createMessage(@Valid @RequestBody MessageRequest dto) {
        MessageCommand command = this.messageDtoMapper.toCommand(dto);
        chatService.saveMessage(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("conversations")
    @ResponseBody
    public ResponseEntity<Iterable<MessageResponse>> getConversation(@Valid @RequestBody ChatIdRequest dto) {

        ChatIdCommand command = this.chatIdMapper.toCommand(dto);
        Iterable<Message> messages = this.chatService.allMessages(command);
        List<MessageResponse> responses = new ArrayList<>();

        for (Message entity: messages) {
            responses.add(this.messageDtoMapper.toResponse(entity));
        }

        return ResponseEntity.ok(responses);
    }


    @DeleteMapping("conversations")
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteAll() {
         chatService.deleteAll();
    }


}
