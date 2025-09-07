package com.milos.numeric.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatId implements Serializable
{
    @Column(name = "participant_a")
    private String participantA;

    @Column(name = "participant_b")
    private String participantB;


    public static ChatId of(String usernameA, String usernameB) {
        String first = usernameA.compareTo(usernameB) < 0 ? usernameA : usernameB;
        String second = usernameA.compareTo(usernameB) < 0 ? usernameB : usernameA;
        return new ChatId(first, second);
    }

}

