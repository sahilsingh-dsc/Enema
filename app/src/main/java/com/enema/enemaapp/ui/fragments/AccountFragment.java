package com.enema.enemaapp.ui.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.ui.activities.AboutActivity;
import com.enema.enemaapp.ui.activities.AccountProfileActivity;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.enema.enemaapp.ui.activities.MyBookingsActivity;
import com.enema.enemaapp.ui.activities.RegisterActivity;
import com.enema.enemaapp.ui.activities.WalletActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    View view;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public AccountFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);

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
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                    Intent registerIntent = new Intent(getActivity(), ReferAndEarnFragment.class);
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

        txtAccountPrivacyPolicy = view.findViewById(R.id.txtAccountPrivacyPolicy);
        txtAccountPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtAccountSupport = view.findViewById(R.id.txtAccountSupport);
        txtAccountSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        assert firebaseUser != null;
//        final String username = firebaseUser.getUid();
//
//        TextView txtLoggedMobileNumber = view.findViewById(R.id.txtLoggedMobileNumber);
//
//        txtLoggedMobileNumber.setText("User id : "+username+"\n"+"Mobile No : "+firebaseUser.getPhoneNumber());
//
//        Button btnLogout = view.findViewById(R.id.btnLogout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
//                }
//                assert builder != null;
//                builder.setMessage("Are you sure, you want to logout ?");
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                        firebaseAuth.signOut();
//                        startActivity(new Intent(getActivity(), LoginActivity.class));
//                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        getActivity().finish();
//
//                    }
//                });
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });

        return view;
    }

    private void authStatus(){
        FirebaseAuth.getInstance().getCurrentUser();
    }

}
