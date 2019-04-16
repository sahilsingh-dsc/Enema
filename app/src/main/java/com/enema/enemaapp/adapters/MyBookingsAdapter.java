package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.MyBookingData;

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

        MyBookingData bookingData = myBookingDataList.get(i);
        myBookingsViewHolder.txtMyBookingCourseName.setText(bookingData.getBooking_course_name());
        myBookingsViewHolder.txtMyBookingLocation.setText(bookingData.getBooking_course_location());
        myBookingsViewHolder.txtMyBookingRateCount.setText(bookingData.getBooking_rating_count());
        myBookingsViewHolder.ratingMyBookingRating.setRating(Float.parseFloat(bookingData.getBooking_rating()));
        Glide.with(this.context).load(bookingData.getBooking_image()).into(myBookingsViewHolder.imgMyBooking);

    }

    @Override
    public int getItemCount() {
        return myBookingDataList.size();
    }

    public class MyBookingsViewHolder extends RecyclerView.ViewHolder {

        TextView txtMyBookingCourseName, txtMyBookingLocation, txtMyBookingRateCount;
        ImageView imgMyBooking;
        RatingBar ratingMyBookingRating;

        public MyBookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMyBookingCourseName = itemView.findViewById(R.id.txtMyBookingCourseName);
            txtMyBookingLocation = itemView.findViewById(R.id.txtMyBookingLocation);
            txtMyBookingRateCount = itemView.findViewById(R.id.txtMyBookingRateCount);
            imgMyBooking = itemView.findViewById(R.id.imgMyBooking);
            ratingMyBookingRating = itemView.findViewById(R.id.ratingMyBookingRating);

        }
    }
}
