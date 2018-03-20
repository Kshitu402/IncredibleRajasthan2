package com.example.ashwani.incredibleindia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HomeInfo extends Fragment {

    private ViewPager viewPager;
    private CustomSwiperAdapter customSwiperAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homeinfo, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        customSwiperAdapter = new CustomSwiperAdapter(getContext());
        viewPager.setAdapter(customSwiperAdapter);
        return rootView;
    }
}
