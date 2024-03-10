package com.milos.numeric.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@IdClass(ChatId.class)
public class Chat
{
    @Id
    private Long idA;

    @Id
    private Long idB;


    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

}
