package com.enema.enemaapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.WalletTxnData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class PaymentActivity extends AppCompatActivity {

    private String walletcurrent_balance, email, phone, amount, purpose;
    private int balance;
    int child_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        walletRef.child(user_mobile).child(username)
                .child("WALLET_DATA")
                .child("PREVIOUS_TNX_DATA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                child_count = (int) dataSnapshot.getChildrenCount();
                Toast.makeText(PaymentActivity.this, ""+child_count, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaymentActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

        Bundle paymentBundle = getIntent().getExtras();
        assert paymentBundle != null;
        email = paymentBundle.getString("email");
        phone = paymentBundle.getString("phone");
        amount = paymentBundle.getString("amount");
        purpose = paymentBundle.getString("purpose");
        String name = paymentBundle.getString("name");
        int balance = paymentBundle.getInt("updated_bal");
        walletcurrent_balance = String.valueOf(paymentBundle.getInt("curr_bal"));
        Toast.makeText(this, ""+balance, Toast.LENGTH_SHORT).show();
        callInstamojoPay(email, phone, amount, purpose, name);


    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
                commitBalance(balance);
                updateTxn(response);
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
                updateTxn(reason);
                startActivity(new Intent(PaymentActivity.this, WalletActivity.class));
                finish();
            }
        };
    }

    private void commitBalance(int commit_balance){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        walletRef.child(user_mobile).child(username).child("WALLET_DATA").child("current_wallet_balance").setValue(""+commit_balance);
        Toast.makeText(this, "Money Added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PaymentActivity.this, WalletActivity.class));
        finish();
    }

    private void updateTxn(String reason){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        int update_count = child_count+1;
        String tnx_id = "ETXN"+update_count;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        WalletTxnData walletTxnData = new WalletTxnData(walletcurrent_balance, amount, tnx_id, purpose, reason, timeStamp);
        walletRef.child(user_mobile).child(username)
                .child("WALLET_DATA")
                .child("PREVIOUS_TNX_DATA").push().setValue(walletTxnData);
    }

}
