package com.example.ashwani.incredibleindia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashwani on 27/9/17.
 */

public class Tour extends Fragment implements OnMapReadyCallback{

    private GoogleMap mgoogleMap;
    private RecyclerView recyclerView;
    private TourCardAdapter tourCardAdapter;
    private List<TourCard> list;
    private LatLng ll[];
    private String titles[];
    private  View view;
    private String locations[];
    private ImageView btnTourMapFilter;
    private Marker marker[];
    private int markerImages[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.tour, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        if(googleServicesAvailable()) {
            initMap();
            initRecyclerView(view);
        }
        btnTourMapFilter = (ImageView) view.findViewById(R.id.btnTourMapFilter);
        btnTourMapFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.search_box, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Search",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        for(int i=0;i<marker.length;++i){
                                            marker[i].remove();
                                        }
                                        marker = null;
                                        titles = new String[]{"Agra Fort","Taj Mahal","Chini Ka Rauza","Anguri Bagh","Mariam Tomb","Sikandra Fort","Itmad-ud-Daula","Mehtab Bagh","Jama Masjid","Tomb of Akbar the Great","Moti Masjid","Delhi Gate","Rambagh Park","Shilpgram","Air Safari","Shaheed Smarak Park","Diwan E Aam","Amar Singh Gate","Keetham Lake"};
                                        ll = new LatLng[]{new LatLng(27.1795328,78.018918),new LatLng(27.1750151,78.0399612),new LatLng(27.2008864,78.0320974),new LatLng(27.1784295,78.0208218),new LatLng(27.2153084,77.9405201),new LatLng(27.2153073,77.9251508),new LatLng(27.1928873,78.0287868),new LatLng(27.1800575,78.0398627),new LatLng(27.1820725,78.0142369),new LatLng(27.2205556,77.9483146),new LatLng(27.1803945,78.0194179),new LatLng(27.1807734,78.0158589),new LatLng(27.2052693,78.0360034),new LatLng(27.166122,78.0505174),new LatLng(27.161597,78.057877),new LatLng(27.1983681,78.0025386),new LatLng(27.1792727,78.0196153),new LatLng(27.176458,78.0197857),new LatLng(27.2535604,77.8327596)};
                                        locations = new String[]{" Rakabganj, Agra, Uttar Pradesh 282003","Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh 282001","Ram Bagh, Agra, Uttar Pradesh 282007","Agra Fort, Rakabganj, Agra, Uttar Pradesh 282003","Mathura Delhi Highway, Near Delhi Highway, Sikandra, Agra, Uttar Pradesh 282007","9, Sikandra-Bodla Rd, Sector 6-C, Sulabh Puram colony, Lohamandi, Agra, Uttar Pradesh 282007","Moti Bagh, Agra, Uttar Pradesh 282006","Near Taj Mahal, Dharmapuri, Forest Colony, Nagla Devjit, Agra, Uttar Pradesh 282001","Jama Masjid Road, Kinari Bazar, Hing ki Mandi, Mantola, Subash Bazar, Kinari Bazar, Hing ki Mandi, Mantola, Agra, Uttar Pradesh 282003","Tomb of Akbar The Great Area, Sikandra, Agra, Uttar Pradesh 282007","State Highway 62, Agra Fort, Rakabganj, Agra, Uttar Pradesh 282003","SH 39, Agra Fort, Rakabganj, Agra, Uttar Pradesh 282003","Firojabad Road, Near Aligadh Hatrash Road, Ram Bagh, Agra, Uttar Pradesh 282006","Telipara, Tajganj, Agra, Uttar Pradesh 282001","Fatehabad Road, Tajganj, Agra, Uttar Pradesh 282001","Bagirati Rd, Near Axis Bank, Sanjay Place, Civil Lines, Agra, Uttar Pradesh 282003","SH 62, Agra Fort, Rakabganj, Agra, Uttar Pradesh 282003","Agra Fort, Rakabganj, Agra, Uttar Pradesh 282003","Sur Sarovar Bird Sanctuary"};
                                        marker = new Marker[titles.length];
                                        markerImages = new int[]{R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal,R.drawable.tajmahal};
                                        for(int i=0;i<titles.length;++i) {
                                            MarkerOptions options = new MarkerOptions().title(titles[i]).draggable(false).position(ll[i]);
                                            marker[i] = mgoogleMap.addMarker(options);
                                        }

