package com.milos.numeric.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{message.content.notblank}")
    @Size(max = 100, message = "{message.content.size}")
    private String content;

    @NotBlank(message = "{message.sender.notblank}")
    @Column(name = "sender_username")
    private String senderUsername;

    @NotBlank(message = "{message.receiver.notblank}")
    @Column(name = "receiver_username")
    private String receiverUsername;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "participant_a", referencedColumnName = "participant_a"),
            @JoinColumn(name = "participant_b", referencedColumnName = "participant_b")
    })
    private Chat chat;

}
