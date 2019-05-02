package com.enema.enemaapp.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.WishListAdapter;
import com.enema.enemaapp.models.WishListData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class WishlistFragment extends Fragment {

    View view;
    private List<WishListData> wishListDataList;
    private AlertDialog loadingDialog;
    private String user_id;

    public WishlistFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_wishlist, container, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
            loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                    .setTheme(R.style.loading)
                    .setMessage("Please Wait")
                    .setCancelable(false)
                    .build();

            wishListDataList = new ArrayList<>();
            wishListDataList.clear();
            getUserWishList();
        }
        return view;
    }

    private void getUserWishList(){
        loadingDialog.show();
        final RecyclerView recyclerWishList;
        final RecyclerView.Adapter[] wishlistAdapter = new RecyclerView.Adapter[1];
        recyclerWishList = view.findViewById(R.id.recyclerWishList);
        recyclerWishList.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerWishList.setLayoutManager(mLayoutManager);

        DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("USER_DATA").child("WISHLIST_DATA");
        loadingDialog.dismiss();
        wishlistRef.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                wishListDataList.clear();
                for (DataSnapshot courseSnap : dataSnapshot.getChildren()){
                    String course_name = (String) courseSnap.child("course_name").getValue();
                    String course_image = (String) courseSnap.child("course_image").getValue();
                    String course_rating = (String) courseSnap.child("course_rating").getValue();
                    String course_area = (String) courseSnap.child("course_area").getValue();
                    String course_city = (String) courseSnap.child("course_city").getValue();
                    String course_rating_count = (String) courseSnap.child("course_rating_count").getValue();
                    String course_id = (String) courseSnap.child("course_id").getValue();
                    loadingDialog.dismiss();
                   WishListData wishListData = new WishListData(course_name, course_image, course_rating, course_area, course_city, course_rating_count, course_id);

                   wishListDataList.add(wishListData);

                }

                wishlistAdapter[0] = new WishListAdapter(wishListDataList, getContext());
                recyclerWishList.setAdapter(wishlistAdapter[0]);
                wishlistAdapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
