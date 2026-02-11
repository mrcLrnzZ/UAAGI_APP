package com.example.uaagi_app.ui.utils;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

public class UiHelpers {
    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void textInputLayoutSetErr(TextInputLayout layout, String ErrMessage){
        layout.setError(ErrMessage);
    }
    public static void setRequiredHint(Context context, TextInputLayout layout, String text) {
        String hint = text + " *";
        SpannableString spannable = new SpannableString(hint);

        spannable.setSpan(
                new ForegroundColorSpan(
                        ContextCompat.getColor(context, android.R.color.holo_red_dark)
                ),
                hint.length() - 1,
                hint.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        layout.setHint(spannable);
    }
    public static void dropDownMaker(String[] array, AutoCompleteTextView textView, Context context) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, array);
        dropDownSetter(textView, adapter);
    }
    public static void dropDownSetter(AutoCompleteTextView textView, ArrayAdapter<String> adapter) {
        textView.setAdapter(adapter);
    }
    public static void addEntry(int layoutResId, LinearLayout container, Context context) {
        View entryView = LayoutInflater.from(context)
                .inflate(layoutResId, container, false);

//        // Optional remove button inside layout
//        ImageButton btnRemove = entryView.findViewById(R.id.btnRemove);
//        if (btnRemove != null) {
//            btnRemove.setOnClickListener(v -> container.removeView(entryView));
//        }

        container.addView(entryView);
    }

}
