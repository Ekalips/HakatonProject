package com.ekalips.hakatonproject.networking.request_bodies;

/**
 * Created by wldev on 4/3/17.
 */

public class LoginRequestBody {
    private String email = "";
    private String password = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
