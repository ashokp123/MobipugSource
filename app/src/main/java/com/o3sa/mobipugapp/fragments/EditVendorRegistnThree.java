package com.o3sa.mobipugapp.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.GPSTracker;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.Gravity.TOP;

/**
 * Created by android_2 on 11/5/2018.
 */

public class EditVendorRegistnThree extends Fragment {

    TextView edt_vreg_latitude_tx,edt_vreg_longitude_tx,edt_vreg_telephne_tx,edt_vreg_fax_tx,edt_vreg_email_tx,edt_vreg_bus_adres_tx,
            edt_vreg_map_adres_tx,edt_vreg_pincde_tx,edt_vreg_website_tx;

    EditText edt_vreg_latitude_edtx,edt_vreg_longitude_edtx,edt_vreg_telephne_edtx,edt_vreg_fax_edtx,edt_vreg_email_edtx,edt_vreg_bus_adres_edtx,
            edt_vreg_map_adres_edtx,edt_vreg_pincde_edtx,edt_vreg_website_edtx;

    Button edt_vreg_sbmt_btn;
    BasicComponents components;

    public static ArrayList<HashMap<String, String>> vndr_profilelistt;

    String vndr_lat = "",vndr_lng = "",vndr_telephone = "",vndr_fax = "",vndr_email = "",vndr_bsness_addrss = "",vndr_map_addrss = "",vndr_pincode = "",vndr_website = "";

    Context mContext;
    GPSTracker gpsTracker;
    public static Double latitude_value=0.0;
    public static Double langitude_value=0.0;
    public static String cur_lat="";
    public static String cur_lng="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_vendor_registn_three,container,false);

        components = new BasicComponents(getActivity());

        mContext = this.getActivity();
        gpsTracker = new GPSTracker(getActivity(),getActivity());

        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            // Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gpsTracker = new GPSTracker(mContext,getActivity());

            // Check if GPS enabled
            if (gpsTracker.canGetLocation()) {

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                Log.i("","gpsvalues:--"+gpsTracker.getLatitude()+"<>longitude:--"+gpsTracker.getLongitude());

                try {
                    latitude_value = latitude;
                    langitude_value = longitude;
                }catch (Exception e){
                    latitude_value = 0.0;
                    langitude_value = 0.0;
                }

                cur_lat = String.valueOf(latitude_value);
                cur_lng = String.valueOf(langitude_value);

                Toast.makeText(getActivity(), "Your Location is - \nLat: " + cur_lat + "\nLong: " + cur_lng, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gpsTracker.showSettingsAlert();
            }
        }

        intialization(v);

        if(StoredObjects.vendorprofilearray.size()>=18){
            //setImages(StoredObjects.vendorprofilearray);
            assigndata();
        }else{
            assigndata();
        }

