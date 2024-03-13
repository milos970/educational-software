package com.milos.numeric.dtos;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewMessageDto
{

    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String content;

    private Long chatId;

    private String sender;

    private Long recipientId;

    private Long senderId;

}
