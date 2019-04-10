package com.enema.enemaapp.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etxtUserMobile, etxtUserPassword;
    private AlertDialog loadingDialog;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etxtUserMobile = findViewById(R.id.etxtUserMobile);
        etxtUserPassword = findViewById(R.id.etxtUserPassword);
        TextView txtForgotPassword = findViewById(R.id.txtForgotPassword);
        ImageView imgFacebookLogin = findViewById(R.id.imgFacebookLogin);
        ImageView imgGoogleLogin = findViewById(R.id.imgGoogleLogin);

        checkAndRequestPermissions();


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent registerIntent = new Intent(LoginActivity.this, OtpActivity.class);
//                startActivity(registerIntent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                finish();
                validateLoginFields();

            }
        });

        LinearLayout lhRegister = findViewById(R.id.lhRegister);
        lhRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });

    }

    private void validateLoginFields() {

        String mobile_number = etxtUserMobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile_number) || !TextUtils.isDigitsOnly(mobile_number) || mobile_number.length() < 10 || mobile_number.length() > 10) {
            Toast.makeText(this, getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
            return;
        }

        String user_password = etxtUserPassword.getText().toString();
        if (TextUtils.isEmpty(user_password) || user_password.length() < 6) {
            Toast.makeText(this, getString(R.string.valid_password), Toast.LENGTH_SHORT).show();
            return;
        }

        sendOtpToNumberBundle(mobile_number, user_password);

    }

    private void sendOtpToNumberBundle(final String mobile_number, final String user_password) {

        DatabaseReference userRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        userRegRef.child(mobile_number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    Toast.makeText(LoginActivity.this, "Account exist.", Toast.LENGTH_SHORT).show();
                    Intent otpIntent = new Intent(LoginActivity.this, OtpActivity.class);
                    Bundle otpBundle = new Bundle();
                    otpBundle.putString("mobile_number", mobile_number);
                    otpBundle.putString("user_password", user_password);
                    otpBundle.putString("auth_type", "login");
                    otpIntent.putExtras(otpBundle);
                    startActivity(otpIntent);
                    Toast.makeText(LoginActivity.this, "data bundled", Toast.LENGTH_SHORT).show();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Account not exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[0]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }
}


