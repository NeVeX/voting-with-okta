package com.nevex.model;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class OktaUser {

    private final String email;

    public OktaUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
