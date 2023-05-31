package com.dev.westminsterquestionnaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.dev.westminsterquestionnaire.auth.LoginActivity;
import com.dev.westminsterquestionnaire.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    Animation fromTop;
    Animation fromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        binding.ivLogo.setAnimation(fromTop);
        binding.llBoottom.setAnimation(fromBottom);
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3500);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    Toast.makeText(SplashActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        };
        thread.start();
    }
}