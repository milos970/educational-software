package com.milos.numeric.repository;

import com.milos.numeric.entity.Chat;
import com.milos.numeric.entity.ChatId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, ChatId>
{

}
