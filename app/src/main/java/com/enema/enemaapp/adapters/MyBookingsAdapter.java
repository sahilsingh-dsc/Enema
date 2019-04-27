package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private List<MyBookingData> myBookingDataList;
    private Context context;

    public MyBookingsAdapter(List<MyBookingData> myBookingDataList, Context context) {
        this.myBookingDataList = myBookingDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyBookingsAdapter.MyBookingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_booking_item, viewGroup, false);

        return new MyBookingsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingsAdapter.MyBookingsViewHolder myBookingsViewHolder, int i) {

        final MyBookingData bookingData = myBookingDataList.get(i);
        myBookingsViewHolder.txtMyBookingCourseName.setText(bookingData.getBooking_course_name());
        myBookingsViewHolder.txtMyBookingLocation.setText(bookingData.getBooking_course_location());
        myBookingsViewHolder.txtMyBookingRateCount.setText(bookingData.getBooking_rating_count());
        Float rating= Float.parseFloat(bookingData.getBooking_rating());
        Toast.makeText(context, ""+bookingData.getBooking_rating(), Toast.LENGTH_SHORT).show();
        myBookingsViewHolder.ratingMyBookingRating.setRating(rating);
        Glide.with(this.context).load(bookingData.getBooking_image()).into(myBookingsViewHolder.imgMyBooking);
        final String wallet_tnx_id = bookingData.getWallet_tnx_id();
        if (bookingData.getBooking_status().equals("cancelled")){
            myBookingsViewHolder.imgCancelledStatus.setVisibility(View.VISIBLE);
        }
        if (bookingData.getBooking_status().equals("none")){
            myBookingsViewHolder.imgCancelledStatus.setVisibility(View.GONE);
        }

        myBookingsViewHolder.constrainMyBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

    }

    @Override
    public int getItemCount() {
        return myBookingDataList.size();
    }

    public class MyBookingsViewHolder extends RecyclerView.ViewHolder {

        TextView txtMyBookingCourseName, txtMyBookingLocation, txtMyBookingRateCount;
        ImageView imgMyBooking, imgCancelledStatus;
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
            imgCancelledStatus = itemView.findViewById(R.id.imgCancelledStatus);

        }
    }
}
