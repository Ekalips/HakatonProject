package com.ekalips.hakatonproject.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wldev on 4/3/17.
 */

public class Project extends RealmObject{
    private String projectName;
    private String projDescr;
    private long dueDate;
    private boolean isDone;
    private String projectImage;
    @PrimaryKey
    private
    String projectId;
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
