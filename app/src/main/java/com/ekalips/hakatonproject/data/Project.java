package com.ekalips.hakatonproject.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wldev on 4/3/17.
 */

public class Project extends RealmObject{
    @SerializedName("title")
    @Expose
    private String projectName;
    @SerializedName("description")
    @Expose
    private String projDescr;
    @SerializedName("dedline")
    @Expose
    private long dueDate;
    private boolean isDone;
    private String projectImage = "https://cs7058.userapi.com/c626616/v626616059/46638/xhxRhvlknHc.jpg";
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private
    String projectId;
    @SerializedName("tasks")
    @Expose
    private RealmList<Task> tasks;

    public Project(String projectName, String projDescr, long dueDate, boolean isDone, String projectId, RealmList<Task> tasks, String projectImage) {
        this.projectName = projectName;
        this.projDescr = projDescr;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.projectId = projectId;
        this.tasks = tasks;
        this.projectImage = projectImage;
    }

    public Project() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjDescr() {
        return projDescr;
    }

    public void setProjDescr(String projDescr) {
        this.projDescr = projDescr;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public RealmList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(RealmList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getProjectImage() {
        return projectImage;
    }

    public void setProjectImage(String projectImage) {
        this.projectImage = projectImage;
    }
}
