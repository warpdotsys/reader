/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package com.htmake.reader;

import org.springframework.context.ApplicationEvent;

public class SpringEvent
extends ApplicationEvent {
    private String event;
    private String message;

    public SpringEvent(Object source, String event, String message) {
        super(source);
        this.event = event;
        this.message = message;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

