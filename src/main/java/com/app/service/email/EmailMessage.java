package com.app.service.email;

import java.io.Serializable;
import java.util.List;

public class EmailMessage implements Serializable {
    private List<String> to;
    private String subject;
    private String text;

    public EmailMessage() {
    }

    public EmailMessage(List<String> to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
