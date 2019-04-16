package com.enema.enemaapp.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AccountProfileActivity extends AppCompatActivity {

    private int editState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        getUserData();

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(AccountProfileActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        ImageView imgProfileToAccount = findViewById(R.id.imgProfileToAccount);
        imgProfileToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });



        final TextView txtEditProfile = findViewById(R.id.txtEditProfile);
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editState == 0){
                    txtEditProfile.setText("");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        txtEditProfile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_black_24dp, 0, 0, 0);

                    }
                    editProfile();
                    editState = 1;

                } else {
                    txtEditProfile.setText(getString(R.string.edit_profile));
                    txtEditProfile.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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
            }
        });

        lvFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMale.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_grey));
                imgFemale.setImageDrawable(getResources().getDrawable(R.drawable.ic_woman));
                txtMale.setTextColor(getResources().getColor(R.color.colorBlack));
                txtFemale.setTextColor(getResources().getColor(R.color.colorCyanTeal));
            }
        });

    }

    private void updateProfile(){

        EditText etxtProfileEmail, etxtProfileName, etxtCityResidence, etxtCityOnID, etxtDOB;
        LinearLayout lhGenderBox, lvMale, lvFemale;
        ImageView imgMale, imgFemale;
        TextView txtMale, txtFemale;

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

        etxtProfileEmail.setEnabled(false);
        etxtProfileName.setEnabled(false);
        etxtCityResidence.setEnabled(false);
        etxtCityOnID.setEnabled(false);
        etxtDOB.setEnabled(false);
        lhGenderBox.setVisibility(View.GONE);
        lvMale.setEnabled(false);
        lvFemale.setEnabled(false);

        etxtProfileEmail.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtProfileName.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtCityResidence.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtCityOnID.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        etxtDOB.setBackground(getResources().getDrawable(R.drawable.border_nothing));
        lhGenderBox.setBackground(getResources().getDrawable(R.drawable.border_nothing));

        

    }

    private void getUserData(){

    }


}
