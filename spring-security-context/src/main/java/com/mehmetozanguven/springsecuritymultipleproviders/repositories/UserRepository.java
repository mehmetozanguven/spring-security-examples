package com.mehmetozanguven.springsecuritymultipleproviders.repositories;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDTO, Long> {

    Optional<UserDTO> findUserDTOByUsername(String username);
}
