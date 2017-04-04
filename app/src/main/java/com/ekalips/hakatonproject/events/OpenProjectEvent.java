package com.ekalips.hakatonproject.events;

/**
 * Created by ekalips on 4/4/17.
 */

public class OpenProjectEvent {
    String id;

    public OpenProjectEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
