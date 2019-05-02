package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enema.enemaapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText txtForgotMobile, txtNewPassword, txtReNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        txtForgotMobile = findViewById(R.id.txtForgotMobile);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtReNewPassword = findViewById(R.id.txtReNewPassword);

        Button btnResetPassword = findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = txtForgotMobile.getText().toString();
                if (mobile.length() != 10){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter valid 10 digit mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = txtNewPassword.getText().toString();
                String repassword = txtReNewPassword.getText().toString();

                if (password.length() < 6){
                    Toast.makeText(ForgotPasswordActivity.this, "Password must be of min 6 char", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!repassword.equals(password)){
                    Toast.makeText(ForgotPasswordActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                resetPassword(mobile, password);

            }
        });

    }

    private void resetPassword(String mobile, String password){

        Intent loginIntent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
        Bundle loginBundle = new Bundle();
        loginBundle.putString("mobile_number", mobile);
        loginBundle.putString("user_password", password);
        loginBundle.putString("auth_type", "reset");
        loginIntent.putExtras(loginBundle);
        startActivity(loginIntent);
        finish();
    }

}
