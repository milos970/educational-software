package com.milos.numeric.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String code;

    @Column(name = "expiration_date")
    @NotBlank
    private String expirationDate;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "person_id")
    private PersonalInfo personalInfo;






}
