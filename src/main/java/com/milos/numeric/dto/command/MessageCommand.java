package com.milos.numeric.dto.command;

import com.milos.numeric.entity.Chat;
import com.milos.numeric.entity.ChatId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageCommand {

    private String content;
    private String senderUsername;
    private String receiverUsername;
    private Chat chat;


}