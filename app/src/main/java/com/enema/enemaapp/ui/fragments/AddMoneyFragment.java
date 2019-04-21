package com.enema.enemaapp.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.ui.activities.OtpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class AddMoneyFragment extends Fragment {

    View view;
    private AlertDialog loadingDialog;

    public AddMoneyFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_money, container, false);


        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();


        loadingDialog.show();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        walletRef.child(user_mobile).child(username).child("WALLET_DATA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wallet_current_balance = (String) dataSnapshot.child("wallet_current_balance").getValue();
                TextView txtCurrentBalance = view.findViewById(R.id.txtCurrentBalance);
                txtCurrentBalance.setText(wallet_current_balance);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button btnAddMoney = view.findViewById(R.id.btnAddMoney);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etxtAddAmount = view.findViewById(R.id.etxtAddAmount);
                String amount = etxtAddAmount.getText().toString().trim();

//                if (TextUtils.isEmpty(amount) || !TextUtils.isDigitsOnly(amount)){
//                    Toast.makeText(getContext(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                addMoneyToWallet();

            }
        });



        return view;
    }

    private void addMoneyToWallet(){
        loadingDialog.show();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        walletRef.child(user_mobile).child(username).child("WALLET_DATA").child("current_wallet_balance").setValue("50");
        loadingDialog.dismiss();
    }

}
