package com.mehmetozanguven.springsecurityoauth2.model;


import com.mehmetozanguven.springsecurityoauth2.dto.Provider;
import com.mehmetozanguven.springsecurityoauth2.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class SecureUser implements OidcUser, UserDetails {
    // Fields for Oidc
    private Map<String, Object> claims;
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;
    private Map<String, Object> attributes;
    private Collection<SimpleGrantedAuthority> authorities;
    private String name;


    // Common for OAuth2 and BasicAuthentication
    private String provider;

    // Fields for UserDetails
    private String password;
    private String username;

    public static SecureUser createFromBasicAuthentication(UserDTO userDTO) {
        SecureUser secureUser = new SecureUser();
        secureUser.setProvider(Provider.LOCAL.name);
        secureUser.setPassword(userDTO.getPassword());
        secureUser.setUsername(userDTO.getEmail());
        return secureUser;
    }

    public static SecureUser createSecureUserFromOidcUser(OidcUser oidcUser, UserDTO userDTO) {
        SecureUser secureUser = new SecureUser();
        secureUser.setClaims(oidcUser.getClaims());
        secureUser.setUserInfo(oidcUser.getUserInfo());
        secureUser.setIdToken(oidcUser.getIdToken());
        secureUser.setAttributes(oidcUser.getAttributes());
        secureUser.setProvider(Provider.GOOGLE.name);

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userDTO.getRole());
        secureUser.setAuthorities(Collections.singleton(simpleGrantedAuthority));

        secureUser.setName(userDTO.getEmail());
        secureUser.setUsername(userDTO.getEmail());
        secureUser.setPassword(userDTO.getPassword());
        return secureUser;
    }


    // OidcUser Methods
    @Override
    public Map<String, Object> getClaims() {
        return claims;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    public void setUserInfo(OidcUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setIdToken(OidcIdToken idToken) {
        this.idToken = idToken;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Common for OAuth2 and BasicAuthentication Methods
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
