package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.CourseData;
import com.enema.enemaapp.ui.activities.CourseDetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    List<CourseData> courseDataList;
    Context context;

    public CourseAdapter(List<CourseData> courseDataList, Context context) {
        this.courseDataList = courseDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.courses_arount_you_list_item, viewGroup, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseAdapter.CourseViewHolder courseViewHolder, int i) {

        final CourseData cd = courseDataList.get(i);

        courseViewHolder.txtCourseName.setText(cd.getCourse_name());
        courseViewHolder.ratingCourse.setRating(Float.parseFloat(cd.getCourse_rating()));
        courseViewHolder.txtRatingCount.setText(String.format("(%s)", cd.getCourse_rating_count()));
        courseViewHolder.txtDiscountedPrice.setText(cd.getCourse_discount_price());
        courseViewHolder.txtActualPrice.setText(cd.getCourse_actual_price());
        Glide.with(this.context).load(cd.getCourse_image()).into(courseViewHolder.imgCourse);

        if (cd.getCourse_best_seller_status().equals("1")){
            courseViewHolder.imgBestSeller.setVisibility(View.VISIBLE);
        }

        if (cd.getCourse_best_seller_status().equals("0")){
            courseViewHolder.imgBestSeller.setVisibility(View.INVISIBLE);
        }

        courseViewHolder.cardCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent courseDetailsIntent = new Intent(context, CourseDetailsActivity.class);
                final Bundle courseBundle = new Bundle();
                courseBundle.putString("course_id", cd.getCourse_id());
                final DatabaseReference courseReqRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");

                courseReqRef.child(cd.getCourse_id()).child("COURSE_REQUIRED").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        boolean laptopReq = (boolean) dataSnapshot.child("laptop").getValue();
                        boolean pendriveReq = (boolean) dataSnapshot.child("pendrive").getValue();
                        boolean cameraReq = (boolean) dataSnapshot.child("camera").getValue();
                        boolean notebookReq = (boolean) dataSnapshot.child("notebook").getValue();

                        courseBundle.putBoolean("laptopReq", laptopReq);
                        courseBundle.putBoolean("pendriveReq", pendriveReq);
                        courseBundle.putBoolean("cameraReq", cameraReq);
                        courseBundle.putBoolean("notebookReq", notebookReq);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference courseDetailsRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");
                courseDetailsRef.child(cd.getCourse_id()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String course_name = (String) dataSnapshot.child("course_name").getValue();
                        String course_area = (String) dataSnapshot.child("course_area").getValue();
                        String course_city = (String) dataSnapshot.child("course_city").getValue();
                        String course_rating = (String) dataSnapshot.child("course_rating").getValue();
                        String course_rating_count = (String) dataSnapshot.child("course_rating_count").getValue();
                        String course_actual_price = (String) dataSnapshot.child("course_actual_price").getValue();
                        String course_discount_price = (String) dataSnapshot.child("course_discount_price").getValue();
                        String course_image = (String) dataSnapshot.child("course_image").getValue();

                        courseBundle.putString("course_name", course_name);
                        courseBundle.putString("course_area", course_area);
                        courseBundle.putString("course_city", course_city);
                        courseBundle.putString("course_rating", course_rating);
                        courseBundle.putString("course_rating_count", course_rating_count);
                        courseBundle.putString("course_actual_price", course_actual_price);
                        courseBundle.putString("course_discount_price", course_discount_price);
                        courseBundle.putString("course_image", course_image);
                        courseDetailsIntent.putExtras(courseBundle);
                        context.startActivity(courseDetailsIntent);

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
        return courseDataList.size();
    }

    public void setfilter(List<CourseData> newlist) {
        courseDataList = new ArrayList<>();
        courseDataList.addAll(newlist);
        notifyDataSetChanged();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView txtCourseName, txtRatingCount, txtDiscountedPrice, txtActualPrice;
        ImageView imgCourse, imgBestSeller;
        RatingBar ratingCourse;
        CardView cardCourse;
        ImageView imgWishListOnCourse;

        CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            ratingCourse = itemView.findViewById(R.id.ratingCourse);
            txtRatingCount = itemView.findViewById(R.id.txtRatingCount);
            txtDiscountedPrice = itemView.findViewById(R.id.txtDiscountedPrice);
            txtActualPrice = itemView.findViewById(R.id.txtActualPrice);
            imgCourse = itemView.findViewById(R.id.imgCourse);
            imgBestSeller = itemView.findViewById(R.id.imgBestSeller);
            cardCourse = itemView.findViewById(R.id.cardCourse);
            imgWishListOnCourse = itemView.findViewById(R.id.imgWishListOnCourse);
        }
    }
}
