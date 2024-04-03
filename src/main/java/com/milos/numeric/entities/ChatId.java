package com.milos.numeric.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ChatId implements Serializable
{
    @Column(name = "participant_a")
    private String participantA;

    @Column(name = "participant_b")
    private String participantB;

}

