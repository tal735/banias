package com.app.controller.validator;


import java.util.regex.Pattern;

public class EmailValidator {

    // http://www.w3.org/TR/html5/forms.html#valid-e-mail-address
    // The following JavaScript- and Perl-compatible regular expression is an implementation of the above definition.
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+$");

    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

}
