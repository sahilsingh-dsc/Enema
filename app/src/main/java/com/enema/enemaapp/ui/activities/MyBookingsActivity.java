package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.CourseAdapter;
import com.enema.enemaapp.adapters.MyBookingsAdapter;
import com.enema.enemaapp.models.MyBookingData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    private ImageView imgMyBkgToAccount;
    private List<MyBookingData> myBookingDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_booking_item);

        myBookingDataList = new ArrayList<>();
        myBookingDataList.clear();



        ConstraintLayout constraintMyBooking = findViewById(R.id.constrainMyBooking);
        constraintMyBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingdetailsIntent = new Intent(MyBookingsActivity.this, BookingDetailsActivity.class);
                startActivity(bookingdetailsIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //finish();
            }
        });

//        imgMyBkgToAccount = findViewById(R.id.imgMyBkgToAccount);
//        imgMyBkgToAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                finish();
//            }
//        });

       // getMyBookings();

    }

    private void getMyBookings() {

        final RecyclerView recyclerMyBookings;
        final RecyclerView.Adapter[] myBookingsAdapter = new RecyclerView.Adapter[1];
        recyclerMyBookings = findViewById(R.id.recyclerMyBookings);
        recyclerMyBookings.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMyBookings.setLayoutManager(mLayoutManager);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();

        DatabaseReference mybookingsRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child(username);

        mybookingsRef.child("USERS_BOOKINGS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myBookingDataList.clear();

                for (DataSnapshot bookingSnap : dataSnapshot.getChildren()) {

                    String booking_image = (String) bookingSnap.child("booking_image").getValue();
                    String booking_course_name = (String) bookingSnap.child("booking_course_name").getValue();
                    String booking_course_location = (String) bookingSnap.child("booking_course_location").getValue();
                    String booking_rating = (String) bookingSnap.child("booking_rating").getValue();
                    String booking_rating_count = (String) bookingSnap.child("booking_rating_count").getValue();

                    Toast.makeText(MyBookingsActivity.this, "" + booking_image, Toast.LENGTH_SHORT).show();

                    MyBookingData bookingData = new MyBookingData(booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count);
                    myBookingDataList.add(bookingData);

                }



                myBookingsAdapter[0] = new MyBookingsAdapter(myBookingDataList, MyBookingsActivity.this);
                recyclerMyBookings.setAdapter(myBookingsAdapter[0]);
                myBookingsAdapter[0].notifyDataSetChanged();

                //loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}