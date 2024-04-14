package com.milos.numeric.dtos;


import jakarta.persistence.Column;
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

    @NotBlank(message = "{message.sender.username}")
    @Column(name = "sender_username")
    private String senderUsername;

    @NotBlank(message = "{message.receiver.username}")
    @Column(name = "receiver_username")
    private String receiverUsername;
}
