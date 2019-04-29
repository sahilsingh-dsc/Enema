package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.MyBookingData;
import com.enema.enemaapp.ui.activities.BookingDetailsActivity;
import com.enema.enemaapp.ui.activities.CourseDetailsActivity;
import com.enema.enemaapp.ui.activities.MyBookingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.MyBookingsViewHolder> {

    View v;
    private List<MyBookingData> myBookingDataList;
    private Context context;

    public MyBookingsAdapter(List<MyBookingData> myBookingDataList, Context context) {
        this.myBookingDataList = myBookingDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyBookingsAdapter.MyBookingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_booking_item, viewGroup, false);

        return new MyBookingsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyBookingsAdapter.MyBookingsViewHolder myBookingsViewHolder, int i) {

        final MyBookingData bookingData = myBookingDataList.get(i);
        myBookingsViewHolder.txtMyBookingCourseName.setText(bookingData.getBooking_course_name());
        myBookingsViewHolder.txtMyBookingLocation.setText(bookingData.getBooking_course_location());
        myBookingsViewHolder.txtMyBookingRateCount.setText(bookingData.getBooking_rating_count());
        float rating= Float.parseFloat(bookingData.getBooking_rating());
        myBookingsViewHolder.ratingMyBookingRating.setRating(rating);
        Glide.with(this.context).load(bookingData.getBooking_image()).into(myBookingsViewHolder.imgMyBooking);
        final String wallet_tnx_id = bookingData.getWallet_tnx_id();
        if (bookingData.getBooking_status().equals("cancelled")){
            myBookingsViewHolder.txtBookingStatusOut.setText("CANCELLED");
            myBookingsViewHolder.txtBookingStatusOut.setTextColor(Color.RED);
        }
        if (bookingData.getBooking_status().equals("booked")){
            myBookingsViewHolder.txtBookingStatusOut.setText("BOOKED");
            myBookingsViewHolder.txtBookingStatusOut.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }
        if (bookingData.getBooking_status().equals("pending")){
            myBookingsViewHolder.txtBookingStatusOut.setText("PENDING");
            myBookingsViewHolder.txtBookingStatusOut.setTextColor(Color.DKGRAY);
        }

        myBookingsViewHolder.constrainMyBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bookingData.getBooking_status().equals("pending")){
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialogView = inflater.inflate(R.layout.pending_alert, null);
                    dialogBuilder.setView(dialogView);
                    final AlertDialog galleryDialog = dialogBuilder.create();
                    final EditText etxtPaymentId = dialogView.findViewById(R.id.etxtPaymentId);
                    Button btnVerifyBooking = dialogView.findViewById(R.id.btnVerifyBooking);
                    btnVerifyBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String paymentId = etxtPaymentId.getText().toString();
                            if (TextUtils.isEmpty(paymentId)){
                                Toast.makeText(context, "Please enter a paymentId", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (paymentId.length() != 20){
                                Toast.makeText(context, "Please enter a valid paymentId", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            assert firebaseUser != null;
                            final String username = firebaseUser.getUid();
                            String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                            assert user_mobile != null;
                            DatabaseReference cancelbookingRef = FirebaseDatabase.getInstance().getReference("USER_DATA")
                                    .child(user_mobile).child(username).child("USERS_BOOKINGS_DATA");
                            cancelbookingRef.child(bookingData.getBooking_id()).child("booking_payment_id").setValue(paymentId);
                            Toast.makeText(context, "Booking Verified", Toast.LENGTH_SHORT).show();
                            cancelbookingRef = FirebaseDatabase.getInstance().getReference("USER_DATA")
                                    .child(user_mobile).child(username).child("USERS_BOOKINGS_DATA");
                            cancelbookingRef.child(bookingData.getBooking_id()).child("booking_status").setValue("booked");
                            galleryDialog.dismiss();
                        }
                    });
                    galleryDialog.show();
                }else if (bookingData.getBooking_status().equals("booked")){
                    Intent bookingdetailsIntent = new Intent(context, BookingDetailsActivity.class);
                    Bundle bookingBundle = new Bundle();
                    bookingBundle.putString("booking_image", bookingData.getBooking_image());
                    bookingBundle.putString("booking_course_name", bookingData.getBooking_course_name());
                    bookingBundle.putString("booking_course_location", bookingData.getBooking_course_location());
                    bookingBundle.putString("booking_rating", bookingData.getBooking_rating());
                    bookingBundle.putString("booking_rating_count", bookingData.getBooking_rating_count());
                    bookingBundle.putString("course_id", bookingData.getCourse_id());
                    bookingBundle.putString("wallet_tnx_id", bookingData.getWallet_tnx_id());
                    bookingBundle.putString("booking_session", bookingData.getBooking_session());
                    bookingBundle.putString("booking_daydate", bookingData.getBooking_daydate());
                    bookingBundle.putString("booking_time", bookingData.getBooking_time());
                    bookingBundle.putString("booking_for", bookingData.getBooking_for());
                    bookingBundle.putString("coupon_code", bookingData.getCoupon_code());
                    bookingBundle.putString("course_fee", bookingData.getCourse_fee());
                    bookingBundle.putString("course_provider_no", bookingData.getCourse_provider_no());
                    bookingBundle.putString("booking_id", bookingData.getBooking_id());
                    bookingBundle.putString("booking_status",bookingData.getBooking_status());
                    bookingdetailsIntent.putExtras(bookingBundle);
                    context.startActivity(bookingdetailsIntent);
                }

                else if (bookingData.getBooking_status().equals("cancelled")){
                    Intent bookingdetailsIntent = new Intent(context, BookingDetailsActivity.class);
                    Bundle bookingBundle = new Bundle();
                    bookingBundle.putString("booking_image", bookingData.getBooking_image());
                    bookingBundle.putString("booking_course_name", bookingData.getBooking_course_name());
                    bookingBundle.putString("booking_course_location", bookingData.getBooking_course_location());
                    bookingBundle.putString("booking_rating", bookingData.getBooking_rating());
                    bookingBundle.putString("booking_rating_count", bookingData.getBooking_rating_count());
                    bookingBundle.putString("course_id", bookingData.getCourse_id());
                    bookingBundle.putString("wallet_tnx_id", bookingData.getWallet_tnx_id());
                    bookingBundle.putString("booking_session", bookingData.getBooking_session());
                    bookingBundle.putString("booking_daydate", bookingData.getBooking_daydate());
                    bookingBundle.putString("booking_time", bookingData.getBooking_time());
                    bookingBundle.putString("booking_for", bookingData.getBooking_for());
                    bookingBundle.putString("coupon_code", bookingData.getCoupon_code());
                    bookingBundle.putString("course_fee", bookingData.getCourse_fee());
                    bookingBundle.putString("course_provider_no", bookingData.getCourse_provider_no());
                    bookingBundle.putString("booking_id", bookingData.getBooking_id());
                    bookingBundle.putString("booking_status",bookingData.getBooking_status());
                    bookingdetailsIntent.putExtras(bookingBundle);
                    context.startActivity(bookingdetailsIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBookingDataList.size();
    }

    public class MyBookingsViewHolder extends RecyclerView.ViewHolder {

        TextView txtMyBookingCourseName, txtMyBookingLocation, txtMyBookingRateCount, txtBookingStatusOut;
        ImageView imgMyBooking;
        RatingBar ratingMyBookingRating;
        ConstraintLayout constrainMyBooking;

        public MyBookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMyBookingCourseName = itemView.findViewById(R.id.txtMyBookingCourseName);
            txtMyBookingLocation = itemView.findViewById(R.id.txtMyBookingLocation);
            txtMyBookingRateCount = itemView.findViewById(R.id.txtMyBookingRateCount);
            imgMyBooking = itemView.findViewById(R.id.imgMyBooking);
            ratingMyBookingRating = itemView.findViewById(R.id.ratingMyBookingRating);
            constrainMyBooking = itemView.findViewById(R.id.constrainMyBooking);
            txtBookingStatusOut = itemView.findViewById(R.id.txtBookingStatusOut);

        }
    }
}
