package utils;

public class UserFactory {

    public static String getUsername(UserType userType) {
        switch (userType) {
            case STANDARD:
                return "standard_user";
            case PROBLEM:
                return "problem_user";
            case LOCKED:
                return "locked_out_user";
            case PERFORMANCE:
                return "performance_glitch_user";
            case ERROR:
                return "error_user";
            case VISUAL:
                return "visual_user";
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }

    public static String getPassword() {
        return "secret_sauce"; // all users share same password
    }
}

