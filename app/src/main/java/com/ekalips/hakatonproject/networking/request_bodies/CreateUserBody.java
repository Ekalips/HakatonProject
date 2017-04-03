package com.ekalips.hakatonproject.networking.request_bodies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wldev on 4/3/17.
 */

public class CreateUserBody {
    @SerializedName("name")
    @Expose
    private
    String name = "";
    @SerializedName("password")
    @Expose
    private
    String pass = "";
    @SerializedName("email")
    @Expose
    private
    String email = "";
    @SerializedName("firebaseToken")
    @Expose
    String firebaseToken = "";

    @SerializedName("teamId")
    @Expose
    String teamId = "";

    public CreateUserBody() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        return !email.isEmpty() && !pass.isEmpty() && !name.isEmpty();
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
