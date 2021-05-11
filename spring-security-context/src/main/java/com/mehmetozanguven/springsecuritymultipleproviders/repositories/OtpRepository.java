package com.mehmetozanguven.springsecuritymultipleproviders.repositories;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.OtpDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpDTO, Long> {

    Optional<OtpDTO> findOtpDtoByUsername(String username);
}
