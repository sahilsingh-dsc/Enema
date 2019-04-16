package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.SliderAdapter;


public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager vpSlider;
    private LinearLayout lhDotsLayout;

    private TextView[] dots;

    // Slider adapter class
    private SliderAdapter sliderAdapter;

    private Button btnSignIn, btnBrowseCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        btnBrowseCourses =findViewById(R.id.btnBrowseCourses);
        btnBrowseCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(OnBoardingActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        vpSlider = findViewById(R.id.vpSlider);
        lhDotsLayout = (LinearLayout) findViewById(R.id.lhDotsLayout);

        sliderAdapter = new SliderAdapter(this);
        vpSlider.setAdapter(sliderAdapter);

        addDotsToSlider(0);

        vpSlider.addOnPageChangeListener(viewListener);


    }

    public void addDotsToSlider(int position){

        dots = new TextView[3];
        lhDotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(getResources().getColor(R.color.colorWhiteTrans));
            lhDotsLayout.addView(dots[i]);

        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.colorDarkTrans));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsToSlider(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
