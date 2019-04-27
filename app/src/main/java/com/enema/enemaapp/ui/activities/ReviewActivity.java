package com.enema.enemaapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.ReviewData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getReviewCourseDtl();

        Button btnSubmitReview = findViewById(R.id.btnSubmitReview);
        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RatingBar ratingReview = findViewById(R.id.ratingReview);
                EditText txtReviewComment = findViewById(R.id.txtReviewComment);
                float reiew_rating = ratingReview.getRating();
                String review_comment = txtReviewComment.getText().toString();

                submitReview(reiew_rating, review_comment);
            }
        });

        ImageView imgReviewToBkgDtl = findViewById(R.id.imgReviewToBkgDtl);
        imgReviewToBkgDtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

    }

    public void getReviewCourseDtl(){
        Bundle reviewBundle = getIntent().getExtras();
        assert reviewBundle != null;

        String booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count;

        booking_image = reviewBundle.getString("booking_image");
        booking_course_name = reviewBundle.getString("booking_course_name");
        booking_course_location = reviewBundle.getString("booking_course_location");
        booking_rating = reviewBundle.getString("booking_rating");
        booking_rating_count = reviewBundle.getString("booking_rating_count");

        ImageView imgMyBookingReview = findViewById(R.id.imgMyBookingReview);
        TextView txtMyBookingCourseNameReview = findViewById(R.id.txtMyBookingCourseNameReview);
        TextView txtMyBookingLocationReview = findViewById(R.id.txtMyBookingLocationReview);
        RatingBar ratingMyBookingRatingReview = findViewById(R.id.ratingMyBookingRatingReview);
        TextView txtMyBookingRateCountReview = findViewById(R.id.txtMyBookingRateCountReview);

        Glide.with(ReviewActivity.this).load(booking_image).into(imgMyBookingReview);
        txtMyBookingCourseNameReview.setText(booking_course_name);
        txtMyBookingLocationReview.setText(booking_course_location);
        txtMyBookingRateCountReview.setText(booking_rating_count);
        assert booking_rating != null;
        ratingMyBookingRatingReview.setRating(Float.parseFloat(booking_rating));

    }

    public void submitReview(final float review_rating, final String review_comment){

        Bundle reviewBundle = getIntent().getExtras();
        assert reviewBundle != null;
        final String course_id = reviewBundle.getString("course_id");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String authId = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("REVIEW_DATA");
        assert course_id != null;
        reviewRef.child(course_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(authId)){
                    Toast.makeText(ReviewActivity.this, "Your review is already submitted", Toast.LENGTH_SHORT).show();
                }else {

                    ReviewData reviewData = new ReviewData(review_rating, review_comment);
                    DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("REVIEW_DATA");
                    reviewRef.child(course_id).child(authId).setValue(reviewData);
                    Toast.makeText(ReviewActivity.this, "You Review Submitted Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
