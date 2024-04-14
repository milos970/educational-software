package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "chat_participant_a", referencedColumnName = "participant_a"),
            @JoinColumn(name = "chat_participant_b", referencedColumnName = "participant_b")
    })
    private Chat chat;

}
