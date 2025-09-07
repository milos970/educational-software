package com.milos.numeric.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat
{
    @EmbeddedId
    private ChatId id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<Message> messages;

}
