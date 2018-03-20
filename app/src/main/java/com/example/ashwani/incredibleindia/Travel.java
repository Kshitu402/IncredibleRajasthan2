package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ashwani on 27/9/17.
 */

public class Travel extends Fragment implements View.OnClickListener {

    private String source="", destination="";
    static double distance;
    String orig[] = new String[6];
    private Button sourceBtn,destinationBtn,btnCheckRides;
    private TextView sourceDetail,destinationDetail;
    static String sourceString, destinationString;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.travel, container, false);
        sourceBtn = (Button) rootView.findViewById(R.id.btnSource);
        destinationBtn = (Button) rootView.findViewById(R.id.btnDestination);
        sourceDetail = (TextView) rootView.findViewById(R.id.sourceDetail);
        destinationDetail = (TextView) rootView.findViewById(R.id.destinationDetail);
        btnCheckRides = (Button) rootView.findViewById(R.id.btnCheckRides);
        sourceBtn.setOnClickListener(this);
        sourceDetail.setOnClickListener(this);
        destinationBtn.setOnClickListener(this);
        destinationDetail.setOnClickListener(this);
        btnCheckRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(source) && TextUtils.isEmpty(destination)){
                    Toast.makeText(getContext(),"Select source and destination!!!!",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(source)){
                    Toast.makeText(getContext(),"Select source!!!!",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(destination)){
                    Toast.makeText(getContext(),"Select destination!!!!",Toast.LENGTH_LONG).show();
                }else {
                    distance = calculate(source,destination);
                    startActivity(new Intent(getActivity(),CheckRides.class).putExtra("distance",distance).putExtra("source",source).putExtra("destination",destination));
                }
            }
        });
        orig = getResources().getStringArray(R.array.auto_complete);

        return rootView;
    }

    private double calculate(String s, String s1) {
        if(!s.equals("") && !s1.equals("")) {
            sourceString = s;
            destinationString = s1;
            double arr[][]=new double[10][10];
            for(int i=0;i<10;i++)
                arr[i][i]=0;
            arr[0][1]=1;
            arr[0][2]=16.9;
            arr[0][3]=208.9;
            arr[0][4]=20.1;

            arr[1][2]=24;
            arr[1][3]=238.1;
            arr[1][4]=21.1;

            arr[2][3]=220.8;
            arr[2][4]=2;

            arr[3][4]=223;
            int a=0,b=0;
            for(int i=0;i<5;i++){
                if(s.equals(orig[i]))
                    a=i;
                if(s1.equals(orig[i]))
                    b=i;
            }
            if(a>b)
                return arr[b][a];
            else
                return arr[a][b];
        }
        return  0;
    }


    public static String sourceLocate(){
        return sourceString;
    }
    public static String destinationLocate(){ return destinationString;}


    @Override
    public void onClick(View v) {
        final View mview = getActivity().getLayoutInflater().inflate(R.layout.alert_input_dialoge,null);
        final AutoCompleteTextView location = (AutoCompleteTextView) mview.findViewById(R.id.Location);
        TextView header = (TextView) mview.findViewById(R.id.header);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(mview);
        final AlertDialog dialog = alert.create();
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, orig);
        location.setAdapter(adapter);
        location.setThreshold(1);


        switch (v.getId()){
            case R.id.btnSource:
                header.setText("SOURCE LOCATION");
                location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        source = location.getText().toString();
                        sourceDetail.setText(source);
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.btnDestination:
                header.setText("DESTINATION LOCATION");
                location.setText("");
                location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        destination = location.getText().toString();
                        destinationDetail.setText(destination);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.sourceDetail:
                header.setText("SOURCE LOCATION");
                location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        source = location.getText().toString();
                        sourceDetail.setText(source);
                        sourceBtn.setBackgroundResource(R.drawable.location_button_food_bg2);
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.destinationDetail:
                header.setText("DESTINATION LOCATION");
                location.setText("");
                location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        destination = location.getText().toString();
                        destinationDetail.setText(destination);
                        destinationBtn.setBackgroundResource(R.drawable.back_text_view2);
                        dialog.dismiss();
                    }
                });
                break;

        }
        dialog.show();
    }
}
