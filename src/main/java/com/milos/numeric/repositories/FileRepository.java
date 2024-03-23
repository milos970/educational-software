package com.milos.numeric.repositories;

import com.milos.numeric.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>
{
    public File findByName(String name);

    public Optional<File> findById(Long id);
}
