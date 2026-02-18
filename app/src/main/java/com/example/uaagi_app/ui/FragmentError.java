package com.example.uaagi_app.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.ui.utils.UiHelpers;

public class FragmentError extends Fragment {

    public interface RetryListener {
        void onRetry();
    }

    private RetryListener retryListener;

    public FragmentError() {}

    public static FragmentError newInstance(String errorMessage) {
        FragmentError fragment = new FragmentError();
        Bundle args = new Bundle();
        args.putString("errorMessage", errorMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (getParentFragment() instanceof RetryListener) {
            retryListener = (RetryListener) getParentFragment();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        retryListener = null;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        TextView errorText = view.findViewById(R.id.errorText);
        Button retryButton = view.findViewById(R.id.btnRetry);
        Bundle args = getArguments();
        errorText.setText(
                args != null
                        ? args.getString("errorMessage", "Something went wrong")
                        : "Something went wrong"
        );

        retryButton.setOnClickListener(v -> {
            if (retryListener != null) {
                retryListener.onRetry();
            }
        });

        return view;
    }
}
