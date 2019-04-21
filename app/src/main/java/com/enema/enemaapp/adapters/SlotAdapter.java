package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.CourseData;
import com.enema.enemaapp.models.SlotModel;
import com.enema.enemaapp.models.TimeSlotData;
import com.enema.enemaapp.ui.activities.CourseDetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SlotAdapter  extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {

    private List<SlotModel> slotModelList;
    private Context context;
    View view;

    public SlotAdapter(List<SlotModel> slotModelList, Context context) {
        this.slotModelList = slotModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SlotAdapter.SlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_slot_item_list, viewGroup, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotAdapter.SlotViewHolder slotViewHolder, int i) {

        final SlotModel slotModel = slotModelList.get(i);
        slotViewHolder.txtMonthYear.setText(slotModel.getSlot_month()+" "+slotModel.getSlot_year());
        slotViewHolder.txtDayDate.setText(slotModel.getSlot_day()+" "+slotModel.getSlot_date());
        slotViewHolder.constrainSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CourseData courseData = new CourseData();
//                DatabaseReference courseDetailsRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");
//                courseDetailsRef.child(String.valueOf(courseData.getCourse_id())).child("COURSE_SLOT").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        String slot_id = (String) dataSnapshot.child("slot_id").getValue();
//                        Toast.makeText(context, ""+slot_id, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
                getAllTimeSlot(slotModel.getSlot_id());

                Toast.makeText(context, ""+slotModel.getSlot_id(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return slotModelList.size();
    }

    public class SlotViewHolder extends RecyclerView.ViewHolder {

        TextView txtMonthYear, txtDayDate;
        ConstraintLayout constrainSlot;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMonthYear = (TextView) itemView.findViewById(R.id.txtMonthYear);
            txtDayDate = (TextView) itemView.findViewById(R.id.txtDayDate);
            constrainSlot = itemView.findViewById(R.id.constrainSlot);

        }
    }


    private void getAllTimeSlot(String slot_id){

        final List<TimeSlotData> timeSlotDataList;
        timeSlotDataList = new ArrayList<>();
        timeSlotDataList.clear();

        final RecyclerView recyclerTime = view.findViewById(R.id.recyclerTime);
        final RecyclerView.Adapter[] timeAdapter = new RecyclerView.Adapter[1];
        recyclerTime.hasFixedSize();
        recyclerTime.setLayoutManager((new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false)));
        SlotModel slotModel = new SlotModel();
        CourseData courseData = new CourseData();
        DatabaseReference courseDetailsRef = FirebaseDatabase.getInstance().getReference("APP_DATA").child("COURSES_DATA");
        courseDetailsRef.child(courseData.getCourse_id()).child("COURSE_SLOT").child("TIME_SLOT").child(slot_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for (DataSnapshot superSnap : dataSnapshot.getChildren()){

                    String time_from = (String) superSnap.child("time_from").getValue();
                    String time_to = (String) superSnap.child("time_to").getValue();
                    Toast.makeText(view.getContext(), ""+time_from, Toast.LENGTH_SHORT).show();

                    TimeSlotData timeSlotData = new TimeSlotData(time_from, time_to);
                    timeSlotDataList.add(timeSlotData);

                }

//


                timeAdapter[0] = new TimeSlotAdapter(timeSlotDataList,view.getContext());
                recyclerTime.setAdapter(timeAdapter[0]);
                timeAdapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
