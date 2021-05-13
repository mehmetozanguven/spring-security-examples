package com.mehmetozanguven.springsecuritycsrfsetup.configuration;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ThreadLocalRandom;

public class MyCsrfRepository implements CsrfTokenRepository {


    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken("mehmetozan", "guven", generateRandomToken());
    }

    /**
     * Dummy implementation
     * @return
     */
    private String generateRandomToken() {
        int rand_int1 = ThreadLocalRandom.current().nextInt();
        return rand_int1 + "abcdfe";
    }

    /**
     * This will be your implementation
     * @param token
     * @param request
     * @param response
     */
    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * This will be your implementation
     * @param request
     * @return
     */
    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return null;
    }
}