                                        LatLng tag = new LatLng(27.1750151,78.0399612);
                                        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tag,15.0f));
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
        });
        return  view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        tourCardAdapter = new TourCardAdapter(getActivity(),list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tourCardAdapter);
        prepareCards();
    }

    private void prepareCards() {
        TourCard tourCard = new TourCard("Taj Mahal","Agra",R.drawable.tajmahal);
        list.add(tourCard);

        tourCard = new TourCard("Qutub Minar","Delhi",R.drawable.qutubminar);
        list.add(tourCard);

        tourCard = new TourCard("India Gate","Delhi",R.drawable.indiagate);
        list.add(tourCard);

        tourCard = new TourCard("Jantar Mantar","Delhi",R.drawable.jantarmantar);
        list.add(tourCard);


        tourCard = new TourCard("Red Fort","Delhi",R.drawable.redfort);
        list.add(tourCard);


        tourCard = new TourCard("Akshardham","Delhi",R.drawable.akshardham);
        list.add(tourCard);


        tourCard = new TourCard("Delhi Haat","Delhi",R.drawable.dilihaat);
        list.add(tourCard);


        tourCard = new TourCard("Lotus Temple","Delhi",R.drawable.lotustemple);
        list.add(tourCard);


        tourCard = new TourCard("Zoo","Delhi",R.drawable.zoo);
        list.add(tourCard);

        tourCardAdapter.notifyDataSetChanged();

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapFragment);
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
        mgoogleMap = googleMap;

        mgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(getContext(),TouristPlace.class));
            }
        });

        if(mgoogleMap != null){
            mgoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getActivity().getLayoutInflater().inflate(R.layout.tour_marker,null);

                    ImageView tourMarkerImage = (ImageView) v.findViewById(R.id.tourMarkerImage);
                    TextView tourMarkerImageName = (TextView) v.findViewById(R.id.tourMarkerImageName);
                    TextView tourMarkerImageLocation = (TextView) v.findViewById(R.id.tourMarkerImageLocation);

                    //set the values of tour_marker
                    int id = getMarkerId(marker);

                    tourMarkerImage.setImageResource(markerImages[id]);
                    tourMarkerImageName.setText(titles[id]);
                    tourMarkerImageLocation.setText(locations[id]);

                    return v;
                }
            });
        }

        ll = new LatLng[]{new LatLng(28.5244281,77.1832619),new LatLng(28.6561592,77.2388263),new LatLng(28.5142911,77.2579241),new LatLng(28.5893011,77.2083742),new LatLng(28.6270547,77.2144327),new LatLng(28.603018,77.244354),new LatLng(28.6095744,77.2415431),new LatLng(28.6406214,77.2473062),new LatLng(28.612912,77.2273157),new LatLng(28.553492,77.2566324),new LatLng(28.6261132,77.222804),new LatLng(28.5731414,77.2063715),new LatLng(28.513307,77.1963091),new LatLng(28.6126735,77.2750679),new LatLng(28.5932853,77.2485212)};
        titles = new String[]{"Qutub Minar","Red Fort","Tughlakabad Fort","Safdarjung Tomb","Jantar Mantar","National Zoological Park","Purana Qila","Raj Ghat","India Gate","Lotus Temple","Agrasen ki Baoli","Dilli Haat","The Garden of Five Senses","Akshardham","Humayun's Tomb"};
        locations = new String[]{"Mehrauli, Seth Sarai, Mehrauli, New Delhi, Delhi 110030","Netaji Subhash Marg, Lal Qila, Chandni Chowk, New Delhi, Delhi 110006","Tughlaqabad Fort, Tughlakabad, New Delhi, Delhi 110044","Airforce Golf Course, Delhi Race Club, New Delhi, Delhi 110021","Sansad Marg, Connaught Place, New Delhi, Delhi 110001","Pragati Maidan, New Delhi, Delhi 110001","Mathura Rd, Near Delhi Zoo, Pragati Maidan, New Delhi, Delhi 110003","Behind Red Fort, Gandhi Smriti, Raj Ghat, New Delhi, Delhi 110006","Rajpath Marg, India Gate, New Delhi, Delhi 110001","Lotus Temple Rd, Bahapur, Shambhu Dayal Bagh, Kalkaji, New Delhi, Delhi 110019","Hailey Road, Near Diwanchand Imaging Centre, K G Marg, New Delhi, Delhi 110001","Kidwai Nagar West, Kidwai Nagar\n" +
                "New Delhi, Delhi 110023","Mehrauli-Badarpur Road, Village : Said-ul-Ajaib, Near Saket Metro Station, New Delhi, Delhi 110030","Noida Mor, Pandav Nagar, New Delhi, Delhi 110092","Opposite Dargah Nizamuddin, Mathura Road, New Delhi, Delhi 110013"};
        markerImages = new int[]{R.drawable.qutubminar,R.drawable.redfort,R.drawable.tughlakabadfort,R.drawable.tughlakabadfort,R.drawable.jantarmantar,R.drawable.zoo,R.drawable.puranaqila,R.drawable.rajghat,R.drawable.indiagate,R.drawable.lotustemple,R.drawable.agarsenkibaoli,R.drawable.dilihaat,R.drawable.gardenoffivesenses,R.drawable.akshardham,R.drawable.humayuns_tomb};
        marker = new Marker[titles.length];
        for(int i=0;i<titles.length;++i) {
            MarkerOptions options = new MarkerOptions().title(titles[i]).draggable(false).position(ll[i]);
            marker[i] = mgoogleMap.addMarker(options);
        }

        LatLng airport = new LatLng(28.5561624,77.0977691);
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(airport,12.0f));

    }

    private int getMarkerId(Marker marker) {
        for(int i = 0; i < titles.length; ++i){
            if(marker.getTitle().trim().equals(titles[i])){
                return i;
            }
        }
        return 0;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
