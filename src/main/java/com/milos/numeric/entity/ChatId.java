package com.milos.numeric.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Embeddable
@EqualsAndHashCode
public class ChatId implements Serializable
{
    @Column(name = "participant_a")
    private String participantA;

    @Column(name = "participant_b")
    private String participantB;

}

