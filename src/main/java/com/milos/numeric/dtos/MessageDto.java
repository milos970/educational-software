package com.milos.numeric.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto
{
    @NotBlank
    @Size(min = 1, message = "{message.content.length.min}")
    @Size(max = 100, message = "{message.content.length.max}")
    private String content;


    @NotBlank(message = "{message.receiver.username}")
    private String receiverUsername;


    private String senderUsername;
}
