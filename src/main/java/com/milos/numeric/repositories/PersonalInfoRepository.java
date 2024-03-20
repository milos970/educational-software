package com.milos.numeric.repositories;

import com.milos.numeric.entities.PersonalInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long>
{
    public Optional<PersonalInfo> findByUsername(String username);

    public Optional<PersonalInfo> findByAuthority(String authority);

    public Optional<PersonalInfo> findByEmail(String email);

    @Query(value = "SELECT * FROM personal_info p WHERE p.email =:email and p.enabled = false", nativeQuery = true)
    public Optional<PersonalInfo> findNotEnabled(String email);



        /*@Query(value = "SELECT * FROM Person u WHERE u.authority <> ADMIN", nativeQuery = true)
        public List<Person> findAllExceptAdmin();
    */
    @Override
    List<PersonalInfo> findAll(Sort sort);
}
