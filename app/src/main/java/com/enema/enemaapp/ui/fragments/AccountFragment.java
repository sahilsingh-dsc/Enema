package com.enema.enemaapp.ui.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.ui.activities.AboutActivity;
import com.enema.enemaapp.ui.activities.AccountProfileActivity;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.enema.enemaapp.ui.activities.MyBookingsActivity;
import com.enema.enemaapp.ui.activities.PrivacyPolicyActivity;
import com.enema.enemaapp.ui.activities.ReferAndEarnActivity;
import com.enema.enemaapp.ui.activities.SupportActivity;
import com.enema.enemaapp.ui.activities.WalletActivity;
import com.enema.enemaapp.utils.SendMail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class AccountFragment extends Fragment {

    View view;
    private FirebaseUser firebaseUser;
    String user_id;
    public AccountFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }

        TextView txtAccountProfile, txtAccountMyBookings, txtAccountWallet, txtAccountRefAndEarn, txtAccountPrivacyPolicy, txtAccountSupport, txtAccountAbout;

        txtAccountProfile = view.findViewById(R.id.txtAccountProfile);
        txtAccountProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null){
                    Intent registerIntent = new Intent(getActivity(), AccountProfileActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }else {
                    Intent registerIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }

            }
        });

        txtAccountMyBookings = view.findViewById(R.id.txtAccountMyBookings);
        txtAccountMyBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null){
                    Intent registerIntent = new Intent(getActivity(), MyBookingsActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }else {
                    Intent registerIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }

            }
        });

        txtAccountWallet = view.findViewById(R.id.txtAccountWallet);
        txtAccountWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null){
                    Intent registerIntent = new Intent(getActivity(), WalletActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }else {
                    Intent registerIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }

            }
        });

        txtAccountRefAndEarn = view.findViewById(R.id.txtAccountRefAndEarn);
        txtAccountRefAndEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null){
                    Intent referandearnIntent = new Intent(getActivity(), ReferAndEarnActivity.class);
                    startActivity(referandearnIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }else {
                    Intent registerIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(registerIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }

            }
        });

        txtAccountPrivacyPolicy = view.findViewById(R.id.txtAccountPrivacyPolicy);
        txtAccountPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent aboutIntent = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(aboutIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });

        txtAccountSupport = view.findViewById(R.id.txtAccountSupport);
        txtAccountSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent aboutIntent = new Intent(getActivity(), SupportActivity.class);
                startActivity(aboutIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });

        txtAccountAbout = view.findViewById(R.id.txtAccountAbout);
        txtAccountAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });

        TextView txtAccountTitle = view.findViewById(R.id.txtAccountTitle);
        txtAccountTitle.setText("Hello");

       if (firebaseUser !=null){
           DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
           nameRef.child("USER_PROFILE").child(user_id).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   String name = (String) dataSnapshot.child("full_name").getValue();
                   TextView txtAccountTitle = view.findViewById(R.id.txtAccountTitle);
                   txtAccountTitle.setText("Hello, "+name);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }


        Button btnLogout = view.findViewById(R.id.btnLogout);
       if (firebaseUser == null){
           btnLogout.setVisibility(View.GONE);
       }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                }
                assert builder != null;
                builder.setMessage("Are you sure, you want to logout ?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        getActivity().finish();
                        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("auth_state", false);
                        editor.apply();

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

}
