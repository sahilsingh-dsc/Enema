package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.SlotModel;
import com.enema.enemaapp.models.TimeSlotData;
import com.enema.enemaapp.ui.activities.CourseDetailsActivity;
import com.enema.enemaapp.ui.activities.LoginActivity;
import com.enema.enemaapp.utils.SlotUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SlotAdapter  extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {

    List<SlotModel> slotModelList;
    Context context;
    View view;
    private FirebaseUser firebaseUser;
    private DatabaseReference slotRef;
    String user_id;

    public SlotAdapter(List<SlotModel> slotModelList, Context context) {
        this.slotModelList = slotModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SlotAdapter.SlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_slot_item_list, viewGroup, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        slotRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }

        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SlotAdapter.SlotViewHolder slotViewHolder, int i) {

        final String[] state = {"0"};

        final SlotModel slotModel = slotModelList.get(i);
        slotViewHolder.txtMonthYear.setText(slotModel.getSlot_month()+" "+slotModel.getSlot_year());
        slotViewHolder.txtDayDate.setText(slotModel.getSlot_day()+" "+slotModel.getSlot_date());
        slotViewHolder.constrainSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (firebaseUser != null) {
                    if (state[0].equals("0")){
                        slotViewHolder.constrainSlot.setBackgroundColor(Color.LTGRAY);
                        state[0] = "1";
                    }else {
                        slotViewHolder.constrainSlot.setBackgroundColor(Color.WHITE);
                        state[0] = "0";
                    }

                    SlotUtil slotUtil = new SlotUtil(slotModel.getSlot_month()+" "+slotModel.getSlot_year(), slotModel.getSlot_day()+" "+slotModel.getSlot_date());
                    slotRef.child("SLOT_UTIL").child(user_id).setValue(slotUtil);
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
}
