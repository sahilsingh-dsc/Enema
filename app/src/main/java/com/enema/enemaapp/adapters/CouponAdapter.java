package com.enema.enemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.CouponData;
import com.enema.enemaapp.ui.activities.BookCourseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    List<CouponData> couponDataList;
    Context context;
    FirebaseUser firebaseUser;
    String user_id;

    public CouponAdapter(List<CouponData> couponDataList, Context context) {
        this.couponDataList = couponDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CouponAdapter.CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coupon_list_item, viewGroup, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            user_id = firebaseUser.getUid();
        }

        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CouponAdapter.CouponViewHolder couponViewHolder, int i) {

        final CouponData couponData = couponDataList.get(i);

        couponViewHolder.txtCouponCode.setText(couponData.getCoupon_code());
        couponViewHolder.txtCouponTitle.setText(couponData.getCoupon_title());
        couponViewHolder.txtCouponDesc.setText(couponData.getCoupon_desc());
        couponViewHolder.txtApplyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference couponRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
                CouponState couponState = new CouponState(couponData.getCoupon_code(), couponData.getCoupon_type(), couponData.getCoupon_value());
                couponRef.child("COUPON_STATE").child(user_id).setValue(couponState);
                couponViewHolder.txtApplyCoupon.setText("Applied");
                Toast.makeText(context, "Now go back and complete remaining info", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return couponDataList.size();
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder {

        TextView txtCouponCode, txtCouponTitle, txtCouponDesc, txtApplyCoupon;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCouponCode = itemView.findViewById(R.id.txtCouponCode);
            txtCouponTitle = itemView.findViewById(R.id.txtCouponTitle);
            txtCouponDesc = itemView.findViewById(R.id.txtCouponDesc);
            txtApplyCoupon = itemView.findViewById(R.id.txtApplyCoupon);

        }
    }
}
