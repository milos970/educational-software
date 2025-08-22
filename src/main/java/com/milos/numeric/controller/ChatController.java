package com.milos.numeric.controller;

import com.milos.numeric.dto.MessageDto;
import com.milos.numeric.entity.Chat;
import com.milos.numeric.service.ChatService;
import com.milos.numeric.service.PersonalInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {
    private final ChatService chatService;
    private final PersonalInfoService personalInfoService;


    public ChatController(ChatService chatService, PersonalInfoService personalInfoService) {
        this.chatService = chatService;
        this.personalInfoService = personalInfoService;
    }


    @PostMapping("person/message")
    public ResponseEntity<Void> saveMessage(@RequestBody @Valid MessageDto messageDto)
    {
        return this.chatService.saveMessage(messageDto) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("person/conversation")
    @ResponseBody
    public ResponseEntity<List<MessageDto>> findById(@RequestParam("receiver") String receiver)
    {
        Optional<Chat> optional = chatService.findByChatId(
                personalInfoService.findUsernameByAuthorityTeacher().orElseThrow(),
                receiver
        );

        return optional
                .map(chat -> ResponseEntity.ok(chatService.getAllMessages(chat)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/admin/conversation")
    public ResponseEntity<Void> deleteAll() {
        return chatService.deleteAll() ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
