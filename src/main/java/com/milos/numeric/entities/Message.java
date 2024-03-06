package com.milos.numeric.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private String date;

    @ManyToOne()
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chat chat;

}
