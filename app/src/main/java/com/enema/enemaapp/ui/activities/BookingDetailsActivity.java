package com.enema.enemaapp.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.utils.CancelDtl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingDetailsActivity extends AppCompatActivity {


    String cancel_reason, booking_for;
    private String booking_course_name, user_email;
    FirebaseUser firebaseUser;
    String user_id;
    String _user_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }

        Bundle bookingBundle = getIntent().getExtras();
        assert bookingBundle != null;
        String booking_status = bookingBundle.getString("booking_status");
        Button btnCancelBooking = findViewById(R.id.btnCancelBooking);
        booking_course_name = bookingBundle.getString("booking_course_name");

        if (booking_status.equals("cancelled")){

            btnCancelBooking.setVisibility(View.INVISIBLE);

        }

        DatabaseReference mobileRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        mobileRef.child("USER_PROFILE").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _user_mobile = (String) dataSnapshot.child("user_mobile").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        emailRef.child("USER_PROFILE").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_email = (String) dataSnapshot.child("user_email").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookingDetailsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBooking();
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
                Bundle bookingBundle = getIntent().getExtras();
                assert bookingBundle != null;

                String booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count, course_id;

                booking_image = bookingBundle.getString("booking_image");
                booking_course_name = bookingBundle.getString("booking_course_name");
                booking_course_location = bookingBundle.getString("booking_course_location");
                booking_rating = bookingBundle.getString("booking_rating");
                booking_rating_count = bookingBundle.getString("booking_rating_count");
                course_id = bookingBundle.getString("course_id");

                Intent reviewIntent = new Intent(BookingDetailsActivity.this, ReviewActivity.class);

                Bundle reviewBundle = new Bundle();
                reviewBundle.putString("booking_image", booking_image);
                reviewBundle.putString("booking_course_name", booking_course_name);
                reviewBundle.putString("booking_course_location", booking_course_location);
                reviewBundle.putString("booking_rating", booking_rating);
                reviewBundle.putString("booking_rating_count", booking_rating_count);
                reviewBundle.putString("course_id", course_id);
                reviewIntent.putExtras(reviewBundle);

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
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText txtOtherReason = dialogView.findViewById(R.id.txtOtherReason);
        ImageView imgCloseCancelBooking = dialogView.findViewById(R.id.imgCloseCancelBooking);
        RadioButton rbCIP = dialogView.findViewById(R.id.rbCIP);
        rbCIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_reason = "Change in plan";
                txtOtherReason.setVisibility(View.GONE);
            }
        });

        RadioButton rbFBD = dialogView.findViewById(R.id.rbFBD);
        rbFBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_reason = "Found a better deal";
                txtOtherReason.setVisibility(View.GONE);
            }
        });

        RadioButton rbBM = dialogView.findViewById(R.id.rbBM);
        rbBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_reason = "Booking by mistake";
                txtOtherReason.setVisibility(View.GONE);
            }
        });

        RadioButton rbOtherReason = dialogView.findViewById(R.id.rbOtherReason);
        rbOtherReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_reason = "other";
                txtOtherReason.setVisibility(View.VISIBLE);
            }
        });

        Button btnCancelBooking = dialogView.findViewById(R.id.btnCancelBooking);
        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancel_reason.equals("other")){
                    cancel_reason = txtOtherReason.getText().toString();
                }

                AlertDialog.Builder builder = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    builder = new AlertDialog.Builder(BookingDetailsActivity.this);
                }
                assert builder != null;
                builder.setMessage("Are you sure, want to cancel this booking ?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bookingBundle = getIntent().getExtras();
                        assert bookingBundle != null;
                        final String course_id = bookingBundle.getString("course_id");
                        String booking_id = bookingBundle.getString("booking_id");

                        DatabaseReference cancelbookingRef = FirebaseDatabase.getInstance().getReference("USER_DATA")
                                .child("USERS_BOOKINGS").child(user_id);
                        assert booking_id != null;
                        cancelbookingRef.child(booking_id).child("booking_status").setValue("cancelled");
                        DatabaseReference cancelRef = FirebaseDatabase.getInstance().getReference("APP_DATA");
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        Calendar calendar = Calendar.getInstance();
                        String current = df.format(calendar.getTime());
                        CancelDtl cancelDtl = new CancelDtl(current, cancel_reason, course_id, booking_course_name, user_email, _user_mobile, booking_for);
                        cancelRef.child("BOOKINGS_CANCEL").push().setValue(cancelDtl);
                        dialog.dismiss();
                        Toast.makeText(BookingDetailsActivity.this, "Booking cancelled Successfully, Our team will contact you soon for refund process.", Toast.LENGTH_LONG).show();
                    }
                });
                final AlertDialog alertdialog = builder.create();
                alertdialog.show();



            }
        });

        imgCloseCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void getBookingDtls(){
        Bundle bookingBundle = getIntent().getExtras();
        assert bookingBundle != null;

        String booking_image, booking_course_location, booking_rating, booking_rating_count, wallet_tnx_id, booking_session,
                booking_daydate,
                booking_time,
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
