package com.enema.enemaapp.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class OtpActivity extends AppCompatActivity {

    private EditText code_one, code_two, code_three, code_four, code_five, code_six;
    FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId, auth_type, user_password, full_name, mobile_number;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Bundle loginBundle = getIntent().getExtras();
        assert loginBundle != null;
        mobile_number = loginBundle.getString("mobile_number");
        user_password = loginBundle.getString("user_password");
        full_name = loginBundle.getString("full_name");
        auth_type = loginBundle.getString("auth_type");

        firebaseAuth = FirebaseAuth.getInstance();

        TextView txtSentOnThisNumber = findViewById(R.id.txtSentOnThisNumber);
        txtSentOnThisNumber.setText(String.format("%s %s", getString(R.string.nine_one), mobile_number));

        code_one = findViewById(R.id.code_one);
        code_two = findViewById(R.id.code_two);
        code_three = findViewById(R.id.code_three);
        code_four = findViewById(R.id.code_four);
        code_five = findViewById(R.id.code_five);
        code_six = findViewById(R.id.code_six);


        loadingDialog = new SpotsDialog.Builder().setContext(OtpActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Verifing Code")
                .setCancelable(false)
                .build();


        ImageView imgVerifyAndLogin = findViewById(R.id.imgVerifyAndLogin);
        imgVerifyAndLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpcode = code_one.getText().toString() +
                        code_two.getText().toString() +
                        code_three.getText().toString() +
                        code_four.getText().toString() +
                        code_five.getText().toString() +
                        code_six.getText().toString();
                Toast.makeText(OtpActivity.this, "" + otpcode, Toast.LENGTH_SHORT).show();
                verifyPhoneNumberWithCode(mVerificationId, otpcode);
            }
        });


        TextView txtResendCode = findViewById(R.id.txtResendCode);
        txtResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(mobile_number, mResendToken);
            }
        });


        code_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!code_one.getText().toString().equals("")) {
                    code_one.clearFocus();
                    code_two.requestFocus();
                    code_two.setCursorVisible(true);
                }

            }
        });

        code_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                code_two.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!code_two.getText().toString().equals("")) {
                    code_two.clearFocus();
                    code_three.requestFocus();
                    code_three.setCursorVisible(true);
                }

            }
        });

        code_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                code_three.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //6594 otp
            @Override
            public void afterTextChanged(Editable s) {
                if (!code_three.getText().toString().equals("")) {
                    code_three.clearFocus();
                    code_four.requestFocus();
                    code_four.setCursorVisible(true);
                }

            }
        });

        code_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                code_four.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //6594 otp
            @Override
            public void afterTextChanged(Editable s) {
                if (!code_four.getText().toString().equals("")) {
                    code_four.clearFocus();
                    code_five.requestFocus();
                    code_five.setCursorVisible(true);
                }

            }
        });

        code_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                code_five.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //6594 otp
            @Override
            public void afterTextChanged(Editable s) {
                if (!code_five.getText().toString().equals("")) {
                    code_five.clearFocus();
                    code_six.requestFocus();
                    code_six.setCursorVisible(true);
                }

            }
        });

        code_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                code_six.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!code_six.getText().toString().equals("")) {
                    hideKeyboard(OtpActivity.this);
                    code_six.clearFocus();
                }

            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OtpActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OtpActivity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OtpActivity.this, "Quota Exceeded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

        startPhoneNumberVerification(mobile_number);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        loadingDialog.show();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (auth_type.equals("register")) {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                assert firebaseUser != null;
                                final String username = firebaseUser.getUid();
                                String mobile_with_code = "+91"+mobile_number;
                                DatabaseReference userRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
                                UserData userData = new UserData(full_name, mobile_with_code, user_password);
                                userRegRef.child(mobile_with_code).child(username).child("PROFILE_DATA").setValue(userData);
                                Toast.makeText(OtpActivity.this, "Register done", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(OtpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(OtpActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();

                            loadingDialog.dismiss();
                        } else {
                            Toast.makeText(OtpActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OtpActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                            }
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        Toast.makeText(this, "Code Resent", Toast.LENGTH_SHORT).show();
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
