package com.ekalips.hakatonproject.networking.response_bodies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ekalips on 4/3/17.
 */

public class CreateTeamResponse {
    @SerializedName("teamId")
    @Expose
    private String teamId;

    public String getTeamId() {
        return teamId;
    }
}
