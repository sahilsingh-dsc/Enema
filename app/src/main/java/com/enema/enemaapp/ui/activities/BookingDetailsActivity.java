package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;

public class BookingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);


        Button btnCancelBooking = findViewById(R.id.btnCancelBooking);
        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBooking();
            }
        });

        ImageView imgBkgDtlToMyBkg = findViewById(R.id.imgBkgDtlToMyBkg);
        imgBkgDtlToMyBkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        TextView txtBookingHelpSection = findViewById(R.id.txtBookingHelpSection);
        txtBookingHelpSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faqIntent = new Intent(BookingDetailsActivity.this, HelpActivity.class);
                startActivity(faqIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        TextView txtBookingFAQ = findViewById(R.id.txtBookingFAQ);
        txtBookingFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faqIntent = new Intent(BookingDetailsActivity.this, SupportActivity.class);
                startActivity(faqIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        TextView txtBookingReview = findViewById(R.id.txtBookingReview);
        txtBookingReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(BookingDetailsActivity.this, SupportActivity.class);
                startActivity(reviewIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        getBookingDtls();

    }

    private void cancelBooking(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bottom_sheet_cancel_bookingg, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void getBookingDtls(){
        Bundle bookingBundle = getIntent().getExtras();
        assert bookingBundle != null;

        String booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count, wallet_tnx_id, booking_session,
                booking_daydate,
                booking_time,
                booking_for,
                coupon_code,
                course_fee,
                course_provider_no,
                course_id;


        booking_image = bookingBundle.getString("booking_image");
        booking_course_name = bookingBundle.getString("booking_course_name");
        booking_course_location = bookingBundle.getString("booking_course_location");
        booking_rating = bookingBundle.getString("booking_rating");
        booking_rating_count = bookingBundle.getString("booking_rating_count");
        wallet_tnx_id = bookingBundle.getString("wallet_tnx_id");
        booking_session = bookingBundle.getString("booking_session");
        booking_daydate = bookingBundle.getString("booking_daydate");
        booking_time = bookingBundle.getString("booking_time");
        booking_for = bookingBundle.getString("booking_for");
        coupon_code = bookingBundle.getString("coupon_code");
        course_fee = bookingBundle.getString("course_fee");
        course_provider_no = bookingBundle.getString("course_provider_no");

        ImageView imgMyBooking;
        TextView txtMyBookingCourseName, txtMyBookingLocation, txtMyBookingRateCount, txtBookingSession, txtBookingClassDate, txtBookingTimeSlot, txtBookingForName, txtCouponCode, txtCourseFeeAmt, txtCourseProviderNumber;
        RatingBar ratingMyBookingRating;

        imgMyBooking = (ImageView) findViewById(R.id.imgMyBooking);
        txtMyBookingCourseName = findViewById(R.id.txtMyBookingCourseName);
        txtMyBookingLocation = findViewById(R.id.txtMyBookingLocation);
        txtMyBookingRateCount = findViewById(R.id.txtMyBookingRateCount);
        txtBookingSession = findViewById(R.id.txtBookingSession);
        txtBookingClassDate = findViewById(R.id.txtBookingClassDate);
        txtBookingTimeSlot = findViewById(R.id.txtBookingTimeSlot);
        txtBookingForName = findViewById(R.id.txtBookingForName);
        txtCouponCode = findViewById(R.id.txtCouponCode);
        txtCourseFeeAmt =findViewById(R.id.txtCourseFeeAmt);
        txtCourseProviderNumber =findViewById(R.id.txtCourseProviderNumber);
        ratingMyBookingRating = findViewById(R.id.ratingMyBookingRating);

        Glide.with(BookingDetailsActivity.this).load(booking_image).into(imgMyBooking);
        txtMyBookingCourseName.setText(booking_course_name);
        txtMyBookingLocation.setText(booking_course_location);
        txtMyBookingRateCount.setText(booking_rating_count);
        txtBookingSession.setText(booking_session);
        txtBookingClassDate.setText(booking_daydate);
        txtBookingTimeSlot.setText(booking_time);
        txtBookingForName.setText(booking_for);
        txtCouponCode.setText(coupon_code);
        txtCourseFeeAmt.setText(course_fee);
        txtCourseProviderNumber.setText(course_provider_no);
        ratingMyBookingRating.setRating(Float.parseFloat(booking_rating));

    }

}
