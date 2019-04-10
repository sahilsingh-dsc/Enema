package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText etxtFullNameR, etxtMobileR, etxtPasswordR, etxtRePasswordR;
    private CheckBox checkAcceptAggrement;
    private LinearLayout lhLogin;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etxtFullNameR = findViewById(R.id.etxtFullNameR);
        etxtMobileR = findViewById(R.id.etxtMobileR);
        etxtPasswordR = findViewById(R.id.etxtPasswordR);
        etxtRePasswordR = findViewById(R.id.etxtRePasswordR);
        checkAcceptAggrement = findViewById(R.id.checkAcceptAggrement);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateLoginFields();

            }
        });

        lhLogin = findViewById(R.id.lhLogin);
        lhLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

    }

    private void validateLoginFields() {

        String full_name = etxtFullNameR.getText().toString();
        if (TextUtils.isEmpty(full_name)){
            Toast.makeText(this, "Please enter your full name.", Toast.LENGTH_SHORT).show();
            return;
        }

        String mobile_number = etxtMobileR.getText().toString().trim();
        if (TextUtils.isEmpty(mobile_number) || !TextUtils.isDigitsOnly(mobile_number) || mobile_number.length() < 10 || mobile_number.length() > 10) {
            Toast.makeText(this, getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
            return;
        }

        String user_password = etxtPasswordR.getText().toString();
        if (TextUtils.isEmpty(user_password) || user_password.length() < 6) {
            Toast.makeText(this, getString(R.string.valid_password), Toast.LENGTH_SHORT).show();
            return;
        }

        String re_user_password = etxtRePasswordR.getText().toString();
        if (!re_user_password.equals(user_password)){
            Toast.makeText(this, "Password did not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        sendOtpToNumberBundle(full_name, mobile_number, user_password);

    }


    private void sendOtpToNumberBundle(final String full_name, final String mobile_number, final String user_password) {

        DatabaseReference userRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        userRegRef.child(mobile_number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    Toast.makeText(RegisterActivity.this, "Account exist.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "Account not exist.", Toast.LENGTH_SHORT).show();
                    Intent otpIntent = new Intent(RegisterActivity.this, OtpActivity.class);
                    Bundle otpBundle = new Bundle();
                    otpBundle.putString("auth_type", "register");
                    otpBundle.putString("full_name", full_name);
                    otpBundle.putString("mobile_number", mobile_number);
                    otpBundle.putString("user_password", user_password);
                    otpIntent.putExtras(otpBundle);
                    startActivity(otpIntent);
                    Toast.makeText(RegisterActivity.this, "data bundled", Toast.LENGTH_SHORT).show();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
