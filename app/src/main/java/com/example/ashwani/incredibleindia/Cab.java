package com.example.ashwani.incredibleindia;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by kraj4 on 9/30/2017.
 */

public class Cab extends Fragment implements View.OnClickListener {

    private double distance;
    private TextView cabDistance,cabPrice,cabPriceDollar;
    private ImageView openMap,pic;
    private FloatingActionButton fab;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cab, container, false);
        cabDistance = (TextView) rootView.findViewById(R.id.tvCabDistance);
        cabPrice = (TextView) rootView.findViewById(R.id.tvCabPrice);
        cabPriceDollar = (TextView) rootView.findViewById(R.id.tvCabPriceDollar);
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
            return (dist-1)*16+25;
        return fair;
    }

    public void setOnClick(String distance,String fare){
        if(!distance.equals("0.0")) {
            cabDistance.setText("Distance = " + distance + " KM");
            cabPrice.setText("Rs. " + fare);
            double x = Double.parseDouble(fare)/64.97;
            String d = String.format("%.2f",x);
            cabPriceDollar.setText("$ " + d);
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
            case "Chhalera, Chhalera Bangar, Sector 44, Noida, Uttar Pradesh 201303, India":
                latlong = "28.552392, 77.340082";
                break;
            case "Delhi Public School, Sector 30, Noida, Uttar Pradesh 201303, India":
                latlong = "28.574598, 77.335600";
                break;
            case "Rajiv Chowk, Block B, Connaught Place, New Delhi, 110001, India":
                latlong = "28.633861, 77.219093";
                break;
            case "Taj Mahal, Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh, 282001, India":
                latlong = "27.175006, 78.042166";
                break;
            case "Gurdwara Bangla Sahib, Sansad Marg Area, Pandit Pant Marg Area, New Delhi, 110001, India":
                latlong = "28.626315, 77.209048";
                break;
        }
        return latlong;
    }
}
