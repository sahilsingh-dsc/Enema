package com.enema.enemaapp.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.GSign;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText etxtUserMobile, etxtUserPassword;
    private AlertDialog loadingDialog;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    EditText txtProfileMobileGoogle, etxtProfileEmailGoogle, etxtProfileNameGoogle;
    Button btnEditProfileGoogle;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingDialog = new SpotsDialog.Builder().setContext(LoginActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback < LoginResult > () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle success
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "user canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.cid))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();

        etxtUserMobile = findViewById(R.id.etxtUserMobile);
        etxtUserPassword = findViewById(R.id.etxtUserPassword);

        TextView txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });



        ImageView imgFacebookLogin = findViewById(R.id.imgFacebookLogin);
        imgFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("email", "public_profile")
                );

            }
        });



        ImageView imgGoogleLogin = findViewById(R.id.imgGoogleLogin);
        imgGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        checkAndRequestPermissions();


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(LoginActivity.this);
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







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "google signin failed"+e, Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE]
             //   updateUI(null);
                // [END_EXCLUDE]
            }
        }



    }


    private void handleFacebookAccessToken(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                            LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.alert_profile, null);
                            dialogBuilder.setView(dialogView);
                            final AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.setCancelable(false);
                            txtProfileMobileGoogle = dialogView.findViewById(R.id. txtProfileMobileGoogle);
                            txtProfileMobileGoogle.setEnabled(true);
                            etxtProfileEmailGoogle = dialogView.findViewById(R.id.etxtProfileEmailGoogle);
                            etxtProfileEmailGoogle.setEnabled(true);
                            etxtProfileNameGoogle = dialogView.findViewById(R.id.etxtProfileNameGoogle);
                            etxtProfileNameGoogle.setEnabled(true);
                            btnEditProfileGoogle = dialogView.findViewById(R.id.btnEditProfileGoogle);

                            etxtProfileEmailGoogle.setText(firebaseAuth.getCurrentUser().getEmail());
                            etxtProfileNameGoogle.setText(firebaseAuth.getCurrentUser().getDisplayName());

                            btnEditProfileGoogle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String mobile = txtProfileMobileGoogle.getText().toString();
                                    String email = etxtProfileEmailGoogle.getText().toString();
                                    String name = etxtProfileNameGoogle.getText().toString();

                                    if (TextUtils.isEmpty(mobile)){
                                        Toast.makeText(LoginActivity.this, "Mobile must not be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (!TextUtils.isDigitsOnly(mobile) || mobile.length() != 10 ){
                                        Toast.makeText(LoginActivity.this, "Please enter a valid 10 digit without (+91) mobile number", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(email)){
                                        Toast.makeText(LoginActivity.this, "Email must not be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(name)){
                                        Toast.makeText(LoginActivity.this, "Name must not be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    DatabaseReference profileRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child("USER_PROFILE");
                                    GSign gSign = new GSign("+91"+mobile, name, email);
                                    profileRegRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(gSign);
                                    alertDialog.dismiss();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();

                                }
                            });

                            alertDialog.show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Toast.makeText(this, ""+acct.getId(), Toast.LENGTH_SHORT).show();
        // [START_EXCLUDE silent]
      //  showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                            LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.alert_profile, null);
                            dialogBuilder.setView(dialogView);
                            final AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.setCancelable(false);
                            txtProfileMobileGoogle = dialogView.findViewById(R.id. txtProfileMobileGoogle);
                            txtProfileMobileGoogle.setEnabled(true);
                            etxtProfileEmailGoogle = dialogView.findViewById(R.id.etxtProfileEmailGoogle);
                            etxtProfileEmailGoogle.setEnabled(true);
                            etxtProfileNameGoogle = dialogView.findViewById(R.id.etxtProfileNameGoogle);
                            etxtProfileNameGoogle.setEnabled(true);
                            btnEditProfileGoogle = dialogView.findViewById(R.id.btnEditProfileGoogle);

                            etxtProfileEmailGoogle.setText(firebaseAuth.getCurrentUser().getEmail());
                            etxtProfileNameGoogle.setText(firebaseAuth.getCurrentUser().getDisplayName());

                            btnEditProfileGoogle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String mobile = txtProfileMobileGoogle.getText().toString();
                                    String email = etxtProfileEmailGoogle.getText().toString();
                                    String name = etxtProfileNameGoogle.getText().toString();

                                    if (TextUtils.isEmpty(mobile)){
                                        Toast.makeText(LoginActivity.this, "Mobile must not be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (!TextUtils.isDigitsOnly(mobile) || mobile.length() != 10 ){
                                        Toast.makeText(LoginActivity.this, "Please enter a valid 10 digit without (+91) mobile number", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(email)){
                                        Toast.makeText(LoginActivity.this, "Email must not be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(name)){
                                        Toast.makeText(LoginActivity.this, "Name must not be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    DatabaseReference profileRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child("USER_PROFILE");
                                    GSign gSign = new GSign("+91"+mobile, name, email);
                                    profileRegRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(gSign);
                                    alertDialog.dismiss();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();

                                }
                            });

                            alertDialog.show();
//
//
                        } else {
                            // If sign in fails, display a message to the user.
                         //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "auth failed", Toast.LENGTH_SHORT).show();
                          //  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                         //   updateUI(null);
                        }

                        // [START_EXCLUDE]
                    //    hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

        loadingDialog.show();

        DatabaseReference userRegRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child("USER_CREDENTIALS");
        userRegRef.child("+91"+mobile_number).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){

                    String db_password = (String) dataSnapshot.child("user_password").getValue();

                    if (user_password.equals(db_password)){

                        Intent otpIntent = new Intent(LoginActivity.this, OtpActivity.class);
                        Bundle otpBundle = new Bundle();
                        otpBundle.putString("mobile_number", mobile_number);
                        otpBundle.putString("user_password", user_password);
                        otpBundle.putString("auth_type", "login");
                        otpIntent.putExtras(otpBundle);
                        startActivity(otpIntent);
                        loadingDialog.dismiss();
                        finish();

                    }else {

                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }

                }else {

                    Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
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

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}


