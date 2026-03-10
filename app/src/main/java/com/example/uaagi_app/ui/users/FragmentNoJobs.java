package com.example.uaagi_app.ui.users;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.uaagi_app.R;

public class FragmentNoJobs extends Fragment {

    public FragmentNoJobs() {}

    public static FragmentNoJobs newInstance() {
        return new FragmentNoJobs();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_no_jobs, container, false);

        TextView noJobsText = view.findViewById(R.id.txtNoJobsMessage);

        noJobsText.setText("No saved jobs yet.");

        return view;
    }
}
