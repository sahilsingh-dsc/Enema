package com.enema.enemaapp.ui.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.CourseAdapter;
import com.enema.enemaapp.adapters.SlotAdapter;
import com.enema.enemaapp.adapters.TimeSlotAdapter;
import com.enema.enemaapp.models.SlotModel;
import com.enema.enemaapp.models.TimeSlotData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    TextView txtCDName, txtCDAreaCity, txtCDRateCount, txtCDDiscountPrice, txtCDActualPrice;
    RatingBar ratingCD;
    ImageView imgCDImage;
    LinearLayout lhLaptop, lhPenDrive, lhCamera, lhNotebook;
    private List<SlotModel> slotModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        slotModelList = new ArrayList<>();
        slotModelList.clear();


        txtCDName = findViewById(R.id.txtCDName);
        txtCDAreaCity = findViewById(R.id.txtCDAreaCity);
        ratingCD = findViewById(R.id.ratingCD);
        txtCDRateCount = findViewById(R.id.txtCDRateCount);
        txtCDDiscountPrice = findViewById(R.id.txtCDDiscountPrice);
        txtCDActualPrice = findViewById(R.id.txtCDActualPrice);
        imgCDImage = findViewById(R.id.imgCDImage);
        lhLaptop = findViewById(R.id.lhLaptop);
        lhPenDrive = findViewById(R.id.lhPenDrive);
        lhCamera = findViewById(R.id.lhCamera);
        lhNotebook = findViewById(R.id.lhNotebook);


        getCourseDetails();

    }

    private void getCourseDetails(){

        Bundle courseBundle = getIntent().getExtras();
        assert courseBundle != null;
        String course_name = courseBundle.getString("course_name");
        String course_area = courseBundle.getString("course_area");
        String course_city = courseBundle.getString("course_city");
        String course_rating = courseBundle.getString("course_rating");
        String course_rating_count = courseBundle.getString("course_rating_count");
        String course_actual_price = courseBundle.getString("course_actual_price");
        String course_discount_price = courseBundle.getString("course_discount_price");
        String course_image = courseBundle.getString("course_image");
        boolean laptopReq = courseBundle.getBoolean("laptopReq");
        boolean pendriveReq = courseBundle.getBoolean("pendriveReq");
        boolean cameraReq = courseBundle.getBoolean("cameraReq");
        boolean notebookReq = courseBundle.getBoolean("notebookReq");
        int course_id = courseBundle.getInt("course_id");
        Toast.makeText(this, ""+course_id, Toast.LENGTH_SHORT).show();

        txtCDName.setText(course_name);
        txtCDAreaCity.setText(String.format("%s, %s", course_area, course_city));
        assert course_rating != null;
        ratingCD.setRating(Float.parseFloat(course_rating));
        txtCDRateCount.setText(course_rating_count);
        txtCDActualPrice.setText(course_actual_price);
        txtCDDiscountPrice.setText(course_discount_price);
        if (laptopReq){
            lhLaptop.setVisibility(View.VISIBLE);

        } else{
            lhLaptop.setVisibility(View.GONE);
        }

        if (pendriveReq){
            lhPenDrive.setVisibility(View.VISIBLE);

        } else{
            lhPenDrive.setVisibility(View.GONE);
        }

        if (cameraReq){
            lhCamera.setVisibility(View.VISIBLE);

        } else{
            lhCamera.setVisibility(View.GONE);
        }
        if (notebookReq){
            lhNotebook.setVisibility(View.VISIBLE);

        } else{
            lhNotebook.setVisibility(View.GONE);
        }

        Glide.with(CourseDetailsActivity.this).load(course_image).into(imgCDImage);

        getAllSlot(course_id);

    }

    private void getAllSlot(int course_id){

        final RecyclerView recyclerSlot = findViewById(R.id.recyclerSlot);
        final RecyclerView.Adapter[] slotAdapter = new RecyclerView.Adapter[1];
        recyclerSlot.hasFixedSize();
        recyclerSlot.setLayoutManager((new LinearLayoutManager(
                CourseDetailsActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false)));

        DatabaseReference courseDetailsRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");
        courseDetailsRef.child(String.valueOf(course_id)).child("COURSE_SLOT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                slotModelList.clear();
                String key = (String) dataSnapshot.getKey();
                Toast.makeText(CourseDetailsActivity.this, ""+key, Toast.LENGTH_SHORT).show();
                for (DataSnapshot slotSnap : dataSnapshot.getChildren()){

                    String slot_date = (String) slotSnap.child("slot_date").getValue();
                    String slot_day = (String) slotSnap.child("slot_day").getValue();
                    String slot_month = (String) slotSnap.child("slot_month").getValue();
                    String slot_year = (String) slotSnap.child("slot_year").getValue();
                    String slot_id = (String) slotSnap.child("slot_id").getValue();
                    Toast.makeText(CourseDetailsActivity.this, ""+slot_date, Toast.LENGTH_SHORT).show();

                    SlotModel slotModel = new SlotModel(slot_date, slot_day, slot_month, slot_year, slot_id);
                    slotModelList.add(slotModel);

                }

                slotAdapter[0] = new SlotAdapter(slotModelList,CourseDetailsActivity.this);
                recyclerSlot.setAdapter(slotAdapter[0]);
                slotAdapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }




}
