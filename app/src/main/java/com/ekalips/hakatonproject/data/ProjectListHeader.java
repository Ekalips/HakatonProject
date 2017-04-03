package com.ekalips.hakatonproject.data;

/**
 * Created by wldev on 4/3/17.
 */

public class ProjectListHeader {
    String imageUrl;
    String projectName;
    String projectDescr;


    public ProjectListHeader(String imageUrl, String projectName, String projectDescr) {
        this.imageUrl = imageUrl;
        this.projectName = projectName;
        this.projectDescr = projectDescr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescr() {
        return projectDescr;
    }

    public void setProjectDescr(String projectDescr) {
        this.projectDescr = projectDescr;
    }
}
