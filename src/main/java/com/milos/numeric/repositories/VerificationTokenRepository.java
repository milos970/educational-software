package com.milos.numeric.repositories;

import com.milos.numeric.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    Optional<VerificationToken> findByCode(String code);
    @Query(value = "SELECT vt.* FROM verification_token vt JOIN personal_info p" +
            " ON p.id = vt.person_id WHERE p.email = :email", nativeQuery = true)
    public Optional<VerificationToken> findByEmail(@Param("email")String email);
}
