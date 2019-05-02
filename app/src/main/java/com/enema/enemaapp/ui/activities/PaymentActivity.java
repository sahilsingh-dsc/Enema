package com.enema.enemaapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.MyBookingData;
import com.enema.enemaapp.models.WalletTxnData;
import com.enema.enemaapp.utils.SendMail;
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

    private String walletcurrent_balance, email, phone, amount, purpose, tag;
    private int balance;
    int child_count;
    String tnx_id;
    Bundle paymentBundle;
    FirebaseUser firebaseUser;
    String user_id;
//    String

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }



        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        walletRef
                .child("WALLET_DATA")
                .child("PREVIOUS_TNX_DATA")
                .child(user_id)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                child_count = (int) dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaymentActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

        paymentBundle = getIntent().getExtras();
        assert paymentBundle != null;

        email = paymentBundle.getString("email");
        phone = paymentBundle.getString("phone");
        amount = paymentBundle.getString("amount");
        purpose = paymentBundle.getString("purpose");
        String name = paymentBundle.getString("name");
        int balance = paymentBundle.getInt("updated_bal");
        tag = paymentBundle.getString("tag");
        walletcurrent_balance = String.valueOf(paymentBundle.getInt("curr_bal"));

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
                //commitBalance(balance);
                updateTxn(response);
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
                updateTxn(reason);

                if(tag.equals("booking")){
                    startActivity(new Intent(PaymentActivity.this, MyBookingsActivity.class));

                    finish();
                }else {
                    startActivity(new Intent(PaymentActivity.this, MyBookingsActivity.class));
                    finish();
                }

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
        startActivity(new Intent(PaymentActivity.this, MyBookingsActivity.class));
        finish();
    }

    private void updateTxn(String reason){
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        int update_count = child_count+1;
        tnx_id = "ETXN"+update_count;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        WalletTxnData walletTxnData = new WalletTxnData(walletcurrent_balance, amount, tnx_id, purpose, reason, timeStamp);
        walletRef
                .child("WALLET_DATA")
                .child("PREVIOUS_TNX_DATA")
                .child(user_id)
                .push().setValue(walletTxnData);

        updateBooking();

    }

    public void updateBooking(){

        String booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count, wallet_tnx_id, booking_session,
                booking_daydate,
                booking_time,
                booking_for,
                coupon_code,
                course_fee,
                course_provider_no,
                course_id, booking_id, booking_status;


        booking_image = paymentBundle.getString("booking_image");
        booking_course_name = paymentBundle.getString("booking_course_name");
        booking_course_location = paymentBundle.getString("booking_course_location");
        booking_rating = paymentBundle.getString("booking_rating");
        booking_rating_count = paymentBundle.getString("booking_rating_count");
        course_id = paymentBundle.getString("course_id");
        booking_session = paymentBundle.getString("booking_session");
        booking_daydate = paymentBundle.getString("booking_daydate");
        booking_time = paymentBundle.getString("booking_time");
        booking_for = paymentBundle.getString("booking_for");
        coupon_code = paymentBundle.getString("coupon_code");
        course_fee = paymentBundle.getString("course_fee");
        booking_id = paymentBundle.getString("booking_id");
        booking_status = paymentBundle.getString("booking_status");

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        MyBookingData myBookingData = new MyBookingData(

                booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count, tnx_id, booking_session,
                booking_daydate,
                booking_time,
                booking_for,
                coupon_code,
                course_fee,
                "0000000000",
                course_id, booking_id, booking_status

        );
        bookingsRef.child("USERS_BOOKINGS").child(user_id).push().setValue(myBookingData);

        String subject = "Regarding your latest course";
        String name = booking_for;
        String message = "Hello, " +name+
                "\n" +
                "Thanks for purchasing our course "+booking_course_name+"\n"+
                "Course Fee :"+" ₹"+course_fee+"\n"+
                "Transaction Id : "+tnx_id+"\n"+
                "Booking For : "+booking_for+"\n"+
                "Booking Status : "+booking_status;

        SendMail sm = new SendMail(PaymentActivity.this, email, subject, message);
        sm.execute();

    }

}
