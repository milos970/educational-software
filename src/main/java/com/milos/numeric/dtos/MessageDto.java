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
    @Size(min = 1, message = "Počet znakov musí býť aspoň 1!")
    @Size(max = 100, message = "Počet znakov musí byť najviac 100!")
    private String content;


    @NotBlank(message = "Používateľské meno odosielateľa nesmie byť prázdne!")
    private String receiverUsername;

    private String senderUsername;
}
