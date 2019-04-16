package com.enema.enemaapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.enema.enemaapp.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageView imgHelpToBkgDtl = findViewById(R.id.imgHelpToBkgDtl);
        imgHelpToBkgDtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

    }
}
