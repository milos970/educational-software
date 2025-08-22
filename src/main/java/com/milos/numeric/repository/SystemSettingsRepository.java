package com.milos.numeric.repository;

import com.milos.numeric.entity.SystemSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingsRepository extends CrudRepository<SystemSettings, Long>
{
    @Query(value = "SELECT s.* FROM system_settings s", nativeQuery = true)
     Optional<SystemSettings> findFirst();
}
