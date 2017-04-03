package com.ekalips.hakatonproject.events;

/**
 * Created by wldev on 4/3/17.
 */

public class SetButtonTextEvent {
    private String text;

    public SetButtonTextEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
