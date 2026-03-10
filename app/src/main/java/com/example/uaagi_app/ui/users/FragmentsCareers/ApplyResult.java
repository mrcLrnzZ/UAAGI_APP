package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uaagi_app.R;

public class ApplyResult extends Fragment {

    private static final String ARG_RESULT = "result";

    private String result;

    public ApplyResult() {
        // Required empty public constructor
    }

    public static ApplyResult newInstance(String result) {
        ApplyResult fragment = new ApplyResult();
        Bundle args = new Bundle();
        args.putString(ARG_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            result = getArguments().getString(ARG_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if ("success".equals(result)) {
            return inflater.inflate(R.layout.fragment_careers_apply_success, container, false);

        } else if ("error".equals(result)) {
            return inflater.inflate(R.layout.fragment_careers_apply_error, container, false);

        } else if ("alreadysent".equals(result)) {
            return inflater.inflate(R.layout.fragment_careers_apply_has_sent, container, false);

        } else {
            return inflater.inflate(R.layout.fragment_apply_result, container, false);
        }
    }
}