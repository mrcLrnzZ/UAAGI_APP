package com.example.uaagi_app.utils;

import com.google.android.material.textfield.TextInputLayout;

import com.example.uaagi_app.ui.utils.UiHelpers;
public class InputValidator {

    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
        if (isNotEmpty(email)) {
            UiHelpers.textInputLayoutSetErr(emailInputLayout, "Please enter your email");
            return false;
        }

        if (!isValidEmail(email)) {
            UiHelpers.textInputLayoutSetErr(emailInputLayout, "Please enter a valid email address");
            return false;
        }

        emailInputLayout.setError(null);
        emailInputLayout.setErrorEnabled(false);
        return true;
    }

    public static boolean isValid(String Input, TextInputLayout layout, String ErrMessage){
        if (isNotEmpty(Input)) {
            UiHelpers.textInputLayoutSetErr(layout, ErrMessage);
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }
}

