package com.milos.numeric.repositories;

import com.milos.numeric.entities.Person;
import com.milos.numeric.entities.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Integer>
{

    public Optional<SystemSettings> findByName(String name);
}
