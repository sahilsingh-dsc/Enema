package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enema.enemaapp.R;
import com.enema.enemaapp.models.AdSliderData;

import java.util.List;

public class AdSliderAdapter extends PagerAdapter {

    private Context context;
    LayoutInflater layoutInflater;
    List<AdSliderData> adSliderDataList;


    public AdSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return adSliderDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ad_slider, null);

        ImageView imgSliderAdImage = view.findViewById(R.id.imgSliderAdImage);
//        TextView txtPunchLine, txtDay, txtAdBody, txtAdAmount, txtClickOnAd;
//
//        txtPunchLine = view.findViewById(R.id.txtPunchLine);
//        txtDay = view.findViewById(R.id.txtDay);
//        txtAdBody = view.findViewById(R.id.txtAdBody);
//        txtAdAmount = view.findViewById(R.id.txtAdAmount);
//        txtClickOnAd = view.findViewById(R.id.txtClickOnAd);

        AdSliderData asd = adSliderDataList.get(position);
        Glide.with(this.context).load(asd.getAd_image()).into(imgSliderAdImage);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
