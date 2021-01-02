package com.app.controller.validator;


import java.util.regex.Pattern;

public class EmailValidator {
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");

    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

}
