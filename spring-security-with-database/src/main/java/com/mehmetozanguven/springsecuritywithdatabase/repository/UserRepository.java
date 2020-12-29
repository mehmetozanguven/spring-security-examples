package com.mehmetozanguven.springsecuritywithdatabase.repository;

import com.mehmetozanguven.springsecuritywithdatabase.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDTO, Long> {

    Optional<UserDTO> findUserByUsername(String username);
}
