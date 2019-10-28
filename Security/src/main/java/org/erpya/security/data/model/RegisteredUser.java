package org.erpya.security.data.model;

import org.erpya.base.util.Util;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class RegisteredUser {

    private String userName;
    private String email;
    private String token;
    private String lastName;
    private String name;

    public RegisteredUser(String name, String lastName, String userName, String email, String token) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return getName() + (Util.isEmpty(getLastName())? "": " " + getLastName());
    }

    public String getToken() {
        return token;
    }
}
