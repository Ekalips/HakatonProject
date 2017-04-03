package com.ekalips.hakatonproject.networking.request_bodies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wldev on 4/3/17.
 */

public class LoginRequestBody {
    @SerializedName("email")
    @Expose
    private String email = "";
    @SerializedName("password")
    @Expose
    private String password = "";
    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken = "";
    @SerializedName("team")
    @Expose
    private String teamToken;

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getTeamToken() {
        return teamToken;
    }

    public void setTeamToken(String teamToken) {
        this.teamToken = teamToken;
    }

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

    public boolean isValid() {
        return !email.isEmpty() && !password.isEmpty();
    }
}
