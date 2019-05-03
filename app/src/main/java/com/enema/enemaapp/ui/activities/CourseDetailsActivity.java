package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    TextView txtCDName, txtCDAreaCity, txtCDRateCount, txtCDDiscountPrice, txtCDActualPrice, txtTotalImages;
    RatingBar ratingCD;
    ImageView imgCDImage;
    LinearLayout lhLaptop, lhPenDrive, lhCamera, lhNotebook;
    List<SlotModel> slotModelList;
    List<TimeSlotData> timeSlotDataList;
    Bundle courseBundle;
    DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseBundle = getIntent().getExtras();
        assert courseBundle != null;
        final String course_id = courseBundle.getString("course_id");


        slotModelList = new ArrayList<>();
        slotModelList.clear();
        timeSlotDataList = new ArrayList<>();
        timeSlotDataList.clear();

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
        txtTotalImages = findViewById(R.id.txtTotalImages);

        ImageView imgCourseToHome = findViewById(R.id.imgCourseToHome);
        imgCourseToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        txtTotalImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert course_id != null;
                courseRef.child(course_id).child("COURSE_GALLERY").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String img1 = (String) dataSnapshot.child("0").getValue();
                        String img2 = (String) dataSnapshot.child("1").getValue();
                        String img3 = (String) dataSnapshot.child("2").getValue();
                        String img4 = (String) dataSnapshot.child("3").getValue();

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseDetailsActivity.this);
                        LayoutInflater inflater = CourseDetailsActivity.this.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.course_gallery_layout, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog galleryDialog = dialogBuilder.create();
                        ImageView imgGal1 = dialogView.findViewById(R.id.imgGal1);
                        Glide.with(CourseDetailsActivity.this).load(img1).into(imgGal1);
                        ImageView imgGal2 = dialogView.findViewById(R.id.imgGal2);
                        Glide.with(CourseDetailsActivity.this).load(img2).into(imgGal2);
                        ImageView imgGal3 = dialogView.findViewById(R.id.imgGal3);
                        Glide.with(CourseDetailsActivity.this).load(img3).into(imgGal3);
                        ImageView imgGal4 = dialogView.findViewById(R.id.imgGal4);
                        Glide.with(CourseDetailsActivity.this).load(img4).into(imgGal4);
                        ImageView imgCloseGallery = dialogView.findViewById(R.id.imgCloseGallery);
                        imgCloseGallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                galleryDialog.dismiss();
                            }
                        });
                        galleryDialog.show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


       getCourseDetails(course_id);

       Button btnBookCourse = findViewById(R.id.btnBookCourse);
        btnBookCourse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (firebaseUser != null){

                   String course_name = courseBundle.getString("course_name");
                   String course_area = courseBundle.getString("course_area");
                   String course_city = courseBundle.getString("course_city");
                   String course_rating = courseBundle.getString("course_rating");
                   String course_rating_count = courseBundle.getString("course_rating_count");
                   String course_actual_price = courseBundle.getString("course_actual_price");
                   String course_discount_price = courseBundle.getString("course_discount_price");
                   String course_image = courseBundle.getString("course_image");

                   Intent courseDetailsIntent = new Intent(CourseDetailsActivity.this, BookCourseActivity.class);
                   Bundle sendcourseBundle = new Bundle();
                   sendcourseBundle.putString("course_name", course_name);
                   sendcourseBundle.putString("course_area", course_area);
                   sendcourseBundle.putString("course_city", course_city);
                   sendcourseBundle.putString("course_rating", course_rating);
                   sendcourseBundle.putString("course_rating_count", course_rating_count);
                   sendcourseBundle.putString("course_actual_price", course_actual_price);
                   sendcourseBundle.putString("course_discount_price", course_discount_price);
                   sendcourseBundle.putString("course_image", course_image);
                   sendcourseBundle.putString("course_id", course_id);
                   courseDetailsIntent.putExtras(sendcourseBundle);
                   startActivity(courseDetailsIntent);

               }else {
                   gotoLogin();
               }
           }
       });

    }



    private void getCourseDetails(String course_id){

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

        courseRef.child(course_id).child("NOTES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String slot_note = (String) dataSnapshot.child("SLOT_NOTE").getValue();
                TextView txtSlotNote = findViewById(R.id.txtSlotNote);
                txtSlotNote.setText(slot_note);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    private void getAllSlot(String course_id){

        final RecyclerView recyclerSlot = findViewById(R.id.recyclerSlot);
        final RecyclerView.Adapter[] slotAdapter = new RecyclerView.Adapter[1];
        recyclerSlot.hasFixedSize();
        recyclerSlot.setLayoutManager((new LinearLayoutManager(
                CourseDetailsActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false)));

        final RecyclerView recyclerTime = findViewById(R.id.recyclerTime);
        final RecyclerView.Adapter[] timeAdapter = new RecyclerView.Adapter[1];
        recyclerTime.hasFixedSize();
        recyclerTime.setLayoutManager((new LinearLayoutManager(
                CourseDetailsActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false)));


        DatabaseReference courseDetailsRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");
        courseDetailsRef.child(String.valueOf(course_id)).child("COURSE_SLOT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                slotModelList.clear();
                for (DataSnapshot slotSnap : dataSnapshot.getChildren()){

                    String time_from = null;
                    String time_to =null;
                    String time_slot_limit = null;

                    String slot_date = (String) slotSnap.child("slot_date").getValue();
                    String slot_day = (String) slotSnap.child("slot_day").getValue();
                    String slot_month = (String) slotSnap.child("slot_month").getValue();
                    String slot_year = (String) slotSnap.child("slot_year").getValue();
                    String slot_id = (String) slotSnap.child("slot_id").getValue();

                    for (DataSnapshot snapshot : slotSnap.child("TIME_SLOT").getChildren()){
                       time_from = (String) snapshot.child("time_from").getValue();
                       time_to = (String) snapshot.child("time_to").getValue();
                       time_slot_limit = (String) snapshot.child("time_slot_limit").getValue();
                    }

                    SlotModel slotModel = new SlotModel(slot_date, slot_day, slot_month, slot_year, slot_id, time_from, time_to);
                    TimeSlotData timeSlotData = new TimeSlotData(time_from, time_to, time_slot_limit);
                    slotModelList.add(slotModel);
                    timeSlotDataList.add(timeSlotData);
                }

                slotAdapter[0] = new SlotAdapter(slotModelList,CourseDetailsActivity.this);
                recyclerSlot.setAdapter(slotAdapter[0]);
                slotAdapter[0].notifyDataSetChanged();
                timeAdapter[0] = new TimeSlotAdapter(timeSlotDataList,CourseDetailsActivity.this);
                recyclerTime.setAdapter(timeAdapter[0]);
                timeAdapter[0].notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gotoLogin(){

        startActivity(new Intent(CourseDetailsActivity.this, LoginActivity.class));

    }

}
