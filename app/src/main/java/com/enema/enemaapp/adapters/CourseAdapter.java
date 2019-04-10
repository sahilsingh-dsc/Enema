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
import com.enema.enemaapp.models.CourseData;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<CourseData> courseDataList;
    private Context context;

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
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder courseViewHolder, int i) {

        CourseData cd = courseDataList.get(i);

        courseViewHolder.txtCourseName.setText(cd.getCourse_name());
        courseViewHolder.ratingCourse.setRating(Float.parseFloat(cd.getCourse_rating()));
        courseViewHolder.txtRatingCount.setText(cd.getCourse_rating_count());
        courseViewHolder.txtDiscountedPrice.setText(cd.getCourse_discount_price());
        courseViewHolder.txtActualPrice.setText(cd.getCourse_actual_price());
        Glide.with(this.context).load(cd.getCourse_image()).into(courseViewHolder.imgCourse);

        if (cd.getCourse_best_seller_status().equals("1")){
            courseViewHolder.imgBestSeller.setVisibility(View.VISIBLE);
        }

        if (cd.getCourse_best_seller_status().equals("0")){
            courseViewHolder.imgBestSeller.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return courseDataList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCourseName, txtRatingCount, txtDiscountedPrice, txtActualPrice;
        public ImageView imgCourse, imgBestSeller;
        public RatingBar ratingCourse;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            ratingCourse = itemView.findViewById(R.id.ratingCourse);
            txtRatingCount = itemView.findViewById(R.id.txtRatingCount);
            txtDiscountedPrice = itemView.findViewById(R.id.txtDiscountedPrice);
            txtActualPrice = itemView.findViewById(R.id.txtActualPrice);
            imgCourse = itemView.findViewById(R.id.imgCourse);
            imgBestSeller = itemView.findViewById(R.id.imgBestSeller);


        }
    }
}
