package com.o3sa.mobipugapp.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.GPSTracker;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android_2 on 11/13/2018.
 */

public class VRegistnThree extends Fragment {

    EditText vreg_latitude_edtx,vreg_longitude_edtx,vreg_telephne_edtx,vreg_fax_edtx,vreg_email_edtx,vreg_bus_adres_edtx,
            vreg_map_adres_edtx,vreg_pincde_edtx,vreg_website_edtx;

    Button vreg_sbmt_btn;

    BasicComponents components;

    public ArrayList<HashMap<String, String>> vndr_profilelist;

    Context mContext;
    GPSTracker gpsTracker;
    public static Double latitude_value=0.0;
    public static Double langitude_value=0.0;
    public static String cur_lat="";
    public static String cur_lng="";

    String [] business_images = {StoredObjects.vndr_business_img,StoredObjects.vndr_business_img};
    String [] business_background_images = {StoredObjects.vndr_business_bg_img,StoredObjects.vndr_business_bg_img};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.v_registn_three, container, false);
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
        servicecalling();
        return v;
    }
    public void intialization(View v) {

        StoredObjects.pagetype="completeprofile2";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,"Complete Profile",Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        vreg_latitude_edtx = (EditText) v.findViewById(R.id.vreg_latitude_edtx);
        vreg_longitude_edtx = (EditText) v.findViewById(R.id.vreg_longitude_edtx);
        vreg_telephne_edtx = (EditText) v.findViewById(R.id.vreg_telephne_edtx);
        vreg_fax_edtx = (EditText) v.findViewById(R.id.vreg_fax_edtx);
        vreg_email_edtx = (EditText) v.findViewById(R.id.vreg_email_edtx);
        vreg_bus_adres_edtx = (EditText) v.findViewById(R.id.vreg_bus_adres_edtx);
        vreg_map_adres_edtx = (EditText) v.findViewById(R.id.vreg_map_adres_edtx);
        vreg_pincde_edtx = (EditText) v.findViewById(R.id.vreg_pincde_edtx);
        vreg_website_edtx = (EditText) v.findViewById(R.id.vreg_website_edtx);

        vreg_sbmt_btn=(Button) v.findViewById(R.id.vreg_sbmt_btn);

        assigndata();
    }

    public void assigndata() {

        vreg_latitude_edtx.setText(cur_lat);
        vreg_longitude_edtx.setText(cur_lng);

        components.CustomizeEditview(vreg_latitude_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.latitude),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_longitude_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.longitude),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_telephne_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.telephone),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_fax_edtx, Constants.Medium ,getActivity().getApplicationContext().getResources().getString(R.string.fax),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_email_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.email_mndtry),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_pincde_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.pincode_mndtry),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vreg_website_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.website),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});

        components.CustomizeMultilineEditview(vreg_bus_adres_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.bussinessaddress),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},3);
        components.CustomizeMultilineEditview(vreg_map_adres_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.mapaddress),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},3);

        components.CustomizeButton(vreg_sbmt_btn, Constants.Normal, R.color.white, getActivity().getApplicationContext().getResources().getString(R.string.submit_), R.drawable.cntinue_btn_bg, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0, 45}, new int[]{0,0, 0,25});

    }

    private void servicecalling() {
        StoredObjects.UserId="1";
        parsing_methods(0, StoredUrls.VendorGetprofile_url,"vendor_id="+1,Constants.POSTMETHOD);

        vreg_sbmt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editservicecalling("No");
            }
        });
    }

    private void editservicecalling(String imageupload) {

        StoredObjects.hide_keyboard_activity(getActivity());

        StoredObjects.vndr_lat = vreg_latitude_edtx.getText().toString().trim();
        StoredObjects.vndr_lng = vreg_longitude_edtx.getText().toString().trim();
        StoredObjects.vndr_telephone = vreg_telephne_edtx.getText().toString().trim();
        StoredObjects.vndr_fax = vreg_fax_edtx.getText().toString().trim();
        StoredObjects.vndr_email = vreg_email_edtx.getText().toString().trim();
        StoredObjects.vndr_bsness_addrss = vreg_bus_adres_edtx.getText().toString().trim();
        StoredObjects.vndr_map_addrss = vreg_map_adres_edtx.getText().toString().trim();
        StoredObjects.vndr_pincode = vreg_pincde_edtx.getText().toString().trim();
        StoredObjects.vndr_website = vreg_website_edtx.getText().toString().trim();

        if (StoredObjects.inputValidation(vreg_latitude_edtx, getActivity().getResources().getString(R.string.pls_ntr_lat), getActivity())) {
            if (StoredObjects.inputValidation(vreg_longitude_edtx, getActivity().getResources().getString(R.string.pls_ntr_lng), getActivity())) {
                if (StoredObjects.inputValidation(vreg_telephne_edtx, getActivity().getResources().getString(R.string.pls_ntr_tel), getActivity())) {
                    if (StoredObjects.inputValidation(vreg_fax_edtx, getActivity().getResources().getString(R.string.pls_ntr_fax), getActivity())) {
                        if (StoredObjects.inputValidation(vreg_email_edtx, getActivity().getResources().getString(R.string.pls_ntr_email), getActivity())) {
                            if (StoredObjects.inputValidation(vreg_bus_adres_edtx, getActivity().getResources().getString(R.string.pls_ntr_bsnss), getActivity())) {
                                if (StoredObjects.inputValidation(vreg_map_adres_edtx, getActivity().getResources().getString(R.string.pls_ntr_map), getActivity())) {
                                    if (StoredObjects.inputValidation(vreg_pincde_edtx, getActivity().getResources().getString(R.string.pls_ntr_pincode), getActivity())) {
                                        if (StoredObjects.inputValidation(vreg_website_edtx, getActivity().getResources().getString(R.string.pls_ntr_website), getActivity())) {

                                            if (StoredObjects.isValidEmail(StoredObjects.vndr_email)) {
                                                if (imageupload.equalsIgnoreCase("No")) {

                                                    parsing_methods(1, StoredUrls.VendorEditprofile_url,"vendor_id="+1+"&latitude="+StoredObjects.vndr_lat+"&longitude="+StoredObjects.vndr_lng+
                                                            "&phone="+StoredObjects.vndr_telephone+"&fax="+StoredObjects.vndr_fax+"&email="+StoredObjects.vndr_email+"&business_address="+StoredObjects.vndr_bsness_addrss+
                                                            "&map_address="+StoredObjects.vndr_map_addrss+"&pincode="+StoredObjects.vndr_pincode+
                                                            "&name="+StoredObjects.vndr_fullname+"&business_name="+StoredObjects.vndr_business_name+"&about_us="+StoredObjects.vndr_abt_us+
                                                            "&account_number="+StoredObjects.vndr_ac+"&ifsc_code="+StoredObjects.vndr_ifsc+"&id_proof="+StoredObjects.vndr_idproof+"&pan_card="+StoredObjects.vndr_idproof+
                                                            "&business_images="+StoredObjects.vndr_business_img+"&business_background_images="+StoredObjects.vndr_business_bg_img+
                                                            "&website="+StoredObjects.vndr_website,Constants.POSTMETHOD);

                                                }else {

                                                    parsing_methods(1, StoredUrls.VendorEditprofile_url,"vendor_id="+1+"&latitude="+StoredObjects.vndr_lat+"&longitude="+StoredObjects.vndr_lng+
                                                            "&phone="+StoredObjects.vndr_telephone+"&fax="+StoredObjects.vndr_fax+"&email="+StoredObjects.vndr_email+"&business_address="+StoredObjects.vndr_bsness_addrss+
                                                            "&map_address="+StoredObjects.vndr_map_addrss+"&pincode="+StoredObjects.vndr_pincode+
                                                            "&name="+StoredObjects.vndr_fullname+"&business_name="+StoredObjects.vndr_business_name+"&about_us="+StoredObjects.vndr_abt_us+
                                                            "&account_number="+StoredObjects.vndr_ac+"&ifsc_code="+StoredObjects.vndr_ifsc+"&id_proof="+StoredObjects.vndr_idproof+"&pan_card="+StoredObjects.vndr_idproof+
                                                            "&business_images="+StoredObjects.vndr_business_img+"&business_background_images="+StoredObjects.vndr_business_bg_img+
                                                            "&website="+StoredObjects.vndr_website,Constants.POSTMETHOD);

                                                    /*parsing_methods(1, StoredUrls.VendorEditprofile_url,"vendor_id="+StoredObjects.UserId+"&latitude="+StoredObjects.vndr_lat+"&longitude="+StoredObjects.vndr_lng+
                                                            "&phone="+StoredObjects.vndr_telephone+"&fax="+StoredObjects.vndr_fax+"&email="+StoredObjects.vndr_email+"&business_address="+StoredObjects.vndr_bsness_addrss+
                                                            "&map_address="+StoredObjects.vndr_map_addrss+"&pincode="+StoredObjects.vndr_pincode+
                                                            "&name="+StoredObjects.vndr_fullname+"&business_name="+StoredObjects.vndr_business_name+"&about_us="+StoredObjects.vndr_abt_us+"&password="+""+
                                                            "&account_number="+StoredObjects.vndr_ac+"&ifsc_code="+StoredObjects.vndr_ifsc+ "&id_proof="+StoredObjects.vndr_idproof+"&pan_card="+StoredObjects.vndr_idproof+
                                                            "&package_type="+StoredObjects.vndr_pckg_type+"&category_id="+""+"&sub_categories="+""+"&main_category="+""+"&business_images_count="+""+"&business_images="+StoredObjects.vndr_business_img+
                                                            "&business_background_images_count="+""+"&business_background_images="+StoredObjects.vndr_business_bg_img+"&website="+StoredObjects.vndr_website,Constants.POSTMETHOD);*/
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
                            vndr_profilelist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
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
                                parsing_methods(0, StoredUrls.VendorGetprofile_url,"vendor_id="+1,Constants.POSTMETHOD);
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

    private void SetVndrProfileData() {

        vreg_telephne_edtx.setText(vndr_profilelist.get(0).get("telephone"));
        vreg_fax_edtx.setText(vndr_profilelist.get(0).get("fax"));
        vreg_email_edtx.setText(vndr_profilelist.get(0).get("email"));
        vreg_bus_adres_edtx.setText(vndr_profilelist.get(0).get("business_address"));
        vreg_map_adres_edtx.setText(vndr_profilelist.get(0).get("map_address"));
        vreg_pincde_edtx.setText(vndr_profilelist.get(0).get("pincode"));
        vreg_website_edtx.setText(vndr_profilelist.get(0).get("pincode"));
        vreg_pincde_edtx.setText(vndr_profilelist.get(0).get("website"));

    }

}
