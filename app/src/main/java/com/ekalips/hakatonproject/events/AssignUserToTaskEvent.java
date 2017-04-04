package com.ekalips.hakatonproject.events;

/**
 * Created by ekalips on 4/4/17.
 */

public class AssignUserToTaskEvent {
    String taskId;

    public AssignUserToTaskEvent(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}
