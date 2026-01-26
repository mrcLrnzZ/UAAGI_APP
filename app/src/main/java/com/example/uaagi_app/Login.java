package com.example.uaagi_app;

import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Change status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.parseColor("#002F62"));
        }

        // Views
        CardView loginCard = findViewById(R.id.loginCard);
        LinearLayout emailSection = findViewById(R.id.emailSection);
        LinearLayout otpSection = findViewById(R.id.otpSection);
        MaterialButton loginBtn = findViewById(R.id.btnlogin);

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        loginCard.startAnimation(slideUp);

        // When login button is clicked
        loginBtn.setOnClickListener(v -> {
            emailSection.setVisibility(View.GONE);

            otpSection.setVisibility(View.VISIBLE);

            otpSection.setAlpha(0f);
            otpSection.animate()
                    .alpha(1f)
                    .setDuration(400)
                    .start();

            loginCard.animate()
                    .translationY(-1000f)
                    .setDuration(400)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        });

        //back botun
        MaterialButton backBtn = findViewById(R.id.btnBackToLogin);

        backBtn.setOnClickListener(v -> {
            otpSection.setVisibility(View.GONE);

            emailSection.setVisibility(View.VISIBLE);

            emailSection.setAlpha(0f);
            emailSection.animate()
                    .alpha(1f)
                    .setDuration(400)
                    .start();

            loginCard.animate()
                    .translationY(0f)
                    .setDuration(400)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        });



    }
}
