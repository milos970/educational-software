package com.milos.numeric.repositories;

import com.milos.numeric.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long>
{
    public File findByFileName(String name);
}
