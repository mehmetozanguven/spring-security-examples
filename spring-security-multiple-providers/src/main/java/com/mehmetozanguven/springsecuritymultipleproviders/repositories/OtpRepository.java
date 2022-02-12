package com.mehmetozanguven.springsecuritymultipleproviders.repositories;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.OtpDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpDTO, Long> {

    List<OtpDTO> findOtpDtoByUsername(String username);
}
