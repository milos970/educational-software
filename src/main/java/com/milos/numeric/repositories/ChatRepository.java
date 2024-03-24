package com.milos.numeric.repositories;

import com.milos.numeric.entities.Chat;
import com.milos.numeric.entities.ChatId;
import com.milos.numeric.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, ChatId>
{

}
