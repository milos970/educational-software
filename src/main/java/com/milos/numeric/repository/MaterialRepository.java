package com.milos.numeric.repository;

import com.milos.numeric.entity.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MaterialRepository extends CrudRepository<Material, Long>
{

     Optional<Material> findById(Long id);
}
