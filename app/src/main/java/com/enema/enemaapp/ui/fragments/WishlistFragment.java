package com.enema.enemaapp.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.CourseAdapter;
import com.enema.enemaapp.adapters.WishListAdapter;
import com.enema.enemaapp.models.CourseData;
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

public class WishlistFragment extends Fragment {

    View view;
    private List<WishListData> wishListDataList;


    public WishlistFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_wishlist, container, false);

        wishListDataList = new ArrayList<>();
        wishListDataList.clear();
        getUserWishList();
        return view;
    }

    private void getUserWishList(){

        final RecyclerView recyclerWishList;
        final RecyclerView.Adapter[] wishlistAdapter = new RecyclerView.Adapter[1];
        recyclerWishList = view.findViewById(R.id.recyclerWishList);
        recyclerWishList.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerWishList.setLayoutManager(mLayoutManager);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        final DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;


        wishlistRef.child(user_mobile).child(username).child("WISHLIST_DATA").addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(getContext(), ""+course_name, Toast.LENGTH_SHORT).show();

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
