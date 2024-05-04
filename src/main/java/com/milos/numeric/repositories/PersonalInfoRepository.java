package com.milos.numeric.repositories;

import com.milos.numeric.entities.PersonalInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long>
{
    public Optional<PersonalInfo> findByUsername(String username);

    @Query(value = "SELECT p.* FROM personal_info p  WHERE p.authority = :authority", nativeQuery = true)
    public Optional<PersonalInfo> findByAuthority(@Param("authority")String authority);


    @Query(value = "SELECT p.username FROM personal_info p  WHERE p.authority = :authority", nativeQuery = true)
    public Optional<String> findUsernameByAuthority(@Param("authority")String authority);

    @Query(value = "SELECT p.authority FROM personal_info p  WHERE p.username = :username", nativeQuery = true)
    public Optional<String> findAuthorityByUsername(@Param("username")String username);

    public Optional<PersonalInfo> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM personal_info p WHERE p.authority =:authority", nativeQuery = true)
    public void deleteByAuthority(@Param("authority")String authority);
    @Override
    List<PersonalInfo> findAll(Sort sort);




}
