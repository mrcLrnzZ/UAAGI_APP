package com.example.uaagi_app.utils;

import com.google.android.material.textfield.TextInputLayout;

public class InputValidator {

    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$");
    }

    public static boolean isNumber(String input) {
        if (input == null) return false;
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateEmailInput(TextInputLayout emailInputLayout, String email) {
        if (email.isEmpty()) {
            emailInputLayout.setError("Please enter your email");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Please enter a valid email address");
            return false;
        }

        emailInputLayout.setError(null);
        emailInputLayout.setErrorEnabled(false);
        return true;
    }
}

