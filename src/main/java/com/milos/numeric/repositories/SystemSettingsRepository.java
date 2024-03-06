package com.milos.numeric.repositories;

import com.milos.numeric.entities.Person;
import com.milos.numeric.entities.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Integer>
{

}
