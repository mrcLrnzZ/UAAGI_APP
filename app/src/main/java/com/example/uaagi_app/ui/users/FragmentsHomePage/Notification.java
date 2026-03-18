package com.example.uaagi_app.ui.users.FragmentsHomePage;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.repository.NotificationRepository;
import com.example.uaagi_app.network.Realtime.NotificationCenter;
import com.example.uaagi_app.network.Services.NotificationService;
import com.example.uaagi_app.ui.NotificationAdapter;
import com.example.uaagi_app.utils.SessionManager;

public class Notification extends Fragment implements NotificationCenter.Listener {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private TextView cardSubtitle;
    private NotificationRepository notificationRepository;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_notification, container, false);

        int userId = SessionManager.getInstance(getContext()).getUserId();
        Log.d("USERID", String.valueOf(userId));
        notificationRepository = NotificationRepository.getInstance();

        recyclerView = view.findViewById(R.id.rvNotification);
        cardSubtitle = view.findViewById(R.id.cardSubtitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        NotificationService service = new NotificationService(getContext());

        service.updateUserNotification(userId,
                new NotificationService.NotificationCallback() {
                    @Override
                    public void onResponse() {

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });


        adapter = new NotificationAdapter(notificationRepository.getNotifications());

        adapter = new NotificationAdapter(notificationRepository.getNotifications());

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                updateNotifCount();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                updateNotifCount();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                updateNotifCount();
            }
        });

        recyclerView.setAdapter(adapter);

        updateNotifCount();

        recyclerView.setAdapter(adapter);
        return view;
    }
    private void updateNotifCount() {
        int notifCount = adapter.getItemCount();

        cardSubtitle.setText(
                notifCount == 0 ? "0 alerts" :
                        notifCount == 1 ? "1 new alert" :
                                notifCount + " new alerts"
        );
    }
    @Override
    public void onNotification(String title, String message, String timeAgo) {

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