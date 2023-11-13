package org.danilkha;

public class ValidationConfig {
    public static final int minNameLength = 2;
    public static final int maxNameLength = 50;
    public static final int mimPasswordLength = 8;

    public static final String emailRegexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String usernameRegexPattern = "[A-z0-9_-]+";

}
