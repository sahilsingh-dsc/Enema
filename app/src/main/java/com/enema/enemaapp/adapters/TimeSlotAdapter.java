package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.TimeSlotData;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.enema.enemaapp.utils.TimeUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private List<TimeSlotData> timeSlotDataList;
    private Context context;
    private FirebaseUser firebaseUser;
    private DatabaseReference slotRef;
    String user_id;
    int count;
    int slot_limit;

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
    public void onBindViewHolder(@NonNull final TimeSlotViewHolder timeSlotViewHolder, int i) {
        final String[] state = {"0"};
        final TimeSlotData timeSlotData = timeSlotDataList.get(i);
        timeSlotViewHolder.txtBookingTimeSlot.setText(String.format("%s - %s", timeSlotData.getTime_from(), timeSlotData.getTime_to()));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USER_DATA").child("USERS_BOOKINGS");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot slotSnap : dataSnapshot.getChildren()){

                    for (DataSnapshot childSnap : slotSnap.getChildren()){
                        String booking_time = (String) childSnap.child("booking_time").getValue();
                        String compare = timeSlotData.getTime_from() +"-"+timeSlotData.getTime_to();
                      //  Toast.makeText(context, ""+compare, Toast.LENGTH_SHORT).show();
                        assert booking_time != null;
                        if (booking_time.equals(compare)){
                            count++;
                        //    Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        slot_limit = Integer.parseInt(timeSlotData.getTime_slot_limit());


        timeSlotViewHolder.txtBookingTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (firebaseUser != null) {

                    if (count < slot_limit){

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

                    }else {
                        Toast.makeText(context, "This slot is full please try another", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            timeSlotViewHolder.txtBookingTimeSlot.setTextColor(context.getColor(R.color.colorWhiteTint));
                        }
                    }

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
