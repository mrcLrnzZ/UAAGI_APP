package com.example.uaagi_app.ui.users;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.uaagi_app.R;

public class FragmentLoading extends Fragment {

    public FragmentLoading() {}

    public static FragmentLoading newInstance() {
        return new FragmentLoading();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        TextView loadingText = view.findViewById(R.id.loadingText);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        loadingText.setText("Loading, please wait...");
        progressBar.setIndeterminate(true);

        return view;
    }
}
