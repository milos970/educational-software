package com.milos.numeric.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verification_token")
public class VerificationToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "person_id")
    private PersonalInfo personalInfo;






}
