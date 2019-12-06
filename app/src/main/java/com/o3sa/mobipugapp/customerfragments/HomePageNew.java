package com.o3sa.mobipugapp.customerfragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.o3sa.mobipugapp.servicesparsing.GPSTracker;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.CircularImageView;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.uicomponents.Constants;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by android-4 on 11/01/2018.
 */

public class HomePageNew extends Fragment implements OnMapReadyCallback {

    //GPSTracker gpsTracker;

    ImageView cstmr_hmpg_seacrh_img, cstmr_hmpg_fltr_img, cstmr_hmpg_list_img;
    EditText cstmr_hmpg_seacrh_edt;
    LinearLayout cstmr_hmpg_fltr_lay, cstmr_hmpg_list_lay;

    ImageView marker_shop_img;
    TextView shop_name_txt, shop_address_txt;
    BasicComponents components;
    SupportMapFragment mapFragment;
    Context mContext;
    GPSTracker gpsTracker;
    Double latitude_value = 0.0;
    Double langitude_value = 0.0;
    MarkerOptions othermarkerOptions;
    private GoogleMap mMap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_pg, container, false);
        mContext = this.getActivity();


        initialvalues(v);
        return v;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initialvalues(View b) {

        StoredObjects.pagetype = "homelist";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components = new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4, R.color.black, "Home", Constants.WrapLeftBold + Constants.Gibson, new int[]{0, 0, 0, 0});


        cstmr_hmpg_seacrh_img = (ImageView) b.findViewById(R.id.cstmr_hmpg_seacrh_img);
        cstmr_hmpg_seacrh_edt = (EditText) b.findViewById(R.id.cstmr_hmpg_seacrh_edt);
        cstmr_hmpg_fltr_img = (ImageView) b.findViewById(R.id.cstmr_hmpg_fltr_img);
        cstmr_hmpg_list_img = (ImageView) b.findViewById(R.id.cstmr_hmpg_list_img);
        cstmr_hmpg_fltr_lay = (LinearLayout) b.findViewById(R.id.cstmr_hmpg_fltr_lay);
        cstmr_hmpg_list_lay = (LinearLayout) b.findViewById(R.id.cstmr_hmpg_list_lay);

        cstmr_hmpg_fltr_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoredObjects.redirecpage="homepage";
                fragmentmethod(new Mycart());
            }
        });

        cstmr_hmpg_fltr_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoredObjects.redirecpage="homepage";
                fragmentmethod(new Mycart());
            }
        });
        cstmr_hmpg_list_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentmethod(new CustomerProductlist());
            }
        });

        cstmr_hmpg_list_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentmethod(new CustomerProductlist());
            }
        });


        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        othermarkerOptions = new MarkerOptions();
        gpsTracker = new GPSTracker(getActivity(), getActivity());
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            // Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gpsTracker = new GPSTracker(mContext, getActivity());

            // Check if GPS enabled
            if (gpsTracker.canGetLocation()) {

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                try {
                    langitude_value = longitude;
                    latitude_value = latitude;
                } catch (Exception e) {
                    langitude_value = 0.0;
                    latitude_value = 0.0;
                }

                //Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude_value + "\nLong: " + langitude_value, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gpsTracker.showSettingsAlert();
            }
        }

        serach_datatomap();

    }

    private void fragmentmethod(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment).addToBackStack("").commit();

    }


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;
        boolean not_first_time_showing_info_window;

        String imagepath_new;
        private ImageView marker_shop_img = null;
        private TextView shop_name_txt = null;
        private TextView shop_address_txt = null;

        MyInfoWindowAdapter(ImageView marker_shop_img, TextView shop_name_txt, TextView shop_address_txt) {

            myContentsView = getActivity().getLayoutInflater().inflate(R.layout.cstmr_hmpg_map_popup, null);
            this.marker_shop_img = marker_shop_img;
            this.shop_name_txt = shop_name_txt;
            this.shop_address_txt = shop_address_txt;

        }

        @Override
        public View getInfoContents(Marker marker) {

            ImageView marker_shop_img = (ImageView) myContentsView.findViewById(R.id.marker_shop_img);
            TextView shop_name_txt = (TextView) myContentsView.findViewById(R.id.shop_name_txt);
            TextView shop_address_txt = (TextView) myContentsView.findViewById(R.id.shop_address_txt);
            LinearLayout packing_availablity_lay = (LinearLayout) myContentsView.findViewById(R.id.packing_availablity_lay);

            packing_availablity_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //fragmentmethod(new ShopNow());
                    //Toast.makeText(getActivity(),"You Clicked Me",Toast.LENGTH_LONG).show();
                }
            });

            return myContentsView;

        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(20, 20, 20, 20);
        try {

            try {
                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getActivity(), R.raw.map_style_plain));

                if (!success) {
                    Log.e(TAG, "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e(TAG, "Can't find style. Error: ", e);
            }



        }catch (Exception e){

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            if(mapFragment!=null){
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(mapFragment);
                // ft.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onDestroy() {

        Log.i("", "Dessss<><>");

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            if(mapFragment!=null){
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(mapFragment);
                // ft.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        super.onDestroy();
    }
    @Override
    public void onResume() {

        Log.i("", "Dessss<><>");

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            if(mapFragment==null){
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(mapFragment);
                ft.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        super.onResume();
    }
    @Override
    public void onPause() {
        Log.i("", "Dessss<><>:::::::pause");
        if(mMap != null) {
            Log.i("", "Dessss<><>:::::::pause_inner");
            mMap = null;
        }
        //mMap = null;
        super.onPause();
    }

    public void serach_datatomap(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.clear();
                Marker newMarker;
                        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter(marker_shop_img,shop_name_txt,shop_address_txt));

                        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {




                            }
                        });


                    }
        });



    }

}
