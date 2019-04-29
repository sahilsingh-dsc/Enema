package com.enema.enemaapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.CouponAdapter;
import com.enema.enemaapp.models.CouponData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CouponActivity extends AppCompatActivity {

    List<CouponData> couponDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        couponDataList = new ArrayList<>();
        couponDataList.clear();
        getAllCoupons();

        ImageView imgCouponToBkgCrs = findViewById(R.id.imgCouponToBkgCrs);
        imgCouponToBkgCrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    public void getAllCoupons(){

        final RecyclerView recyclerCoupon = findViewById(R.id.recyclerCoupon);
        final RecyclerView.Adapter[] couponAdapter = new RecyclerView.Adapter[1];
        recyclerCoupon.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCoupon.setLayoutManager(mLayoutManager);

        DatabaseReference supportRef = FirebaseDatabase.getInstance().getReference("APP_DATA");
        supportRef.child("COUPON_CODES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot couponSanp : dataSnapshot.getChildren()){
                    String coupon_code = (String) couponSanp.child("coupon_code").getValue();
                    String coupon_desc = (String) couponSanp.child("coupon_desc").getValue();
                    String coupon_title = (String) couponSanp.child("coupon_title").getValue();
                    String coupon_type = (String) couponSanp.child("coupon_type").getValue();
                    String coupon_value = (String) couponSanp.child("coupon_value").getValue();

                    CouponData courseData = new CouponData(coupon_code, coupon_title, coupon_desc, coupon_value, coupon_type);
                    couponDataList.add(courseData);

                }

                couponAdapter[0] = new CouponAdapter(couponDataList, CouponActivity.this);
                recyclerCoupon.setAdapter(couponAdapter[0]);
                couponAdapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
