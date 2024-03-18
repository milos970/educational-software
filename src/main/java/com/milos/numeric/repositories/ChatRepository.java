package com.milos.numeric.repositories;

import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>
{
    @Query(value = "SELECT ch.* FROM chat ch WHERE (ch.person_ida = :idA and ch.person_idb = :idB) or (ch.person_ida = :idB and ch.person_idb = :idA)", nativeQuery = true)
    public Optional<Chat> findByParticipants(@Param("idA") Long idA, @Param("idB") Long idB);
}
