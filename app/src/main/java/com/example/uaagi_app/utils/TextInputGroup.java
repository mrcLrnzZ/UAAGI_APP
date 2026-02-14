package com.example.uaagi_app.utils;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class TextInputGroup {
    private final TextInputLayout[] fields;
    public TextInputGroup(TextInputLayout... fields) {
        this.fields = fields;
    }
    public boolean validateRequired() {
        boolean isValid = true;

        for (TextInputLayout layout : fields) {
            EditText editText = layout.getEditText();

            if (editText == null || editText.getText().toString().trim().isEmpty()) {
                layout.setError("This field is required");
                isValid = false;
            } else {
                layout.setError(null);
            }
        }
        return isValid;
    }
    public void clearErrors() {
        for (TextInputLayout layout : fields) {
            layout.setError(null);
        }
    }
    public void setEnabled(boolean enabled) {
        for (TextInputLayout layout : fields) {
            layout.setEnabled(enabled);
        }
    }
    public TextInputLayout[] getFields() {
        return fields;
    }
}
