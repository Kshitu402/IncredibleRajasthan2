package com.example.ashwani.incredibleindia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.bloder.magic.view.MagicButton;

/**
 * Created by ashwani on 27/9/17.
 */

public class Hotel extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    EditText et;
    ImageView iv;
    String[] NAMES = {"Sort by Popularity", "Sort by * Rating", "Sort by Distance", "Sort by Price"};
    String[] STARS = {"1 Star", "2 Star", "3 Star", "4 Star", "5 Star"};
    TextView tv;
    MagicButton b1;
    AlertDialog.Builder ad, ad2;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.hotel, container, false);
        } catch (InflateException e) {
        }

        if (googleServicesAvailable()) {
            initMap();
        } else {
            //no google maps layout
        }

        b1 = (MagicButton) rootView.findViewById(R.id.magicButton);
        tv = (TextView) rootView.findViewById(R.id.textView);
        b1.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("Sort by");
                ad.setItems(NAMES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (NAMES[i].equals("Sort by * Rating")) {
                            //dusra wala shuru
                            ad2 = new AlertDialog.Builder(getActivity());
                            ad2.setTitle("Select star");
                            ad2.setItems(STARS, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getContext(), STARS[i], Toast.LENGTH_SHORT).show();
                                    goToLocationZoom(28.7041, 77.1025,10);

                                }
                            });
                            AlertDialog alertDialog = ad2.create();
                            alertDialog.show();
                            //dusra wala khatm

                        }

                        Toast.makeText(getContext(), NAMES[i], Toast.LENGTH_SHORT).show();
                        tv.setText(NAMES[i]);
                    }
                });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });

        return rootView;
    }
    private void initMap() {
        MapFragment mapFrag = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.maphotelFragment);
        mapFrag.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(getActivity());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(getActivity(), isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(getContext(), "Cannot connect to Google Play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(28.7041, 77.1025, 15.0f);

    }
    public void geoLocate(View view) throws IOException {
        et=(EditText)rootView.findViewById(R.id.editText);
        iv=(ImageView)rootView.findViewById(R.id.imageView) ;
        String location=et.getText().toString();
        Geocoder gc=new Geocoder(getActivity());
        List<Address> list =gc.getFromLocationName(location,1);
        Address address=list.get(0);
        String locality=address.getLocality();
        Toast.makeText(getContext(), locality, Toast.LENGTH_LONG).show();
        double lat=address.getLatitude();
        double lng=address.getLongitude();
        goToLocationZoom(lat,lng,12);

        //adding a marker////////////////////////////////
        MarkerOptions options=new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat,lng))
                .snippet("175 hotels found");
        mGoogleMap.addMarker(options);

    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate upd= CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mGoogleMap.moveCamera(upd);

    }
}
