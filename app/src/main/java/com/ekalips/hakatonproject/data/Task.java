package com.ekalips.hakatonproject.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wldev on 4/3/17.
 */

public class Task  extends RealmObject{
    private boolean isDone;
    private String taskName;
    private String taskDescr;
    @PrimaryKey
    private
    String taskId;
    private long dueDate;
    private RealmList<Member> assignedMembers;


    public Task() {
    }

    public Task(boolean isDone, String taskName, String taskDescr, String taskId, long dueDate, RealmList<Member> assignedMembers) {
        this.isDone = isDone;
        this.taskName = taskName;
        this.taskDescr = taskDescr;
        this.taskId = taskId;
        this.dueDate = dueDate;
        this.assignedMembers = assignedMembers;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescr() {
        return taskDescr;
    }

    public void setTaskDescr(String taskDescr) {
        this.taskDescr = taskDescr;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public RealmList<Member> getAssignedMembers() {
        return assignedMembers;
    }

    public void setAssignedMembers(RealmList<Member> assignedMembers) {
        this.assignedMembers = assignedMembers;
    }
}
