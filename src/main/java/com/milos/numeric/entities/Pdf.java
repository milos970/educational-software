package com.milos.numeric.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pdf
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] data;

    String name;


    public Pdf(byte[] data, String name)
    {
        this.data = data;
        this.name = name;
    }
}
