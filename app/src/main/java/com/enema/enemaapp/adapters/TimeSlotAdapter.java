package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.TimeSlotData;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private List<TimeSlotData> timeSlotDataList;
    private Context context;

    public TimeSlotAdapter(List<TimeSlotData> timeSlotDataList, Context context) {
        this.timeSlotDataList = timeSlotDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeSlotAdapter.TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_slot_item_list, viewGroup, false);
        return new TimeSlotViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.TimeSlotViewHolder timeSlotViewHolder, int i) {

        TimeSlotData timeSlotData = timeSlotDataList.get(i);
        timeSlotViewHolder.txtBookingTimeSlot.setText(String.format("%s - %s", timeSlotData.getTime_from(), timeSlotData.getTime_to()));

    }

    @Override
    public int getItemCount() {
        return timeSlotDataList.size();
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        TextView txtBookingTimeSlot;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBookingTimeSlot = (TextView) itemView.findViewById(R.id.txtBookingTimeSlot);

        }
    }
}
