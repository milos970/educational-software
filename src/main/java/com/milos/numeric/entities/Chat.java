package com.milos.numeric.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name  = "chat_id")
    private Integer id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;
}