        servicecalling();
        return v;
    }

    public void intialization(View v) {

        edt_vreg_latitude_edtx =(EditText)v.findViewById(R.id.edt_vreg_latitude_edtx);
        edt_vreg_longitude_edtx =(EditText)v.findViewById(R.id.edt_vreg_longitude_edtx);
        edt_vreg_telephne_edtx =(EditText)v.findViewById(R.id.edt_vreg_telephne_edtx);
        edt_vreg_fax_edtx =(EditText)v.findViewById(R.id.edt_vreg_fax_edtx);
        edt_vreg_email_edtx =(EditText)v.findViewById(R.id.edt_vreg_email_edtx);
        edt_vreg_bus_adres_edtx =(EditText)v.findViewById(R.id.edt_vreg_bus_adres_edtx);
        edt_vreg_map_adres_edtx = (EditText)v.findViewById(R.id.edt_vreg_map_adres_edtx);
        edt_vreg_pincde_edtx = (EditText)v.findViewById(R.id.edt_vreg_pincde_edtx);
        edt_vreg_website_edtx = (EditText)v.findViewById(R.id.edt_vreg_website_edtx);

        edt_vreg_latitude_tx = (TextView)v.findViewById(R.id.edt_vreg_latitude_tx);
        edt_vreg_longitude_tx = (TextView)v.findViewById(R.id.edt_vreg_longitude_tx);
        edt_vreg_telephne_tx = (TextView)v.findViewById(R.id.edt_vreg_telephne_tx);
        edt_vreg_fax_tx = (TextView)v.findViewById(R.id.edt_vreg_fax_tx);
        edt_vreg_email_tx = (TextView)v.findViewById(R.id.edt_vreg_email_tx);
        edt_vreg_bus_adres_tx = (TextView)v.findViewById(R.id.edt_vreg_bus_adres_tx);
        edt_vreg_map_adres_tx = (TextView)v.findViewById(R.id.edt_vreg_map_adres_tx);
        edt_vreg_pincde_tx = (TextView)v.findViewById(R.id.edt_vreg_pincde_tx);
        edt_vreg_website_tx = (TextView)v.findViewById(R.id.edt_vreg_website_tx);

        edt_vreg_sbmt_btn = (Button) v.findViewById(R.id.edt_vreg_sbmt_btn);

    }

    private void servicecalling() {
        StoredObjects.UserId="1";
        parsing_methods(0, StoredUrls.VendorGetprofile_url,"vendor_id="+1,Constants.POSTMETHOD);

        edt_vreg_sbmt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editservicecalling("No");
            }
        });
    }

    private void editservicecalling(String imageupload) {

        StoredObjects.hide_keyboard_activity(getActivity());

        vndr_lat = edt_vreg_latitude_edtx.getText().toString().trim();
        vndr_lng = edt_vreg_longitude_edtx.getText().toString().trim();
        vndr_telephone = edt_vreg_telephne_edtx.getText().toString().trim();
        vndr_fax = edt_vreg_fax_edtx.getText().toString().trim();
        vndr_email = edt_vreg_email_edtx.getText().toString().trim();
        vndr_bsness_addrss = edt_vreg_bus_adres_edtx.getText().toString().trim();
        vndr_map_addrss = edt_vreg_map_adres_edtx.getText().toString().trim();
        vndr_pincode = edt_vreg_pincde_edtx.getText().toString().trim();
        vndr_website = edt_vreg_website_edtx.getText().toString().trim();

        if (StoredObjects.inputValidation(edt_vreg_latitude_edtx, getActivity().getResources().getString(R.string.pls_ntr_lat), getActivity())) {
            if (StoredObjects.inputValidation(edt_vreg_longitude_edtx, getActivity().getResources().getString(R.string.pls_ntr_lng), getActivity())) {
                if (StoredObjects.inputValidation(edt_vreg_telephne_edtx, getActivity().getResources().getString(R.string.pls_ntr_tel), getActivity())) {
                    if (StoredObjects.inputValidation(edt_vreg_fax_edtx, getActivity().getResources().getString(R.string.pls_ntr_fax), getActivity())) {
                        if (StoredObjects.inputValidation(edt_vreg_email_edtx, getActivity().getResources().getString(R.string.pls_ntr_email), getActivity())) {
                            if (StoredObjects.inputValidation(edt_vreg_bus_adres_edtx, getActivity().getResources().getString(R.string.pls_ntr_bsnss), getActivity())) {
                                if (StoredObjects.inputValidation(edt_vreg_map_adres_edtx, getActivity().getResources().getString(R.string.pls_ntr_map), getActivity())) {
                                    if (StoredObjects.inputValidation(edt_vreg_pincde_edtx, getActivity().getResources().getString(R.string.pls_ntr_pincode), getActivity())) {
                                        if (StoredObjects.inputValidation(edt_vreg_website_edtx, getActivity().getResources().getString(R.string.pls_ntr_website), getActivity())) {

                                            if (StoredObjects.isValidEmail(vndr_email)) {
                                                if (imageupload.equalsIgnoreCase("No")) {


                                                    StoredObjects.vendorprofilearray.add(vndr_lat);//17
                                                    StoredObjects.vendorprofilearray.add(vndr_lng);//18
                                                    StoredObjects.vendorprofilearray.add(vndr_telephone);//19
                                                    StoredObjects.vendorprofilearray.add(vndr_fax);//20
                                                    StoredObjects.vendorprofilearray.add(vndr_email);//21
                                                    StoredObjects.vendorprofilearray.add(vndr_bsness_addrss);//22
                                                    StoredObjects.vendorprofilearray.add(vndr_map_addrss);//23
                                                    StoredObjects.vendorprofilearray.add(vndr_pincode);//24
                                                    StoredObjects.vendorprofilearray.add(vndr_website);//25

                                                    parsing_methods(1, StoredUrls.VendorEditprofile_url,"vendor_id="+1+"&latitude="+StoredObjects.vendorprofilearray.get(17)+"&longitude="+StoredObjects.vendorprofilearray.get(18)+
                                                            "&phone="+StoredObjects.vendorprofilearray.get(19)+"&fax="+StoredObjects.vendorprofilearray.get(20)+"&email="+StoredObjects.vendorprofilearray.get(21)+"&business_address="+StoredObjects.vendorprofilearray.get(22)+
                                                            "&map_address="+StoredObjects.vendorprofilearray.get(23)+"&pincode="+StoredObjects.vendorprofilearray.get(24)+"&package_type="+StoredObjects.vendorprofilearray.get(5)+
                                                            "&name="+StoredObjects.vendorprofilearray.get(2)+"&business_name="+StoredObjects.vendorprofilearray.get(9)+"&about_us="+StoredObjects.vendorprofilearray.get(14)+
                                                            "&account_number="+StoredObjects.vendorprofilearray.get(3)+"&ifsc_code="+StoredObjects.vendorprofilearray.get(4)+"&id_proof="+StoredObjects.vendorprofilearray.get(0)+"&pan_card="+StoredObjects.vendorprofilearray.get(1)+
                                                            "&business_images="+StoredObjects.vendorprofilearray.get(10)+"&business_background_images="+StoredObjects.vendorprofilearray.get(11)+
                                                            "&website="+StoredObjects.vendorprofilearray.get(25)+"&image="+StoredObjects.vendorprofilearray.get(6),Constants.POSTMETHOD);

                                                }else {

                                                    StoredObjects.vendorprofilearray.add(vndr_lat);//17
                                                    StoredObjects.vendorprofilearray.add(vndr_lng);//18
                                                    StoredObjects.vendorprofilearray.add(vndr_telephone);//19
                                                    StoredObjects.vendorprofilearray.add(vndr_fax);//20
                                                    StoredObjects.vendorprofilearray.add(vndr_email);//21
                                                    StoredObjects.vendorprofilearray.add(vndr_bsness_addrss);//22
                                                    StoredObjects.vendorprofilearray.add(vndr_map_addrss);//23
                                                    StoredObjects.vendorprofilearray.add(vndr_pincode);//24
                                                    StoredObjects.vendorprofilearray.add(vndr_website);//25

                                                    parsing_methods(1, StoredUrls.VendorEditprofile_url,"vendor_id="+1+"&latitude="+StoredObjects.vendorprofilearray.get(17)+"&longitude="+StoredObjects.vendorprofilearray.get(18)+
                                                            "&phone="+StoredObjects.vendorprofilearray.get(18)+"&fax="+StoredObjects.vendorprofilearray.get(20)+"&email="+StoredObjects.vendorprofilearray.get(21)+"&business_address="+StoredObjects.vendorprofilearray.get(22)+
                                                            "&map_address="+StoredObjects.vendorprofilearray.get(23)+"&pincode="+StoredObjects.vendorprofilearray.get(24)+"&package_type="+StoredObjects.vendorprofilearray.get(5)+
                                                            "&name="+StoredObjects.vendorprofilearray.get(2)+"&business_name="+StoredObjects.vendorprofilearray.get(9)+"&about_us="+StoredObjects.vendorprofilearray.get(14)+
                                                            "&account_number="+StoredObjects.vendorprofilearray.get(3)+"&ifsc_code="+StoredObjects.vendorprofilearray.get(4)+"&id_proof="+StoredObjects.vendorprofilearray.get(0)+"&pan_card="+StoredObjects.vendorprofilearray.get(1)+
                                                            "&business_images="+StoredObjects.vendorprofilearray.get(10)+"&business_background_images="+StoredObjects.vendorprofilearray.get(11)+
                                                            "&website="+StoredObjects.vendorprofilearray.get(25)+"&image="+StoredObjects.vendorprofilearray.get(6),Constants.POSTMETHOD);
                                                }
                                            } else {
                                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidemail), getActivity());
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void parsing_methods(final int val,String url,String parameters,String parsing_type){

        WebServicesCalling webServicesCalling = new WebServicesCalling(getActivity());

        webServicesCalling.calling_webservices(url,parameters,val,parsing_type);

        StoredObjects.Services_list.get(val).countDown = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                StoredObjects.LogMethod("Seconds remaining: "," Seconds remaining: " + millisUntilFinished / 100);

                if(StoredObjects.Services_list.get(val).Result != ""){

                    StoredObjects.LogMethod("Result",StoredObjects.Services_list.get(val).Result);
                    StoredObjects.LogMethod("Service_called",StoredObjects.Services_list.get(val).url);
                    if(val==0) {
                        try {
                            vndr_profilelistt = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            SetVndrProfileData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(val==1){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.profileupdate_msg),getActivity());

                                fragmentcalling(new VendorProfile());
                                //StoredObjects.vendorprofilearray.clear();

                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    StoredObjects.Services_list.get(val).countDown.cancel();

                }

            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }
        };

        StoredObjects.Services_list.get(val).countDown.start();

    }

    public void assigndata() {

        edt_vreg_latitude_edtx.setText(cur_lat);
        edt_vreg_longitude_edtx.setText(cur_lng);

        components.CustomizeTextview(edt_vreg_latitude_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.latitude), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_longitude_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.longitude), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_telephne_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.telephone), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_fax_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.fax), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_email_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.email_mndtry), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_bus_adres_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.bussinessaddress), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_map_adres_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.mapaddress), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_pincde_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.pincode_mndtry), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});
        components.CustomizeTextview(edt_vreg_website_tx, Constants.XNormal, R.color.black, getActivity().getApplicationContext().getResources().getString(R.string.website), Constants.WrapLeftNormal + Constants.Gibson, new int[]{5,0,0,5});

        components.CustomizeEditview(edt_vreg_latitude_edtx, Constants.Medium,  getActivity().getApplicationContext().getResources().getString(R.string.latitude),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_longitude_edtx, Constants.Medium,  getActivity().getApplicationContext().getResources().getString(R.string.longitude),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_telephne_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.telephone),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_fax_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.fax),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_email_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.email_mndtry),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_pincde_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.pincode_mndtry),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(edt_vreg_website_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.website),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});

        components.CustomizeMultilineEditview(edt_vreg_bus_adres_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.bussinessaddress),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},3);
        components.CustomizeMultilineEditview(edt_vreg_map_adres_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.mapaddress),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},3);
        edt_vreg_bus_adres_edtx.setGravity(Gravity.LEFT|TOP);
        edt_vreg_map_adres_edtx.setGravity(Gravity.LEFT|TOP);
        edt_vreg_bus_adres_edtx.setPadding(20,5,20,5);
        edt_vreg_map_adres_edtx.setPadding(20,5,20,5);

        components.CustomizeButton(edt_vreg_sbmt_btn, Constants.Normal, R.color.white, getActivity().getApplicationContext().getResources().getString(R.string.submit_), R.drawable.cntinue_btn_bg, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0, 45}, new int[]{0,10,0,30});

        edt_vreg_latitude_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("latitude"));
        edt_vreg_longitude_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("longitude"));
        edt_vreg_telephne_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("phone"));
        edt_vreg_fax_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("fax"));
        edt_vreg_email_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("email"));
        edt_vreg_bus_adres_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("business_address"));
        edt_vreg_map_adres_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("map_address"));
        edt_vreg_pincde_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("pincode"));
        edt_vreg_website_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("website"));


    }

    private void SetVndrProfileData() {

        edt_vreg_latitude_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("latitude"));
        edt_vreg_longitude_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("longitude"));
        edt_vreg_telephne_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("phone"));
        edt_vreg_fax_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("fax"));
        edt_vreg_email_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("email"));
        edt_vreg_bus_adres_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("business_address"));
        edt_vreg_map_adres_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("map_address"));
        edt_vreg_pincde_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("pincode"));
        edt_vreg_website_edtx.setText(VendorProfile.vndr_profilelist.get(0).get("website"));

    }

    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

}
