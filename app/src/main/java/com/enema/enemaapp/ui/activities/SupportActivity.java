package com.enema.enemaapp.ui.activities;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.MyBookingsAdapter;
import com.enema.enemaapp.adapters.SupportAdapter;
import com.enema.enemaapp.models.SupportData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class SupportActivity extends AppCompatActivity {

    private List<SupportData> supportDataList;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        loadingDialog = new SpotsDialog.Builder().setContext(SupportActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();


        supportDataList = new ArrayList<>();
        supportDataList.clear();

        ImageView imgSupportToAccount = findViewById(R.id.imgSupportToAccount);
        imgSupportToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        getAllQuestions();

    }

    private void getAllQuestions(){
        loadingDialog.show();
        final RecyclerView recyclerSupport = findViewById(R.id.recyclerSupport);
        final RecyclerView.Adapter[] supportAdapter = new RecyclerView.Adapter[1];
        recyclerSupport.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerSupport.setLayoutManager(mLayoutManager);

        DatabaseReference supportRef = FirebaseDatabase.getInstance().getReference("APP_DATA");
        supportRef.child("SUPPORT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                supportDataList.clear();
                for (DataSnapshot supportSnap : dataSnapshot.getChildren()){

                    String support_question = (String) supportSnap.child("support_question").getValue();
                    String support_answer = (String) supportSnap.child("support_answer").getValue();

                    SupportData supportData = new SupportData(support_question, support_answer);

                    supportDataList.add(supportData);
                    loadingDialog.dismiss();

                }

                supportAdapter[0] = new SupportAdapter(supportDataList, SupportActivity.this);
                recyclerSupport.setAdapter(supportAdapter[0]);
                supportAdapter[0].notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
