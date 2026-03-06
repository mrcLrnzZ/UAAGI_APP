package com.example.uaagi_app.ui.users.FragmentsHomePage;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.model.NotificationModel;
import com.example.uaagi_app.data.repository.NotificationRepository;
import com.example.uaagi_app.network.Realtime.NotificationCenter;
import com.example.uaagi_app.ui.NotificationAdapter;

public class Notification extends Fragment implements NotificationCenter.Listener {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private NotificationRepository notificationRepository;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_notification, container, false);

        // Get the singleton repository instance
        notificationRepository = NotificationRepository.getInstance();

        recyclerView = view.findViewById(R.id.rvNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        adapter = new NotificationAdapter(notificationRepository.getNotifications());

        recyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onNotification(String title, String message) {

        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                adapter.notifyItemInserted(0);
            });
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        NotificationCenter.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        NotificationCenter.setListener(null);
    }

}