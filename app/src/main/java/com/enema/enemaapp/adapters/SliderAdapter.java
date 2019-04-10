package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enema.enemaapp.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays for content

    public String[] slide_heading = {

            "Learn on demand",
            "Lifetime Access",
            "100+ Online Courses"

    };

    public String[] slide_sub_heading = {

            "Accelerate your Future, Study any course anytime, anywhere.",
            "Explore a variety of fresh topic. Learn on your schedule.",
            "Learn by particular module or take an entire course end-to-end."

    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        TextView txtHeadingSlider = (TextView) view.findViewById(R.id.txtHeadingSlider);
        TextView txtSubHeadingSlider = (TextView) view.findViewById(R.id.txtSubHeadingSlider);

//      imgSlider.setImageResource(slide_images[position]);

        txtHeadingSlider.setText(slide_heading[position]);
        txtSubHeadingSlider.setText(slide_sub_heading[position]);

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
