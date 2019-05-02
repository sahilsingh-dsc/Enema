package com.enema.enemaapp.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    private EditText etxtFullNameR, etxtMobileR, etxtPasswordR, etxtRePasswordR;
    private CheckBox checkAcceptAggrement;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etxtFullNameR = findViewById(R.id.etxtFullNameR);
        etxtMobileR = findViewById(R.id.etxtMobileR);
        etxtPasswordR = findViewById(R.id.etxtPasswordR);
        etxtRePasswordR = findViewById(R.id.etxtRePasswordR);
        checkAcceptAggrement = findViewById(R.id.checkAcceptAggrement);

        loadingDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(RegisterActivity.this);
                validateLoginFields();

            }
        });

        LinearLayout lhLogin = findViewById(R.id.lhLogin);
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
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkAcceptAggrement.isChecked()){
            Toast.makeText(this, "Please accept Terms & Condition", Toast.LENGTH_SHORT).show();
            return;
        }

        sendOtpToNumberBundle(full_name, mobile_number, user_password);

    }

    private void sendOtpToNumberBundle(final String full_name, final String mobile_number, final String user_password) {
        loadingDialog.show();
        DatabaseReference userRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child("USER_CREDENTIALS");
        userRegRef.child("+91"+mobile_number).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){

                    Toast.makeText(RegisterActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();

                }else {

                    Intent otpIntent = new Intent(RegisterActivity.this, OtpActivity.class);
                    Bundle otpBundle = new Bundle();
                    otpBundle.putString("auth_type", "register");
                    otpBundle.putString("full_name", full_name);
                    otpBundle.putString("mobile_number", mobile_number);
                    otpBundle.putString("user_password", user_password);
                    otpIntent.putExtras(otpBundle);
                    startActivity(otpIntent);
                    loadingDialog.dismiss();
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
