package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.TimeSlotData;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.enema.enemaapp.utils.TimeUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private List<TimeSlotData> timeSlotDataList;
    private Context context;
    private FirebaseUser firebaseUser;
    private DatabaseReference slotRef;
    String user_id;

    public TimeSlotAdapter(List<TimeSlotData> timeSlotDataList, Context context) {
        this.timeSlotDataList = timeSlotDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeSlotAdapter.TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_slot_item_list, viewGroup, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        slotRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }

        return new TimeSlotViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeSlotAdapter.TimeSlotViewHolder timeSlotViewHolder, int i) {
        final String[] state = {"0"};
        final TimeSlotData timeSlotData = timeSlotDataList.get(i);
        timeSlotViewHolder.txtBookingTimeSlot.setText(String.format("%s - %s", timeSlotData.getTime_from(), timeSlotData.getTime_to()));

        timeSlotViewHolder.txtBookingTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser != null) {
                    if (state[0].equals("0")){
                        timeSlotViewHolder.txtBookingTimeSlot.setBackground(context.getResources().getDrawable(R.drawable.border_and_gravity_grey));
                        state[0] = "1";
                    }else {
                        timeSlotViewHolder.txtBookingTimeSlot.setBackground(context.getResources().getDrawable(R.drawable.border_round_corner_no_gravity));
                        state[0] = "0";
                    }
                    TimeUtil timeUtil = new TimeUtil(timeSlotData.getTime_from() +"-"+timeSlotData.getTime_to());
                    assert user_id != null;
                    slotRef.child("TIME_UTIL").child(user_id).setValue(timeUtil);
                } else {
                    gotoLogin();
                }

            }
        });

    }

    private void gotoLogin(){
        context.startActivity(new Intent(context, LoginActivity.class));
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
