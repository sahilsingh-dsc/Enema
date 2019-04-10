package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.LocationData;
import com.enema.enemaapp.ui.fragments.HomeFragment;

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
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder locationViewHolder, int i) {

        LocationData ld = locationDataList.get(i);
        locationViewHolder.txtLocationName.setText(ld.getLoaction_name());
        Glide.with(this.context).load(ld.getLocation_image()).into(locationViewHolder.imgLocationImage);

    }

    @Override
    public int getItemCount() {
        return locationDataList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView txtLocationName;
        public ImageView imgLocationImage;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLocationName = itemView.findViewById(R.id.txtLocationName);
            imgLocationImage = itemView.findViewById(R.id.imgLocationImage);

        }
    }
}
