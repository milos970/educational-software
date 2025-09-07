package com.milos.numeric.repository;

import com.milos.numeric.entity.PersonalInfo;
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
     Optional<PersonalInfo> findByUsername(String username);

    @Query(value = "SELECT p.* FROM personal_info p  WHERE p.role = :role", nativeQuery = true)
     Optional<PersonalInfo> findByAuthority(@Param("role")String role);


    @Query(value = "SELECT p.username FROM personal_info p  WHERE p.role = :role", nativeQuery = true)
    Optional<String> findUsernameByAuthority(@Param("role")String role);

    @Query(value = "SELECT p.role FROM personal_info p  WHERE p.username = :username", nativeQuery = true)
     Optional<String> findAuthorityByUsername(@Param("username")String username);

     Optional<PersonalInfo> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM personal_info p WHERE p.role =:role", nativeQuery = true)
     void deleteByAuthority(@Param("role")String role);

    @Override
    List<PersonalInfo> findAll(Sort sort);


    boolean existsByUsername (String username);




}
