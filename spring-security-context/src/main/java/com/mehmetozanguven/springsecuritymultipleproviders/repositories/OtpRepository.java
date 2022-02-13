package com.mehmetozanguven.springsecuritymultipleproviders.repositories;

import com.mehmetozanguven.springsecuritymultipleproviders.entities.OtpDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpDTO, Long> {

    @Query("FROM OtpDTO WHERE username = ?1 AND  otp = ?2")
    Optional<OtpDTO> findOtpDtoByUsername(String username, String otpCode);
}
