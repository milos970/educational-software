package com.milos.numeric.entities;

import com.milos.numeric.TokenType;
import com.milos.numeric.validators.DateValid;
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

    @NotBlank
    @DateValid
    @Column(name = "expiration_date")
    private String expirationDate;

    @OneToOne(targetEntity = PersonalInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "person_id")
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;




}
