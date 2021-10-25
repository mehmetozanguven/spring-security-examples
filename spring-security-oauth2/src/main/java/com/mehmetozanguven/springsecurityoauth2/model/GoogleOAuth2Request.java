package com.mehmetozanguven.springsecurityoauth2.model;

import java.util.Map;

public class GoogleOAuth2Request {

    private String registrationId;
    private Map<String, Object> attributes;

    public GoogleOAuth2Request(String registrationId, Map<String, Object> attributes) {
        this.registrationId = registrationId;
        this.attributes = attributes;
    }

    public String getId() {
        return (String) attributes.get("sub");
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
