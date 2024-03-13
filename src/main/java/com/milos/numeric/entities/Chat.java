package com.milos.numeric.entities;

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

public class Chat
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "person_ida")
    private PersonalInfo personalInfoA;


    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "person_idb")
    private PersonalInfo personalInfoB;


    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

}
