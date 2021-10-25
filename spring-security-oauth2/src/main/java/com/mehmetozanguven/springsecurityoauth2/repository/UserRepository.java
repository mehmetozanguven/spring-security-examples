package com.mehmetozanguven.springsecurityoauth2.repository;

import com.mehmetozanguven.springsecurityoauth2.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
    @Query("SELECT u FROM UserDTO u WHERE u.email = ?1")
    Optional<UserDTO> findByEmail(String email);
}
