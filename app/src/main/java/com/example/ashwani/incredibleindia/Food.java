package com.example.ashwani.incredibleindia;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashwani on 27/9/17.
 */

public class Food extends Fragment implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,LocationListener {


    private ImageView btnTourMapFilter;
    private GoogleMap mMap;
    private ArrayList<LatLng> restaurant = new ArrayList<LatLng>();
    private ArrayList<Restinfo> restaurantname = new ArrayList<>();
    private LinearLayout review;
    private ImageView img;
    private GoogleApiClient mgoogleApiclient;
    private LocationRequest mlocationreq;
    private double latit, longi, curLati, curLongi;
    private double buslat, buslong;
    private LatLng ll1, ll, mark;
    private float distanceInMeters;
    private TextView txt;
    private TextView busstat, distancefromcur;
    private ArrayAdapter<String> adapter;
    private RatingBar rb;
    private String pre="";
    private String restname="";
    private String restspecial="";
    private String resttime="";
    private int j;
    private Circle cc,ccd;
    private String mvar;
    private Marker marker,mm;
    private MarkerOptions option = new MarkerOptions();
    private  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.food, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        if(googleServicesAvailable()) {
            initMap();
        }
        btnTourMapFilter = (ImageView) view.findViewById(R.id.btnTourMapFilter);
        btnTourMapFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(getActivity(),btnTourMapFilter);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString().trim()){
                            case "Go To Location":
                                goToLocationFilter();
                                break;
                            case "State Special":
                                stateSpecialFilter();
                                break;
                            case "Select Cuisine and Budget":
                                cuisineAndBudget();
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        return view;
    }

    private void cuisineAndBudget() {
        restaurant.clear();
        restaurantname.clear();
        final AlertDialog.Builder mb = new AlertDialog.Builder(getActivity());
        final View mview = getActivity().getLayoutInflater().inflate(R.layout.menu_dialog, null);
        mb.setCancelable(false);
        final Spinner cuisine = (Spinner) mview.findViewById(R.id.cuisine);
        Button search=(Button) mview.findViewById(R.id.search);
        final Spinner budget = (Spinner) mview.findViewById(R.id.budget);
        ArrayList<String> cuis = new ArrayList<String>();
        cuis.add("CARIBBEAN");
        cuis.add("CHINESE");
        cuis.add("CONTINENTAL");
        cuis.add("FRENCH");
        cuis.add("GREEK");
        cuis.add("INDIAN");
        cuis.add("ITALIAN");
        cuis.add("JAPANESE");
        cuis.add("MEXICAN");
        cuis.add("SPANISH");
        cuis.add("VIETNAMESE");
        ArrayAdapter<String> puradap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, cuis);
        cuisine.setAdapter(puradap);

        cuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<String> bud = new ArrayList<String>();
                pre=cuisine.getSelectedItem().toString();
                if(pre=="MEXICAN")
                {
                    restaurant.add(new LatLng(28.574744,77.356026));
                    restaurantname.add(new Restinfo(4.0f,"Mexo Fresh","MEXICAN-ITALIAN","11AM-10PM"));
                    restaurant.add(new LatLng(28.472558,77.499550));
                    restaurantname.add(new Restinfo(4.0f,"Mexo Fresh","MEXICAN-ITALIAN","11AM-10PM"));

                    curLati = 28.470757;
                    curLongi = 77.483319;
                    restaurant.add(new LatLng(28.573405,77.371203));
                    restaurantname.add(new Restinfo(4.5f,"SUBWAY","MEXICAN-WESTERN","10AM-10PM"));
                    restaurant.add(new LatLng(28.469465,77.468822));
                    restaurantname.add(new Restinfo(4.5f,"SUBWAY","MEXICAN-WESTERN","10AM-10PM"));
                    restaurant.add(new LatLng(28.470757,77.483319));
                    restaurantname.add(new Restinfo(4.5f,"SUBWAY","MEXICAN-WESTERN","10AM-10PM"));

                    bud.add("less than 1000");
                    bud.add("1000-2000");
                    bud.add("2000-3000");
                    bud.add("3000-4000");
                    bud.add("4000 and greater");

                }
                else
                {
                    bud.add("less than 1000");
                    bud.add("1000-2000");
                    restaurant.add(new LatLng(28.574744,77.356026));
                    restaurantname.add(new Restinfo(3.0f,"SAGAR RATNA","INDIAN-CHINEASE-ARABIC","24/7"));
                    restaurant.add(new LatLng(28.467871,77.490722));
                    restaurantname.add(new Restinfo(3.0f,"SAGAR RATNA","INDIAN-CHINEASE-ARABIC","24/7"));
                }
                ArrayAdapter<String> puradap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, bud);
                budget.setAdapter(puradap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getActivity(),"Please select your choice !!",Toast.LENGTH_LONG).show();
            }
        });
        mb.setView(mview);
        final AlertDialog dialog = mb.create();
        dialog.show();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                searchmyresult();
                dialog.dismiss();
                Toast.makeText(getActivity(),"CHANGES APPLIED SUCCESSULLY \n Your result for "+pre,Toast.LENGTH_LONG).show();
            }
        });

    }

    private void stateSpecialFilter() {
        final AlertDialog.Builder mbe = new AlertDialog.Builder(getActivity());
        final View mmview = getActivity().getLayoutInflater().inflate(R.layout.statespecial_dialog, null);
        mbe.setCancelable(true);
        Button searchstate=(Button) mmview.findViewById(R.id.searchspecial);
        TextView ss=(TextView) mmview.findViewById(R.id.statespecial);
        mbe.setView(mmview);
        final AlertDialog dialog2 = mbe.create();
        dialog2.show();
        ss.setText("Welcome to India"+". The cuisine consists of both vegetarian and non-vegetarian dishes of different varieties. Being a large state, the cuisine of Uttar Pradesh shares a lot of dishes and recipes with the neighboring states of Bihar, Delhi, Uttarakhand, Haryana and Madhya Pradesh Apart from native cuisine, Mughlai and Awadhi are two famous sub types of cuisine of the state." +"\n"+ "-> MENU CHART AVAILABLE :\n"+"1. Shab Deg (a winter dish, turnips and mutton balls with saffron)\n"+"2. Pasanda Paneer (similar to Paneer Makhani or butter paneer (Indian cheese)\n"+"3. Dum Bhindi (Fried whole okra stuffed with spiced potato filling)\n"+"4. Nimona (Green Pea & Potato Curry)\n\n"+"To taste the best kindly press the search button");
        searchstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurant.clear();
                restaurantname.clear();
                mMap.clear();
                curLongi = 77.356046;
                curLati = 28.574752;
                restaurant.add(new LatLng(28.574744,77.356026));
                restaurantname.add(new Restinfo(4.0f,"PRADESH CUISINE","INDIAN","11AM-12NOON"));
                restaurant.add(new LatLng(28.468250,77.484915));
                restaurantname.add(new Restinfo(4.0f,"PRADESH CUISINE","INDIAN","11AM-12NOON"));

                restaurant.add(new LatLng(28.573405,77.371203));
                restaurantname.add(new Restinfo(4.5f,"NAWABI KABAB","MUGHLAI","1PM-11PM"));
                restaurant.add(new LatLng(28.475802,77.490280));
                restaurantname.add(new Restinfo(4.5f,"NAWABI KABAB","MUGHLAI","1PM-11PM"));
                searchmyresult();
                dialog2.dismiss();
                Toast.makeText(getContext(),"CHANGES APPLIED SUCCESSULLY \n Your result for State Special",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchmyresult() {
        ll1 = new LatLng(curLati, curLongi);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(ll1, 13);
        mMap.animateCamera(upd);
        Geocoder geocoder = new Geocoder(getActivity());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(curLati, curLongi, 1);


        } catch (IOException e) {
            e.printStackTrace();
        }

        String locality = addresses.get(0).getLocality();
        String mSubLocality = addresses.get(0).getSubLocality();
        String k = "You are at :" + mSubLocality + "\n" + "Taste the best in "+locality;
        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .position(new LatLng(curLati, curLongi))
                .snippet(k);
        mm=mMap.addMarker(options);
        ccd= drawCircle(new LatLng(curLati, curLongi), mm);


    }

    private void goToLocationFilter() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.go_to_location, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final AutoCompleteTextView userInput = (AutoCompleteTextView) promptsView
                .findViewById(R.id.goToLocationFilter);

        String[] countries = getResources().getStringArray(R.array.place_array);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countries);

        userInput.setAdapter(adapter);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Search",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                curLati = 28.5888119;
                                curLongi = 77.3514184;
                                searchmyresult();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private boolean googleServicesAvailable(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(apiAvailability.isUserResolvableError(isAvailable)){
            Dialog dialog = apiAvailability.getErrorDialog(getActivity(),isAvailable,0);
            dialog.show();
        }else{
            Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {


            }
        });
        curLati = 28.7041;
        curLongi = 77.1025;

        // Add a marker in Sydney and move the camera
        LatLng delhi = new LatLng(28.7041, 77.1025);
        //mMap.addMarker(new MarkerOptions().position(delhi).title("Marker in Delhi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delhi,15.0f));
        restaurant.add(new LatLng(28.583800,77.359719));
        restaurantname.add(new Restinfo(4.0f,"HALDIRAM","INDIAN","11AM-10PM"));
        restaurant.add(new LatLng(28.589268,77.356849));
        restaurantname.add(new Restinfo(4.5f,"BIKANER","INDIAN,MUGHLAI","10AM-10PM"));
        restaurant.add(new LatLng(28.579876,77.356131));
        restaurantname.add(new Restinfo(3.5f,"ASIAN FOOD","CONTINENTAL","1PM-1AM"));
        restaurant.add(new LatLng(28.574744,77.356026));
        restaurantname.add(new Restinfo(3.0f,"SAGAR RATNA","INDIAN-CHINEASE-ARABIC","24/7"));

        restaurant.add(new LatLng(28.469568,77.481088));
        restaurantname.add(new Restinfo(4.0f,"HALDIRAM","INDIAN","11AM-10PM"));
        restaurant.add(new LatLng(28.468588,77.494520));
        restaurantname.add(new Restinfo(4.5f,"BIKANER","INDIAN,MUGHLAI","10AM-10PM"));
        restaurant.add(new LatLng(28.475868,77.495765));
        restaurantname.add(new Restinfo(3.5f,"ASIAN FOOD","CONTINENTAL","1PM-1AM"));
        restaurant.add(new LatLng(28.479188,77.487117));
        restaurantname.add(new Restinfo(3.0f,"SAGAR RATNA","INDIAN-CHINEASE-ARABIC","24/7"));



        if (mMap != null) {
            searchmyresult();
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getActivity().getLayoutInflater().inflate(R.layout.infomarker, null);
                    review=(LinearLayout)v.findViewById(R.id.review);
                    busstat = (TextView) v.findViewById(R.id.textView);
                    distancefromcur = (TextView) v.findViewById(R.id.textView3);
                    rb = (RatingBar) v.findViewById(R.id.ratingBar);
                    rb.setMax(5);
                    rb.setFocusableInTouchMode(true);
                    rb.setVisibility(View.VISIBLE);
                    busstat.setText(marker.getTitle());
                    distancefromcur.setText(marker.getSnippet());
                    return v;
                }
            });
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mgoogleApiclient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mgoogleApiclient.connect();

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }}

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationreq = LocationRequest.create();
        mlocationreq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // mlocationreq.setInterval(0);  to refresh after how much time your current location

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiclient, mlocationreq, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(getActivity(), "cant get your location", Toast.LENGTH_SHORT).show();
        }
        else {
            curLati = location.getLatitude();
            curLongi = location.getLongitude();
            ll1 = new LatLng(curLati, curLongi);
            CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(ll1, 13);
            mMap.animateCamera(upd);
            Geocoder geocoder = new Geocoder(getActivity());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(curLati, curLongi, 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            String locality = addresses.get(0).getLocality();
            String mSubLocality = addresses.get(0).getSubLocality();
            String k = "You are at :" + mSubLocality + "\n" + "Taste the best in "+locality;
            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .position(new LatLng(curLati, curLongi))
                    .snippet(k);
            mm=mMap.addMarker(options);
            ccd= drawCircle(new LatLng(curLati, curLongi), mm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mgoogleApiclient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private Circle drawCircle(LatLng latLng, Marker mar) {

        double dd = 2000;
        CircleOptions opt = new CircleOptions().center(latLng).radius(dd)
                .fillColor(0x30FF0000)
                .strokeColor(Color.BLUE)
                .strokeWidth(3);

        //rb.setVisibility(View.VISIBLE);
        for (j = 0; j < restaurant.size(); j++)
        {
            restname=restaurantname.get(j).restname;
            restspecial=restaurantname.get(j).restspeciality;
            resttime=restaurantname.get(j).timing;
            Float ff=restaurantname.get(j).rating;

            buslat = restaurant.get(j).latitude;
            buslong = restaurant.get(j).longitude;
            LatLng latLngA = new LatLng(latLng.latitude, latLng.longitude);
            LatLng latLngB = new LatLng(buslat, buslong);

            Location locationA = new Location("point A");
            locationA.setLatitude(latLngA.latitude);
            locationA.setLongitude(latLngA.longitude);
            Location locationB = new Location("point B");
            locationB.setLatitude(latLngB.latitude);
            locationB.setLongitude(latLngB.longitude);

            distanceInMeters = (float) locationA.distanceTo(locationB);



                        option.position(new LatLng(buslat, buslong));
                        option.title(""+restname);
                        option.snippet("Distance from current- "+String.format("%.2f",distanceInMeters/1000)+"km"+"\n"+"OUR SPECIALITY:"+"\n"+restspecial+"\n\n"+"TIMINGS :"+resttime);
                        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        mar = mMap.addMarker(option);

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker)
                            {
                                Intent ii=new Intent(getActivity(),Resturant.class);
                                Bundle bb=new Bundle();
                                bb.putString("restaurantname",marker.getTitle().toString());
                                bb.putString("restaurantspecial",marker.getSnippet().toString());
                                ii.putExtras(bb);
                                startActivity(ii);
                            }
                        });


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {


                        return false;
                    }
                });
        }
        return mMap.addCircle(opt);
    }
}
