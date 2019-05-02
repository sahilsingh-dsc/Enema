package com.enema.enemaapp.ui.activities;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.ProfileData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class AccountProfileActivity extends AppCompatActivity {

    int editState = 0;
    String genderState = "male";
    AlertDialog loadingDialog;
    FirebaseUser firebaseUser;
    DatabaseReference profileRef;
    EditText txtProfileMobile;
    EditText etxtProfileEmail, etxtProfileName, etxtCityResidence, etxtCityOnID, etxtDOB;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        etxtProfileEmail = findViewById(R.id.etxtProfileEmail);
        etxtProfileName = findViewById(R.id.etxtProfileName);
        etxtCityResidence = findViewById(R.id.etxtCityResidence);
        etxtCityOnID = findViewById(R.id.etxtCityOnID);
        etxtDOB = findViewById(R.id.etxtDOB);
        txtProfileMobile = findViewById(R.id.txtProfileMobile);
        txtProfileMobile.setEnabled(false);

        etxtCityResidence.setEnabled(true);
        etxtCityOnID.setEnabled(true);
        etxtDOB.setEnabled(true);

        profileRef = FirebaseDatabase.getInstance().getReference("USER_DATA");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){

            user_id = firebaseUser.getUid();
            //Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();

        }

        loadingDialog = new SpotsDialog.Builder().setContext(AccountProfileActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();


        etxtProfileEmail.setEnabled(false);
        etxtProfileName.setEnabled(false);
        etxtCityResidence.setEnabled(false);
        etxtCityOnID.setEnabled(false);
        etxtDOB.setEnabled(false);

        getUserData();


        ImageView imgProfileToAccount = findViewById(R.id.imgProfileToAccount);
        imgProfileToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });



        final Button txtEditProfile = findViewById(R.id.btnEditProfile);
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editState == 0){
                    txtEditProfile.setText(getString(R.string.spro));
                    editProfile();
                    editState = 1;

                } else {
                    txtEditProfile.setText(getString(R.string.edtpro));
                    editState = 0;
                    updateProfile();
                }

            }
        });

    }

    private void editProfile(){

        EditText etxtProfileEmail, etxtProfileName, etxtCityResidence, etxtCityOnID, etxtDOB;
        LinearLayout lhGenderBox, lvMale, lvFemale;
        final ImageView imgMale, imgFemale;
        final TextView txtMale, txtFemale;

        etxtProfileEmail = findViewById(R.id.etxtProfileEmail);
        etxtProfileName = findViewById(R.id.etxtProfileName);
        etxtCityResidence = findViewById(R.id.etxtCityResidence);
        etxtCityOnID = findViewById(R.id.etxtCityOnID);



        etxtDOB = findViewById(R.id.etxtDOB);
        lhGenderBox = findViewById(R.id.lhGenderBox);
        lvMale = findViewById(R.id.lvMale);
        lvFemale = findViewById(R.id.lvFemale);
        imgMale = findViewById(R.id.imgMale);
        imgFemale = findViewById(R.id.imgFemale);
        txtMale = findViewById(R.id.txtMale);
        txtFemale = findViewById(R.id.txtFemale);

        etxtProfileEmail.setEnabled(true);
        etxtProfileName.setEnabled(true);
        etxtCityResidence.setEnabled(true);
        etxtCityOnID.setEnabled(true);
        etxtDOB.setEnabled(true);
        lhGenderBox.setVisibility(View.VISIBLE);
        lvMale.setEnabled(true);
        lvFemale.setEnabled(true);
        txtProfileMobile.setEnabled(true);

        txtProfileMobile.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));
        etxtProfileEmail.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));
        etxtProfileName.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));
        etxtCityResidence.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));
        etxtCityOnID.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));
        etxtDOB.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));
        lhGenderBox.setBackground(getResources().getDrawable(R.drawable.rect_border_dash));

        lvMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMale.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy));
                imgFemale.setImageDrawable(getResources().getDrawable(R.drawable.ic_woman_grey));
                txtMale.setTextColor(getResources().getColor(R.color.colorCyanTeal));
                txtFemale.setTextColor(getResources().getColor(R.color.colorBlack));
                genderState = "male";
            }
        });

        lvFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMale.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_grey));
                imgFemale.setImageDrawable(getResources().getDrawable(R.drawable.ic_woman));
                txtMale.setTextColor(getResources().getColor(R.color.colorBlack));
                txtFemale.setTextColor(getResources().getColor(R.color.colorCyanTeal));
                genderState = "female";
            }
        });

    }

    private void updateProfile(){

        final EditText etxtProfileEmail, etxtProfileName, etxtCityResidence, etxtCityOnID, etxtDOB;
        LinearLayout lhGenderBox, lvMale, lvFemale;

        etxtProfileEmail = findViewById(R.id.etxtProfileEmail);
        etxtProfileName = findViewById(R.id.etxtProfileName);
        etxtCityResidence = findViewById(R.id.etxtCityResidence);
        etxtCityOnID = findViewById(R.id.etxtCityOnID);
        etxtDOB = findViewById(R.id.etxtDOB);
        lhGenderBox = findViewById(R.id.lhGenderBox);
        lvMale = findViewById(R.id.lvMale);
        lvFemale = findViewById(R.id.lvFemale);

        etxtProfileEmail.setEnabled(false);
        etxtProfileName.setEnabled(false);
        etxtCityResidence.setEnabled(false);
        etxtCityOnID.setEnabled(false);
        etxtDOB.setEnabled(false);
        lhGenderBox.setVisibility(View.GONE);
        lvMale.setEnabled(false);
        lvFemale.setEnabled(false);
        txtProfileMobile.setEnabled(false);

        txtProfileMobile.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtProfileEmail.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtProfileName.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtCityResidence.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtCityOnID.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtDOB.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        lhGenderBox.setBackground(getResources().getDrawable(R.drawable.border_nothing));

        String user_email = etxtProfileEmail.getText().toString();
        String full_name = etxtProfileName.getText().toString();
        String user_city_residence = etxtCityResidence.getText().toString();
        String user_city_on_id = etxtCityOnID.getText().toString();
        String user_dob = etxtDOB.getText().toString();
        String mobile = txtProfileMobile.getText().toString();
        ProfileData profileData = new ProfileData(user_email, full_name, user_city_residence, user_city_on_id, user_dob, genderState, mobile);
        profileRef.child("USER_PROFILE").child(user_id).setValue(profileData);

    }

    private void getUserData(){

        loadingDialog.show();

        final EditText etxtProfileEmail, etxtProfileName, etxtCityResidence, etxtCityOnID, etxtDOB;
        etxtProfileEmail = findViewById(R.id.etxtProfileEmail);
        etxtProfileName = findViewById(R.id.etxtProfileName);
        etxtCityResidence = findViewById(R.id.etxtCityResidence);
        etxtCityOnID = findViewById(R.id.etxtCityOnID);
        etxtDOB = findViewById(R.id.etxtDOB);

        profileRef.child("USER_PROFILE").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@  NonNull DataSnapshot dataSnapshot) {

                String user_email = (String) dataSnapshot.child("user_email").getValue();
                String full_name = (String) dataSnapshot.child("full_name").getValue();
                String user_city_residence = (String) dataSnapshot.child("user_city_residence").getValue();
                String user_city_on_id = (String) dataSnapshot.child("user_city_on_id").getValue();
                String user_dob = (String) dataSnapshot.child("user_dob").getValue();
                String user_gender = (String) dataSnapshot.child("user_gender").getValue();
                String user_mobile = (String) dataSnapshot.child("user_mobile").getValue();

                if (user_gender != null){

                    if (user_gender.equals("male")){
                        etxtProfileName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boy, 0, 0, 0);
                    }

                    if (user_gender.equals("female")){
                        etxtProfileName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_woman, 0, 0, 0);
                    }

                }

                String cityonid = etxtCityOnID.getText().toString();
                if (!TextUtils.isEmpty(cityonid)){
                    etxtCityOnID.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_teal, 0, 0, 0);
                }

                String cityasonresi = etxtCityResidence.getText().toString();
                if (!TextUtils.isEmpty(cityasonresi)){
                    etxtCityResidence.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_skyline_teal, 0, 0, 0);
                }

                String dob = etxtDOB.getText().toString();
                if (!TextUtils.isEmpty(dob)){
                    etxtDOB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_birthday_cake_teal, 0, 0, 0);
                }

                etxtProfileEmail.setText(user_email);
                etxtProfileName.setText(full_name);
                etxtCityResidence.setText(user_city_residence);
                etxtCityOnID.setText(user_city_on_id);
                etxtDOB.setText(user_dob);
                txtProfileMobile.setText(user_mobile);

                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
