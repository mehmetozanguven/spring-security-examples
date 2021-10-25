package com.mehmetozanguven.springsecurityoauth2.dto;

public enum Provider {
    LOCAL("local"), GOOGLE("google");

    public final String name;

    Provider(String name) {
        this.name = name;
    }
}
