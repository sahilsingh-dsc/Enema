package com.enema.enemaapp.adapters;

import android.content.Context;
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
import com.enema.enemaapp.ui.activities.MyBookingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        MyBookingData bookingData = myBookingDataList.get(i);
        myBookingsViewHolder.txtMyBookingCourseName.setText(bookingData.getBooking_course_name());
        myBookingsViewHolder.txtMyBookingLocation.setText(bookingData.getBooking_course_location());
        myBookingsViewHolder.txtMyBookingRateCount.setText(bookingData.getBooking_rating_count());
        Float rating= Float.parseFloat(bookingData.getBooking_rating());
        Toast.makeText(context, ""+bookingData.getBooking_rating(), Toast.LENGTH_SHORT).show();
        myBookingsViewHolder.ratingMyBookingRating.setRating(rating);
        Glide.with(this.context).load(bookingData.getBooking_image()).into(myBookingsViewHolder.imgMyBooking);
        final String wallet_tnx_id = bookingData.getWallet_tnx_id();
        Toast.makeText(context , ""+wallet_tnx_id, Toast.LENGTH_SHORT).show();

        myBookingsViewHolder.constrainMyBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                final String username = firebaseUser.getUid();
                String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
                assert user_mobile != null;
                bookingsRef.child(user_mobile).child(username).child("USERS_BOOKINGS_DATA").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String loca_name = (String) dataSnapshot.child("booking_course_location").getValue();
                        Toast.makeText(context, ""+loca_name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return myBookingDataList.size();
    }

    public class MyBookingsViewHolder extends RecyclerView.ViewHolder {

        TextView txtMyBookingCourseName, txtMyBookingLocation, txtMyBookingRateCount;
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


        }
    }
}
