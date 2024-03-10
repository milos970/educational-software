package com.milos.numeric.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatId implements Serializable
{
    private Long idA;
    private Long idB;

}
