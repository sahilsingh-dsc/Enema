package com.enema.enemaapp.ui.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enema.enemaapp.adapters.NotificatiomAdapter;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.NotificationData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private List<NotificationData> notificationDataList;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ImageView imgNotificationToMain = findViewById(R.id.imgNotificationToMain);
        imgNotificationToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        notificationDataList = new ArrayList<>();
        notificationDataList.clear();

        if (firebaseUser != null) {
            getAllNotification();
        }

        TextView txtClearNoti = findViewById(R.id.txtClearNoti);
        TextView txtNotiCount = findViewById(R.id.txtNotiCount);
        if (txtNotiCount.getText().equals("No Notification")){
            txtClearNoti.setVisibility(View.INVISIBLE);
        }else {
            txtClearNoti.setVisibility(View.VISIBLE);
        }


    }

    private void getAllNotification(){

        final RecyclerView recyclerNotification = findViewById(R.id.recyclerNotification);
        final RecyclerView.Adapter[] notificationAdapter = new RecyclerView.Adapter[1];
        recyclerNotification.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerNotification.setLayoutManager(mLayoutManager);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();

        DatabaseReference userNotificationRef = FirebaseDatabase.getInstance().getReference("USER_DATA");

        userNotificationRef.child(username).child("USER_NOTIFICATIONS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                notificationDataList.clear();
                for (DataSnapshot notiSnap : dataSnapshot.getChildren()){

                    String noti_message = (String) notiSnap.child("noti_message").getValue();
                    NotificationData notificationData = new NotificationData(noti_message);
                    notificationDataList.add(notificationData);
                    TextView txtNotiCount = findViewById(R.id.txtNotiCount);
                    txtNotiCount.setText(notificationDataList.size()+" "+getString(R.string.new_noti));
                }

                notificationAdapter[0] = new NotificatiomAdapter(notificationDataList, NotificationActivity.this);
                recyclerNotification.setAdapter(notificationAdapter[0]);
                notificationAdapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
