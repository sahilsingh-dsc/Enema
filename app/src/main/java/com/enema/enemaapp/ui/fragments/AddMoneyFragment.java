package com.enema.enemaapp.ui.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
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
import com.enema.enemaapp.ui.activities.BookCourseActivity;
import com.enema.enemaapp.ui.activities.PaymentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class AddMoneyFragment extends Fragment {

    View view;
    private AlertDialog loadingDialog;
    private String walletcurrent_balance = "0";
    EditText etxtAddAmount;
    String name, email;

    public AddMoneyFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_add_money, container, false);

        etxtAddAmount = view.findViewById(R.id.etxtAddAmount);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        loadingDialog.show();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        walletRef.child(user_mobile).child(username).child("WALLET_DATA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                walletcurrent_balance = (String) dataSnapshot.child("current_wallet_balance").getValue();
                TextView txtCurrentBalance = view.findViewById(R.id.txtCurrentBalance);
                txtCurrentBalance.setText(walletcurrent_balance);
                loadingDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference profiledataRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        profiledataRef.child(user_mobile).child(username).child("PROFILE_DATA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = (String) dataSnapshot.child("full_name").getValue();
                email = (String) dataSnapshot.child("user_email").getValue();
                loadingDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnAddMoney = view.findViewById(R.id.btnAddMoney);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                String phone = user_mobile.replace("+91", "");
                String amount = etxtAddAmount.getText().toString().trim();
                if (TextUtils.isEmpty(amount) || !TextUtils.isDigitsOnly(amount)){
                    Toast.makeText(getContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    return;
                }

                if (walletcurrent_balance == null){
                    walletcurrent_balance = "0";
                }
                int curr_bal = Integer.parseInt(walletcurrent_balance);
                int toadd = Integer.parseInt(amount);
                int updated_bal = curr_bal+toadd;

                paymentDetails(email, phone, amount, name, updated_bal, curr_bal);

            }
        });

        return view;
    }




    private void paymentDetails(String email, String phone, String amount, String name, int updated_bal, int curr_bal) {
        loadingDialog.show();
        String purpose = getString(R.string.add_money);
        Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
        Bundle paymentBundle = new Bundle();
        paymentBundle.putString("email", email);
        paymentBundle.putString("phone", phone);
        paymentBundle.putString("purpose", purpose);
        paymentBundle.putString("amount", amount);
        paymentBundle.putString("name", name);
        paymentBundle.putInt("updated_bal",updated_bal);
        paymentBundle.putInt("curr_bal", curr_bal);
        if (email == null){
            Toast.makeText(getContext(), "Please Complete you profile before making payment.", Toast.LENGTH_SHORT).show();
            return;
        }
        paymentIntent.putExtras(paymentBundle);
        if (phone != null && amount != null && name != null){
            startActivity(paymentIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).finish();
            }
        }
        loadingDialog.dismiss();
    }

}
