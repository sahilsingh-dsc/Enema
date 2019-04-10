package com.enema.enemaapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        @SuppressLint("ResourceType") final Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        TextView txtWeConnect = findViewById(R.id.txtWeConnect);
        txtWeConnect.startAnimation(animBlink);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){

                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                }else {

                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                }
            }
        }, SPLASH_TIME_OUT);

    }
}
