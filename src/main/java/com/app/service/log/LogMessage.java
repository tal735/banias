package com.app.service.log;

import java.io.Serializable;
import java.util.Date;

public class LogMessage implements Serializable {
    private Long id;
    private String text;
    private Date timestamp;

    public LogMessage(Long id, String text) {
        this.id = id;
        this.text = text;
        this.timestamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
