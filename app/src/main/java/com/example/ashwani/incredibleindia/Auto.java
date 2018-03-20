package com.example.ashwani.incredibleindia;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kraj4 on 9/30/2017.
 */

public class Auto extends Fragment implements View.OnClickListener {

    private double distance;
    private TextView autoDistance,autoPrice,autoPriceDollar;
    private ImageView openMap,pic;
    private FloatingActionButton fab;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auto, container, false);
        autoDistance = (TextView) rootView.findViewById(R.id.tvAutoDistance);
        autoPrice = (TextView) rootView.findViewById(R.id.tvAutoPrice);
        autoPriceDollar = (TextView) rootView.findViewById(R.id.tvAutoPriceDollar);
        openMap = (ImageView) rootView.findViewById(R.id.map);
        pic = (ImageView) rootView.findViewById(R.id.dollarPic);

        distance = getActivity().getIntent().getExtras().getDouble("distance");
        setOnClick("" + distance, "" + calculateFair(distance));

        openMap.setOnClickListener(this);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TravelShareDetailQrScan.class));
            }
        });
        return rootView;
    }

    public double calculateFair(double dist){
        double fair=25;
        if(dist>2)
            return (dist-2)*8+25;
        return fair;
    }
    public void setOnClick(String distance,String fare){
        if(!distance.equals("0.0")) {
            autoDistance.setText("Distance = " + distance + " KM");
            autoPrice.setText("Rs. " + fare);
            double x = Double.parseDouble(fare)/64.97;
            String d = String.format("%.2f",x);
            autoPriceDollar.setText("$ " + d);

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),TravelShowMap.class);
        intent.putExtra("sourcelatlong",calculateLatLong(new Travel().sourceLocate()));
        intent.putExtra("destinationlatlong",calculateLatLong(new Travel().destinationLocate()));
        startActivity(intent);
    }

    public static String calculateLatLong(String s){

        String latlong="";
        switch(s){
            case " Hawa Mahal Rd, J.D.A. Market, Jaipur, Rajasthan 302002":
                latlong = "26.912417,75.787288";
                break;
            case "Jaipur, Rajasthan 302002, India":
                latlong = "26.922070,75.778885";
                break;
            case "Rajiv Chowk, Block B, Connaught Place, New Delhi, 110001, India":
                latlong = "28.633861,77.219093";
                break;
            case "Taj Mahal, Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh, 282001, India":
                latlong = "27.175006,78.042166";
                break;
            case "Gurdwara Bangla Sahib, Sansad Marg Area, Pandit Pant Marg Area, New Delhi, 110001, India":
                latlong = "28.626315,77.209048";
                break;
        }
        return latlong;
    }
}
