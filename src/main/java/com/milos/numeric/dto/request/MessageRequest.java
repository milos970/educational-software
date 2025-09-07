package com.milos.numeric.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MessageRequest(

        @NotBlank(message = "{message.content.notblank}")
        @Size(max = 100, message = "{message.content.size}")
        String content,

        @NotBlank(message = "{message.receiver.notblank}")
        @Size(max = 50, message = "{message.receiver.size}")
        String receiverUsername,

        @NotBlank(message = "{message.sender.notblank}")
        @Size(max = 50, message = "{message.sender.size}")
        String senderUsername
) {}
