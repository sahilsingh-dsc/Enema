package com.enema.enemaapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enema.enemaapp.R;

public class FAQActivity extends AppCompatActivity {

    String faq1 = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        final TextView txtFAQ1, txtFAQ1Answer;

        ImageView imgFAQToBkgDtl = findViewById(R.id.imgFAQToBkgDtl);
        imgFAQToBkgDtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });


        txtFAQ1 = findViewById(R.id.txtFAQ1);
        txtFAQ1Answer = findViewById(R.id.txtFAQ1Answer);

        txtFAQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faq1.equals("0")){
                    txtFAQ1Answer.setVisibility(View.VISIBLE);
                    faq1 = "1";
                } else {
                    txtFAQ1Answer.setVisibility(View.GONE);
                    faq1 = "0";
                }
            }
        });

    }
}
