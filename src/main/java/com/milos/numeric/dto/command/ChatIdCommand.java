package com.milos.numeric.dto.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatIdCommand {
    private String senderUsername;

    private String receiverUsername;
}
