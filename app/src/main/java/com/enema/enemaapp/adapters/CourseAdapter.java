package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.CourseData;
import com.enema.enemaapp.models.WishListData;
import com.enema.enemaapp.ui.activities.CourseDetailsActivity;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    List<CourseData> courseDataList;
    Context context;
    String get_course_id, wish = "0";
    Query applesQuery;

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
        courseViewHolder.txtRatingCount.setText("("+cd.getCourse_rating_count()+")");
        courseViewHolder.txtDiscountedPrice.setText(cd.getCourse_discount_price());
        courseViewHolder.txtActualPrice.setText(cd.getCourse_actual_price());
        Glide.with(this.context).load(cd.getCourse_image()).into(courseViewHolder.imgCourse);

        if (cd.getCourse_best_seller_status().equals("1")){
            courseViewHolder.imgBestSeller.setVisibility(View.VISIBLE);
        }

        if (cd.getCourse_best_seller_status().equals("0")){
            courseViewHolder.imgBestSeller.setVisibility(View.INVISIBLE);
        }



//        wishlistRef.child(user_mobile).child(username).child("WISHLIST_DATA").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot wishSnap : dataSnapshot.getChildren()){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){

            final String username = firebaseUser.getUid();
            final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            final DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
            assert user_mobile != null;

            applesQuery = wishlistRef.child(user_mobile).child(username)
                    .child("WISHLIST_DATA")
                    .orderByChild("course_id");

        }

//        applesQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot checkWishSnap : dataSnapshot.getChildren()) {
//
//               //     Toast.makeText(context, "" + checkWishSnap.child("course_id").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    int postion = Integer.parseInt(checkWishSnap.child("course_id").getValue().toString());
//               //     Toast.makeText(context, ""+courseViewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//        final SharedPreferences.Editor editor = wishlistPref.edit();

        courseViewHolder.imgWishListOnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null) {

                    if (wish.equals("0")) {
                        courseViewHolder.imgWishListOnCourse.setImageResource(R.drawable.ic_wish_teel);
                        wish = "1";
//                    editor.putString(cd.getCourse_id(), "1");
//                    editor.apply();
                        WishListData wishListData = new WishListData(cd.getCourse_name(),
                                cd.getCourse_image(),
                                cd.getCourse_rating(),
                                cd.getCourse_area(),
                                cd.getCourse_city(),
                                cd.getCourse_rating_count(),
                                cd.getCourse_id());
                        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        final String username = firebaseUser.getUid();
                        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                        final DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
                        assert user_mobile != null;
                        wishlistRef.child(user_mobile).child(username).child("WISHLIST_DATA").push().setValue(wishListData);


                    } else if (wish.equals("1")) {
                        courseViewHolder.imgWishListOnCourse.setImageResource(R.drawable.ic_wish_empty);
                        wish = "0";
//                    editor.putString(cd.getCourse_id(), "0");
//                    editor.apply();
                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }else {
                    Toast.makeText(context, "You must login to add this course in wishlist", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(registerIntent);
                }
            }
        });


        courseViewHolder.cardCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                boolean laptop, pendriveReq, cameraReq, notebookReq;
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

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCourseName, txtRatingCount, txtDiscountedPrice, txtActualPrice;
        public ImageView imgCourse, imgBestSeller;
        public RatingBar ratingCourse;
        public CardView cardCourse;
        public ImageView imgWishListOnCourse;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            ratingCourse = itemView.findViewById(R.id.ratingCourse);
            txtRatingCount = itemView.findViewById(R.id.txtRatingCount);
            txtDiscountedPrice = itemView.findViewById(R.id.txtDiscountedPrice);
            txtActualPrice = itemView.findViewById(R.id.txtActualPrice);
            imgCourse = itemView.findViewById(R.id.imgCourse);
            imgBestSeller = itemView.findViewById(R.id.imgBestSeller);
            cardCourse = itemView.findViewById(R.id.cardCourse);
            imgWishListOnCourse = (ImageView) itemView.findViewById(R.id.imgWishListOnCourse);
        }
    }
}
