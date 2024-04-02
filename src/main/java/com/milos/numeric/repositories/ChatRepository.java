package com.milos.numeric.repositories;

import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, ChatId>
{

}
