package com.example.uaagi_app.ui.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.function.Consumer;
public class SimpleTextWatcher implements TextWatcher {
    private final Consumer<String> onTextChanged;

    public SimpleTextWatcher(Consumer<String> onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    private static void removeWatcher(TextView view, Object tag) {
        if (tag instanceof TextWatcher) {
            view.removeTextChangedListener((TextWatcher) tag);
        }
    }
    public static void bindTextWatcher(TextView view, TextWatcher watcher) {
        Object tag = view.getTag();

        if (tag instanceof TextWatcher) {
            view.removeTextChangedListener((TextWatcher) tag);
        }

        view.addTextChangedListener(watcher);
        view.setTag(watcher);
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged.accept(s.toString());
    }
    @Override public void afterTextChanged(Editable s) {}
}