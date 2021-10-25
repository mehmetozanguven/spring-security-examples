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
        SecureUser basicAuth = new SecureUser();
        basicAuth.setProvider(Provider.LOCAL.name);
        basicAuth.setUsername(userDTO.getEmail());
        basicAuth.setPassword(userDTO.getPassword());

        return basicAuth;
    }

    public static SecureUser createFromOidcUser(OidcUser oidcUser, UserDTO userDTO) {
        SecureUser myOidcUser = new SecureUser();
        myOidcUser.setClaims(oidcUser.getClaims());
        myOidcUser.setUserInfo(oidcUser.getUserInfo());
        myOidcUser.setIdToken(oidcUser.getIdToken());
        myOidcUser.setAttributes(oidcUser.getAttributes());
        myOidcUser.setProvider(Provider.GOOGLE.name);

        SimpleGrantedAuthority readAuthority = new SimpleGrantedAuthority(userDTO.getRole());
        myOidcUser.setAuthorities(Collections.singleton(readAuthority));
        myOidcUser.setName(userDTO.getEmail());

        myOidcUser.setUsername(userDTO.getEmail());
        return myOidcUser;
    }

    // Implementations from OidcUser interface
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    // Implementations from UserDetails interface
    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    @Override
    public String toString() {
        return "SecureUser{" +
                "claims=" + claims +
                ", userInfo=" + userInfo +
                ", idToken=" + idToken +
                ", authorities=" + authorities +
                ", name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
