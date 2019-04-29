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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.RecCourseData;
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

import java.util.List;

public class RecCourseAdapter extends RecyclerView.Adapter<RecCourseAdapter.RecCourseViewHolder> {

    List<RecCourseData> recCourseDataList;
    Context context;
    String wish = "0";
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("USER_DATA");

    public RecCourseAdapter(List<RecCourseData> recCourseDataList, Context context) {
        this.recCourseDataList = recCourseDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecCourseAdapter.RecCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.courses_arount_you_list_item, viewGroup, false);
        return new RecCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecCourseAdapter.RecCourseViewHolder recCourseViewHolder, int i) {

        final RecCourseData recCourseData = recCourseDataList.get(i);

        recCourseViewHolder.txtCourseName.setText(recCourseData.getCourse_name());
        recCourseViewHolder.ratingCourse.setRating(Float.parseFloat(recCourseData.getCourse_rating()));
        recCourseViewHolder.txtRatingCount.setText(String.format("(%s)", recCourseData.getCourse_rating_count()));
        recCourseViewHolder.txtDiscountedPrice.setText(recCourseData.getCourse_discount_price());
        recCourseViewHolder.txtActualPrice.setText(recCourseData.getCourse_actual_price());
        Glide.with(this.context).load(recCourseData.getCourse_image()).into(recCourseViewHolder.imgCourse);

        if (recCourseData.getCourse_best_seller_status().equals("1")) {
            recCourseViewHolder.imgBestSeller.setVisibility(View.VISIBLE);
        }

        if (recCourseData.getCourse_best_seller_status().equals("0")) {
            recCourseViewHolder.imgBestSeller.setVisibility(View.INVISIBLE);
        }


        recCourseViewHolder.cardCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent courseDetailsIntent = new Intent(context, CourseDetailsActivity.class);
                final Bundle courseBundle = new Bundle();
                courseBundle.putString("course_id", recCourseData.getCourse_id());
                final DatabaseReference courseReqRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");

                courseReqRef.child(recCourseData.getCourse_id()).child("COURSE_REQUIRED").addValueEventListener(new ValueEventListener() {
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
                courseDetailsRef.child(recCourseData.getCourse_id()).addValueEventListener(new ValueEventListener() {
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

        if (firebaseUser != null) {
            String user_id = firebaseUser.getUid();
            String user_mobile = firebaseUser.getPhoneNumber();
            assert user_mobile != null;
            Query query = FirebaseDatabase.getInstance().getReference("USER_DATA")
                    .child(user_mobile).child(user_id).child("WISHLIST_DATA")
                    .orderByChild("course_id")
                    .equalTo(recCourseData.getCourse_id());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot wishSnap : dataSnapshot.getChildren()){
                        String course = (String) wishSnap.child("course_id").getValue();
                        assert course != null;
                        if (course.equals(recCourseData.getCourse_id())){
                            recCourseViewHolder.imgWishListOnCourse.setImageResource(R.drawable.ic_wish_teel);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        recCourseViewHolder.imgWishListOnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null) {
                    String username = firebaseUser.getUid();
                    String user_mobile = firebaseUser.getPhoneNumber();
                    assert user_mobile != null;

                    final Query applesQuery = wishlistRef.child(user_mobile).child(username)
                            .child("WISHLIST_DATA")
                            .orderByChild("course_id")
                            .equalTo(recCourseData.getCourse_id());

                    if (wish.equals("0")){
                        recCourseViewHolder.imgWishListOnCourse.setImageResource(R.drawable.ic_wish_teel);
                        wish = "1";
                        WishListData wishListData = new WishListData(recCourseData.getCourse_name(),
                                recCourseData.getCourse_image(),
                                recCourseData.getCourse_rating(),
                                recCourseData.getCourse_area(),
                                recCourseData.getCourse_city(),
                                recCourseData.getCourse_rating_count(),
                                recCourseData.getCourse_id());
                        wishlistRef.child(user_mobile).child(username).child("WISHLIST_DATA").push().setValue(wishListData);



                    }else if (wish.equals("1")){
                        recCourseViewHolder.imgWishListOnCourse.setImageResource(R.drawable.ic_wish_empty);
                        wish = "0";
                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }else {
                    Intent registerIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(registerIntent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return recCourseDataList.size();
    }

    public class RecCourseViewHolder extends RecyclerView.ViewHolder {

        TextView txtCourseName, txtRatingCount, txtDiscountedPrice, txtActualPrice;
        ImageView imgCourse, imgBestSeller;
        RatingBar ratingCourse;
        CardView cardCourse;
        ImageView imgWishListOnCourse;

        public RecCourseViewHolder(@NonNull View itemView) {
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
