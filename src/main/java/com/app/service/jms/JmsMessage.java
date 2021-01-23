package com.app.service.jms;

import java.io.Serializable;

public class JmsMessage implements Serializable {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
