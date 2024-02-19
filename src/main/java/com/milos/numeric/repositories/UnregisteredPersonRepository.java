package com.milos.numeric.repositories;

import com.milos.numeric.entities.UnregisteredPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnregisteredPersonRepository extends CrudRepository<UnregisteredPerson, Integer>
{
    public UnregisteredPerson save(UnregisteredPerson unregisteredPerson);


}
