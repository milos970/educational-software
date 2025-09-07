package com.milos.numeric.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatIdRequest(@NotBlank(message = "{message.content.notblank}")
                            @Size(max = 100, message = "{message.content.size}") String senderUsername,
                            @NotBlank(message = "{message.content.notblank}")
                            @Size(max = 100, message = "{message.content.size}") String receiverUsername) {
}
