package com.ekalips.hakatonproject.networking.request_bodies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wldev on 4/3/17.
 */

public class CreateTeamBody {
    @SerializedName("title")
    @Expose
    private String teamName = "";
    @SerializedName("description")
    @Expose
    private String teamDescr = "";
    @SerializedName("target")
    @Expose
    private String teamTarget = "";

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDescr() {
        return teamDescr;
    }

    public void setTeamDescr(String teamDescr) {
        this.teamDescr = teamDescr;
    }

    public String getTeamTarget() {
        return teamTarget;
    }

    public void setTeamTarget(String teamTarget) {
        this.teamTarget = teamTarget;
    }

    public boolean isValid() {
        return (!teamName.isEmpty() && !teamDescr.isEmpty());
    }
}
