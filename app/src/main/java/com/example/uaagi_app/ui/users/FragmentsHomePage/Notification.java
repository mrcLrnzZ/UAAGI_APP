package com.example.uaagi_app.ui.users.FragmentsHomePage;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uaagi_app.R;

public class Notification extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification, container, false);

        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().findViewById(R.id.top_bar)
                .setVisibility(View.VISIBLE);
    }
}