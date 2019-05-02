package com.enema.enemaapp.ui.fragments;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.AdSliderAdapter;
import com.enema.enemaapp.adapters.CategoriesAdapter;
import com.enema.enemaapp.adapters.CourseAdapter;
import com.enema.enemaapp.adapters.LocationAdapter;
import com.enema.enemaapp.adapters.RecCourseAdapter;
import com.enema.enemaapp.models.AdSliderData;
import com.enema.enemaapp.models.CategoriesData;
import com.enema.enemaapp.models.CourseData;
import com.enema.enemaapp.models.LocationData;
import com.enema.enemaapp.models.RecCourseData;
import com.enema.enemaapp.ui.activities.NotificationActivity;
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
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {

    View view;
    List<LocationData> locationDataList;
    List<CategoriesData> categoriesDataList;
    List<CourseData> courseDataList;
    List<RecCourseData> recCourseDataList;
    List<AdSliderData> adSliderDataList;
    String user_id;
    private AlertDialog loadingDialog;
    android.widget.SearchView searchView;
    String city = "All Cities";
    String click_state = "none";
    String categories = "All Categories";


    private CourseAdapter courseAdapter;



    public HomeFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView14 = view.findViewById(R.id.textView14);
        textView14.setText(getString(R.string.helloww));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser !=null){

            user_id = firebaseUser.getUid();

            DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
            nameRef.child("USER_PROFILE").child(user_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child("full_name").getValue();
                    TextView textView14 = view.findViewById(R.id.textView14);
                    textView14.setText(String.format("Hello, %s", name));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(locationReceiver,
                new IntentFilter("locations"));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(categoryReceiver,
                new IntentFilter("categories"));

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        loadingDialog.show();


        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        locationDataList = new ArrayList<>();
        locationDataList.clear();
        categoriesDataList = new ArrayList<>();
        categoriesDataList.clear();
        courseDataList = new ArrayList<>();
        courseDataList.clear();
        recCourseDataList = new ArrayList<>();
        recCourseDataList.clear();
        adSliderDataList = new ArrayList<>();
        adSliderDataList.clear();

        getAllAds();
        getAllLocations();
        getAllCategories();
        getCourse();
        getRecommendedCourse();
        //getAllSliderAd();

        getAllDeals();


        if (firebaseUser != null) {
            getNotiCount();
        }

        ImageView imgNotiBell = view.findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent notiIntent = new Intent(getContext(), NotificationActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).startActivity(notiIntent);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                } else {
                    Toast.makeText(getContext(), "You must login to see notifications", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }










    private void getAllAds() {

        DatabaseReference homeScreenAdsRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("ADS_DATA")
                .child("HOME_SCREEN_ADS")
                .child("TOP_SCREEN");

        homeScreenAdsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String ad_title = (String) dataSnapshot.child("ad_title").getValue();
                String ad_price = (String) dataSnapshot.child("ad_price").getValue();
                String ad_image = (String) dataSnapshot.child("ad_image").getValue();
                String ad_click = (String) dataSnapshot.child("ad_click").getValue();

                showTopScreenAd(ad_title, ad_price, ad_image, ad_click);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showTopScreenAd(String ad_title, String ad_price, String ad_image, String ad_click) {

        TextView txttopScreenAdTitle, txttopScreenAdPrice, txttopScreenAdClick;
        ImageView imgAdOnTop;

        txttopScreenAdTitle = view.findViewById(R.id.txttopScreenAdTitle);
        txttopScreenAdPrice = view.findViewById(R.id.txttopScreenAdPrice);
        txttopScreenAdClick = view.findViewById(R.id.txttopScreenAdClick);
        imgAdOnTop = view.findViewById(R.id.imgAdOnTop);

        txttopScreenAdTitle.setText(ad_title);
        txttopScreenAdPrice.setText(ad_price);
        txttopScreenAdClick.setText(ad_click);
        Glide.with(HomeFragment.this).load(ad_image).into(imgAdOnTop);

        loadingDialog.dismiss();

    }

    private void getAllLocations() {

        loadingDialog.show();

        final RecyclerView recyclerLocation;
        final RecyclerView.Adapter[] locationAdapter = new RecyclerView.Adapter[1];

        recyclerLocation = view.findViewById(R.id.recyclerLocation);
        recyclerLocation.hasFixedSize();
        recyclerLocation.setLayoutManager((new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false)));

        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("AVAILABLE_LOCATIONS");

        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                locationDataList.clear();

                for (DataSnapshot locationSnap : dataSnapshot.getChildren()) {

                    String location_name = (String) locationSnap.child("location_name").getValue();
                    String location_image = (String) locationSnap.child("location_image").getValue();

                    LocationData ld = new LocationData(location_name, location_image);
                    locationDataList.add(ld);

                }

                locationAdapter[0] = new LocationAdapter(locationDataList, view.getContext());
                recyclerLocation.setAdapter(locationAdapter[0]);
                locationAdapter[0].notifyDataSetChanged();

                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getCourse();

    }

    private void getAllCategories() {

        loadingDialog.show();

        final RecyclerView recyclerCategory;
        final RecyclerView.Adapter[] categoriesAdapter = new RecyclerView.Adapter[1];

        recyclerCategory = view.findViewById(R.id.recyclerCategory);
        recyclerCategory.hasFixedSize();
        recyclerCategory.setLayoutManager((new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false)));

        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("CATEGORIES_DATA");

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                categoriesDataList.clear();
                for (DataSnapshot categorySnap : dataSnapshot.getChildren()) {

                    String categories_name = (String) categorySnap.child("category_name").getValue();

                    CategoriesData cd = new CategoriesData(categories_name);
                    categoriesDataList.add(cd);

                }

                categoriesAdapter[0] = new CategoriesAdapter(categoriesDataList, view.getContext());
                recyclerCategory.setAdapter(categoriesAdapter[0]);
                categoriesAdapter[0].notifyDataSetChanged();

                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getCourse();

    }



    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            city = intent.getStringExtra("queryCity");
            click_state = "city";
            getCourse();
        }
    };

    public BroadcastReceiver categoryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            categories = intent.getStringExtra("queryCategory");
            click_state = "category";
            getCourse();
        }
    };

    private void getCourse() {
        Query courseQuery;
        final RecyclerView recyclerCourse;
        recyclerCourse = view.findViewById(R.id.recyclerCourse);
        recyclerCourse.hasFixedSize();
        recyclerCourse.setLayoutManager((new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false)));

        courseQuery = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("COURSES_DATA");

        if(click_state.equals("city")){

            if (city.equals("All Cities")) {
                courseQuery = FirebaseDatabase.getInstance().getReference("APP_DATA")
                        .child("COURSES_DATA");
            }else if (city.equals("Near Me")) {
                courseQuery = FirebaseDatabase.getInstance().getReference("APP_DATA")
                        .child("COURSES_DATA").orderByChild("course_city").equalTo("Bangalore");
            } else {
                courseQuery = FirebaseDatabase.getInstance().getReference("APP_DATA")
                        .child("COURSES_DATA").orderByChild("course_city").equalTo(city);
            }

        }

        if (click_state.equals("category")){

            if (categories.equals("All Categories")){
                courseQuery = FirebaseDatabase.getInstance().getReference("APP_DATA")
                        .child("COURSES_DATA");
            }else{
                courseQuery = FirebaseDatabase.getInstance().getReference("APP_DATA")
                        .child("COURSES_DATA").orderByChild("course_category").equalTo(categories);
            }
        }

        courseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                courseDataList.clear();
                for (DataSnapshot courseSnap : dataSnapshot.getChildren()) {

                    String course_name = (String) courseSnap.child("course_name").getValue();
                    String course_image = (String) courseSnap.child("course_image").getValue();
                    String course_rating = (String) courseSnap.child("course_rating").getValue();
                    String course_rating_count = (String) courseSnap.child("course_rating_count").getValue();
                    String course_discount_price = (String) courseSnap.child("course_discount_price").getValue();
                    String course_actual_price = (String) courseSnap.child("course_actual_price").getValue();
                    String course_best_seller_status = (String) courseSnap.child("course_best_seller_status").getValue();
                    String course_id = (String) courseSnap.child("course_id").getValue();
                    String course_area = (String) courseSnap.child("course_area").getValue();
                    String course_city = (String) courseSnap.child("course_city").getValue();


                    CourseData cd = new CourseData(
                            course_name,
                            course_image,
                            course_rating,
                            course_area,
                            course_city,
                            course_rating_count,
                            course_discount_price,
                            course_actual_price,
                            course_best_seller_status,
                            course_id);

                    courseDataList.add(cd);
                }
                courseAdapter = new CourseAdapter(courseDataList, view.getContext());
                recyclerCourse.setAdapter(courseAdapter);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getRecommendedCourse() {

        loadingDialog.show();

        final RecyclerView recyclerRecommended;
        final RecyclerView.Adapter[] reccourseAdapter = new RecyclerView.Adapter[1];
        recyclerRecommended = view.findViewById(R.id.recyclerRecommended);
        recyclerRecommended.hasFixedSize();
        recyclerRecommended.setLayoutManager((new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false)));


        Query recommendedRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("COURSES_DATA").orderByChild("course_rating").startAt("3.5");

        recommendedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                courseDataList.clear();
                for (DataSnapshot courseSnap : dataSnapshot.getChildren()) {

                    String course_name = (String) courseSnap.child("course_name").getValue();
                    String course_image = (String) courseSnap.child("course_image").getValue();
                    String course_rating = (String) courseSnap.child("course_rating").getValue();
                    String course_rating_count = (String) courseSnap.child("course_rating_count").getValue();
                    String course_discount_price = (String) courseSnap.child("course_discount_price").getValue();
                    String course_actual_price = (String) courseSnap.child("course_actual_price").getValue();
                    String course_best_seller_status = (String) courseSnap.child("course_best_seller_status").getValue();
                    String course_id = (String) courseSnap.child("course_id").getValue();
                    String course_area = (String) courseSnap.child("course_area").getValue();
                    String course_city = (String) courseSnap.child("course_city").getValue();

                    RecCourseData recCourseData = new RecCourseData(
                            course_name,
                            course_image,
                            course_rating,
                            course_area,
                            course_city,
                            course_rating_count,
                            course_discount_price,
                            course_actual_price,
                            course_best_seller_status,
                            course_id);

                    recCourseDataList.add(recCourseData);

                }

                reccourseAdapter[0] = new RecCourseAdapter(recCourseDataList, view.getContext());
                recyclerRecommended.setAdapter(reccourseAdapter[0]);
                reccourseAdapter[0].notifyDataSetChanged();

                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void getAllSliderAd(){

        loadingDialog.show();

        final ViewPager pagerAdSlider = view.findViewById(R.id.pagerAdSlider);
        final AdSliderAdapter adSliderAdapter = new AdSliderAdapter(this.getContext());

        DatabaseReference sliderAdsRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("ADS_DATA")
                .child("AD_SLIDER");

        sliderAdsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //adSliderDataList.clear();

                for (DataSnapshot sliderSnap : dataSnapshot.getChildren()){

                    String ad_image = (String) sliderSnap.child("ad_image").getValue();
                    String ad_punch_line = (String) sliderSnap.child("ad_punch_line").getValue();
                    String ad_day = (String) sliderSnap.child("ad_day").getValue();
                    String ad_body = (String) sliderSnap.child("ad_body").getValue();
                    String ad_cost = (String) sliderSnap.child("ad_cost").getValue();
                    String ad_click_data = (String) sliderSnap.child("ad_click_data").getValue();

                    AdSliderData sliderData = new AdSliderData(ad_image, ad_punch_line, ad_day, ad_body, ad_cost, ad_click_data);
                    adSliderDataList.add(sliderData);

                }

                pagerAdSlider.setAdapter(adSliderAdapter);
                adSliderAdapter.notifyDataSetChanged();

                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAllDeals(){

        loadingDialog.show();

        final ImageView imgDeal1, imgDeal2, imgDeal3, imgDeal4;

        imgDeal1 = view.findViewById(R.id.imgDeal1);
        imgDeal2 = view.findViewById(R.id.imgDeal2);
        imgDeal3 = view.findViewById(R.id.imgDeal3);
        imgDeal4 = view.findViewById(R.id.imgDeal4);

        DatabaseReference dealsRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("ADS_DATA")
                .child("AD_DEALS");

        dealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String deal_1 = (String) dataSnapshot.child("deal_1").getValue();
                String deal_2 = (String) dataSnapshot.child("deal_2").getValue();
                String deal_3 = (String) dataSnapshot.child("deal_3").getValue();
                String deal_4 = (String) dataSnapshot.child("deal_4").getValue();

                Glide.with(HomeFragment.this).load(deal_1).into(imgDeal1);
                Glide.with(HomeFragment.this).load(deal_2).into(imgDeal2);
                Glide.with(HomeFragment.this).load(deal_3).into(imgDeal3);
                Glide.with(HomeFragment.this).load(deal_4).into(imgDeal4);

                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getNotiCount(){

        DatabaseReference userNotificationRef = FirebaseDatabase.getInstance().getReference("USER_DATA");

        userNotificationRef.child("USER_NOTIFICATIONS").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String noticount = String.valueOf(dataSnapshot.getChildrenCount());
                    TextView txtMessageCount = view.findViewById(R.id.txtMessageCount);
                    txtMessageCount.setText(noticount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<CourseData> newcourseDataList = new ArrayList<>();
        for (CourseData courseData : courseDataList){
            String course_name = courseData.getCourse_name().toLowerCase().replace(" ", "");
            if (course_name.contains(s))
                newcourseDataList.add(courseData);
        }
        courseAdapter.setfilter(newcourseDataList);
        return true;
    }
}
