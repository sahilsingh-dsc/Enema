package com.enema.enemaapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.LocationData;
import com.enema.enemaapp.ui.activities.MainActivity;
import com.enema.enemaapp.ui.fragments.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<LocationData> locationDataList;
    private Context context;

    public LocationAdapter(List<LocationData> locationDataList, Context context) {
        this.locationDataList = locationDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.availble_location_item_list, viewGroup, false);

        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationAdapter.LocationViewHolder locationViewHolder, final int i) {

        final LocationData ld = locationDataList.get(i);
        locationViewHolder.txtLocationName.setText(ld.getLoaction_name());
        Glide.with(this.context).load(ld.getLocation_image()).into(locationViewHolder.imgLocationImage);
        locationViewHolder.constrainLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("locations");
                intent.putExtra("queryCity", locationViewHolder.txtLocationName.getText().toString());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationDataList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView txtLocationName;
        public ImageView imgLocationImage;
        ConstraintLayout constrainLocation;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLocationName = itemView.findViewById(R.id.txtLocationName);
            imgLocationImage = itemView.findViewById(R.id.imgLocationImage);
            constrainLocation = itemView.findViewById(R.id.constrainLocation);
        }
    }
}
