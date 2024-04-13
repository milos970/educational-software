package com.milos.numeric.repositories;

import com.milos.numeric.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long>
{

    @Query(value = "SELECT COUNT(m.id) FROM material m WHERE  m.name = :name ", nativeQuery = true)
    public int countByName(@Param("name") String name);

    public Optional<Material> findByName(String name);

    public Optional<Material> findById(Long id);
}
