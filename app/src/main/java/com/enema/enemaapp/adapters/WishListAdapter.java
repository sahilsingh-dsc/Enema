package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.WishListData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private List<WishListData> wishListDataList;
    private Context context;
    private String user_id;

    public WishListAdapter(List<WishListData> wishListDataList, Context context) {
        this.wishListDataList = wishListDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_list, viewGroup, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }

        return new WishListViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder wishListViewHolder, int i) {

        final WishListData wishListData = wishListDataList.get(i);
        wishListViewHolder.txtWLCourseName.setText(wishListData.getCourse_name());
        Glide.with(this.context).load(wishListData.getCourse_image()).into(wishListViewHolder.imgWLCourseImg);
        wishListViewHolder.txtWLCityLocation.setText(String.format("%s, %s", wishListData.getCourse_area(), wishListData.getCourse_city()));
        wishListViewHolder.ratingWLCourse.setRating(Float.parseFloat(wishListData.getCourse_rating()));
        wishListViewHolder.txtWLRateCount.setText(wishListData.getCourse_rating_count());
        wishListViewHolder.imgWishChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    builder = new AlertDialog.Builder(context);
                }
                assert builder != null;
                builder.setMessage("Are you sure, want to remove this course from list ?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        Query applesQuery = FirebaseDatabase.getInstance().getReference("USER_DATA").child("WISHLIST_DATA")
                                .child(user_id)
                                .orderByChild("course_id")
                                .equalTo(wishListData.getCourse_id());

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
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return wishListDataList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {

        TextView txtWLCourseName, txtWLCityLocation, txtWLRateCount;
        ImageView imgWLCourseImg, imgWishChecker;
        RatingBar ratingWLCourse;
        ConstraintLayout constrainMyWL;

        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);

            txtWLCourseName = (TextView) itemView.findViewById(R.id.txtWLCourseName);
            txtWLCityLocation = (TextView) itemView.findViewById(R.id.txtWLCityLocation);
            txtWLRateCount = (TextView) itemView.findViewById(R.id.txtWLRateCount);
            imgWLCourseImg = (ImageView) itemView.findViewById(R.id.imgWLCourseImg);
            imgWishChecker = (ImageView) itemView.findViewById(R.id.imgWishChecker);
            ratingWLCourse = (RatingBar) itemView.findViewById(R.id.ratingWLCourse);
            constrainMyWL = (ConstraintLayout) itemView.findViewById(R.id.constrainMyWL);



        }
    }
}
