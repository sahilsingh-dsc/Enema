package com.enema.enemaapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.CouponState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConstraintLayout layoutSplash = findViewById(R.id.layoutSplash);

        @SuppressLint("ResourceType") final Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        TextView txtWeConnect = findViewById(R.id.txtWeConnect);
        txtWeConnect.startAnimation(animBlink);

                int SPLASH_TIME_OUT = 3000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user != null) {

                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            assert firebaseUser != null;
                            final String username = firebaseUser.getUid();
                            final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                            assert user_mobile != null;
                            final DatabaseReference couponRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child(user_mobile).child(username);
                            CouponState couponState = new CouponState("Coupon Code", "none", "0");
                            couponRef.child("COUPON_STATE").setValue(couponState);
                            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();

                        } else {

                            Intent loginIntent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                            startActivity(loginIntent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();

                        }
                    }
                }, SPLASH_TIME_OUT);

            }

}
