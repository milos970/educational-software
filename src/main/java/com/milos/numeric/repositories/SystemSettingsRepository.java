package com.milos.numeric.repositories;

import com.milos.numeric.entities.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long>
{
    @Query(value = "SELECT s.* FROM system_settings s", nativeQuery = true)
    public Optional<SystemSettings> findFirst();
}
