package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.SlotModel;
import com.enema.enemaapp.utils.SlotUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookCourseActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    TextView txtCDNameBook, txtCDAreaCity, txtCDRateCount, txtCDDiscountPrice, txtCDActualPrice, txtOriginalFee, txtOfferFee, txtDiscountAmt, txtTotalCourseFee;
    RatingBar ratingCD;
    EditText txtBookingForFullName;
    ImageView imgCDImageBook;
    String user_email, current_wallet_balance, course_name;
    Bundle courseBundle;
    TextView txtCouponCodeBook;
    int offer_amount, coupon_discount, coupon_net_discount, final_discount, total_payable;
    String coupon_code, coupon_value, coupon_type;
    String slot_session ;
    String user_mobile;
    String slot_daydate ;
    String time_slot ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_course);

        txtCDNameBook = findViewById(R.id.txtCDNameBook);
        txtCDAreaCity = findViewById(R.id.txtCDAreaCityBook);
        ratingCD = findViewById(R.id.ratingCDBook);
        txtCDRateCount = findViewById(R.id.txtCDRateCountBook);
        txtCDDiscountPrice = findViewById(R.id.txtCDDiscountPriceBook);
        txtCDActualPrice = findViewById(R.id.txtCDActualPriceBook);
        imgCDImageBook = findViewById(R.id.imgCDImageBook);
        txtOriginalFee = findViewById(R.id.txtOriginalFee);
        txtOfferFee = findViewById(R.id.txtOfferFee);
        txtDiscountAmt = findViewById(R.id.txtDiscountAmt);
        txtBookingForFullName = findViewById(R.id.txtBookingForFullName);
        txtTotalCourseFee = findViewById(R.id.txtTotalCourseFee);

        courseBundle = getIntent().getExtras();
        assert courseBundle != null;
        course_name = courseBundle.getString("course_name");

        txtCouponCodeBook = findViewById(R.id.txtCouponCodeBook);
        txtCouponCodeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent couponIntent = new Intent(BookCourseActivity.this, CouponActivity.class);
                startActivity(couponIntent);
            }
        });

        // firebase data fetech...
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser !=null){
            final String username = firebaseUser.getUid();
            user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
            assert user_mobile != null;
            walletRef.child(user_mobile).child(username).child("WALLET_DATA").addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    current_wallet_balance = (String) dataSnapshot.child("current_wallet_balance").getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
            emailRef.child(user_mobile).child(username).child("PROFILE_DATA").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user_email = (String) dataSnapshot.child("user_email").getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(BookCourseActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });
            DatabaseReference couponRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child(user_mobile).child(username);
            couponRef.child("COUPON_STATE").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    coupon_code = (String) dataSnapshot.child("coupon_code").getValue();
                    coupon_value = (String) dataSnapshot.child("coupon_value").getValue();
                    coupon_type = (String) dataSnapshot.child("coupon_type").getValue();
                    txtCouponCodeBook.setText(coupon_code);
                    if (coupon_type.equals("flat")){
                        String offer_amt = txtOfferFee.getText().toString();
                        offer_amount = Integer.parseInt(offer_amt);
                        coupon_discount = Integer.parseInt(coupon_value);
                        final_discount = coupon_discount;
                        total_payable = offer_amount - coupon_discount;
                        txtDiscountAmt.setText(""+final_discount);
                        txtTotalCourseFee.setText(""+total_payable);

                    }

                    if (coupon_type.equals("percent")){
                        String offer_amt = txtOfferFee.getText().toString();
                        offer_amount = Integer.parseInt(offer_amt);
                        coupon_discount = Integer.parseInt(coupon_value);
                        int cal = offer_amount*coupon_discount;
                        final_discount = cal/100;
                        total_payable = offer_amount - final_discount;
                        txtDiscountAmt.setText(""+final_discount);
                        txtTotalCourseFee.setText(""+total_payable);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            // firebase fetch ends here...

            DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
            bookingsRef.child(user_mobile).child(username).child("slots_util").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    slot_session = (String) dataSnapshot.child("booking_session").getValue();
                    slot_daydate = (String) dataSnapshot.child("booking_daydate").getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference bookingsRef3 = FirebaseDatabase.getInstance().getReference("USER_DATA");
            bookingsRef3.child(user_mobile).child(username).child("time_util").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    time_slot = (String) dataSnapshot.child("booking_time").getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else{
            Intent registerIntent = new Intent(BookCourseActivity.this, LoginActivity.class);
            startActivity(registerIntent);
        }




        getBookData();

        Button btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FirebaseAuth.getInstance().getCurrentUser() != null){

                    String mobile  = user_mobile.replace("+91", "");
                    String name = txtBookingForFullName.getText().toString();
                    if (TextUtils.isEmpty(name)){
                        Toast.makeText(BookCourseActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent paymentIntent = new Intent(BookCourseActivity.this, PaymentActivity.class);
                    Bundle paymentbundle =  new Bundle();


                    //Course Details
                    String course_name = courseBundle.getString("course_name");
                    String course_area = courseBundle.getString("course_area");
                    String course_city = courseBundle.getString("course_city");
                    String course_rating = courseBundle.getString("course_rating");
                    String course_rating_count = courseBundle.getString("course_rating_count");
                    String course_actual_price = courseBundle.getString("course_actual_price");
                    String course_discount_price = courseBundle.getString("course_discount_price");
                    String course_image = courseBundle.getString("course_image");
                    String course_id = courseBundle.getString("course_id");

                    paymentbundle.putString("booking_image", course_image);
                    paymentbundle.putString("booking_course_name", course_name);
                    paymentbundle.putString("booking_course_location", course_area+", "+course_city);
                    paymentbundle.putString("booking_rating", course_rating);
                    paymentbundle.putString("booking_rating_count", course_rating_count);
                    paymentbundle.putString("course_id", course_id);
                    //paymentbundle.putString("wallet_tnx_id", bookingData.getWallet_tnx_id());
                    paymentbundle.putString("booking_session", slot_session);
                    paymentbundle.putString("booking_daydate", slot_daydate);
                    paymentbundle.putString("booking_time", time_slot);

                    paymentbundle.putString("booking_for", name);
                    paymentbundle.putString("coupon_code", coupon_code);
                    if (total_payable <=0){
                        String offer_amt = txtOfferFee.getText().toString();
                        offer_amount = Integer.parseInt(offer_amt);
                        paymentbundle.putString("course_fee", String.valueOf(offer_amount));
                    }else {
                        paymentbundle.putString("course_fee", String.valueOf(total_payable));
                    }
                 //   paymentbundle.putString("course_provider_no", );
                    paymentbundle.putString("booking_id", course_id);
                    paymentbundle.putString("booking_status","pending");

                    ///

                    paymentbundle.putString("email", user_email);
                    paymentbundle.putString("phone", mobile);
                    paymentbundle.putString("purpose", course_name);
                    if (total_payable <=0){
                        String offer_amt = txtOfferFee.getText().toString();
                        offer_amount = Integer.parseInt(offer_amt);
                        paymentbundle.putString("amount", String.valueOf(offer_amount));
                    }else {
                        paymentbundle.putString("amount", String.valueOf(total_payable));
                    }
                    paymentbundle.putString("name", name);
                    paymentbundle.putString("updated_bal", current_wallet_balance);
                    paymentbundle.putString("tag", "booking");
                    if (user_email == null){
                        Toast.makeText(BookCourseActivity.this, "Please Complete you profile before making payment.", Toast.LENGTH_SHORT).show();
                        return;
                    }else {

                        paymentIntent.putExtras(paymentbundle);
                        startActivity(paymentIntent);
                    }

                }else {
                    Intent registerIntent = new Intent(BookCourseActivity.this, LoginActivity.class);
                    startActivity(registerIntent);
                }
            }
        });

    }



    private void getBookData(){


        String course_area = courseBundle.getString("course_area");
        String course_city = courseBundle.getString("course_city");
        String course_rating = courseBundle.getString("course_rating");
        String course_rating_count = courseBundle.getString("course_rating_count");
        String course_actual_price = courseBundle.getString("course_actual_price");
        String course_discount_price = courseBundle.getString("course_discount_price");
        String course_image = courseBundle.getString("course_image");

        txtCDNameBook.setText(course_name);
        txtCDAreaCity.setText(String.format("%s, %s", course_area, course_city));
        assert course_rating != null;
        txtCDRateCount.setText(course_rating_count);
        txtCDActualPrice.setText(course_actual_price);
        txtCDDiscountPrice.setText(course_discount_price);
        txtOriginalFee.setText(course_actual_price);
        txtOfferFee.setText(course_discount_price);
        txtTotalCourseFee.setText(course_discount_price);
        Glide.with(BookCourseActivity.this).load(course_image).into(imgCDImageBook);

    }

}
