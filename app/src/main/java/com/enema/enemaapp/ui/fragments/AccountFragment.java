package com.enema.enemaapp.ui.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    View view;

    public AccountFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();

        TextView txtLoggedMobileNumber = view.findViewById(R.id.txtLoggedMobileNumber);

        txtLoggedMobileNumber.setText("User id : "+username+"\n"+"Mobile No : "+firebaseUser.getPhoneNumber());

        Button btnLogout = view.findViewById(R.id.btnLogout);
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

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

}
