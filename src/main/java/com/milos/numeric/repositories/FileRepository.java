package com.milos.numeric.repositories;

import com.milos.numeric.entities.Pdf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Pdf, Integer>
{
    public Pdf findByName(String name);
}
