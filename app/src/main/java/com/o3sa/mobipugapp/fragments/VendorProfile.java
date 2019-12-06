package com.o3sa.mobipugapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.CircularImageView;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android_2 on 11/1/2018.
 */

public class VendorProfile extends Fragment {

    CircularImageView vprfle_prfle_img;
    TextView vprfle_nme_tx,vprfle_adrs_tx,vprfle_bascinfo_tx,vprfle_phnum_tx,vprfle_email_tx,vprfle_fax_tx,vprfle_busnesinfo_tx,
            vprfle_busnme_tx,vprfle_busadrs_tx,vprfle_webste_tx,vprfle_acuntdetls_tx,vprfle_acuntnme_tx,vprfle_acuntnumbr_tx,vprfle_ifsc_tx;

    ImageView vprfle_edtble_img;
    LinearLayout vprfle_edtble_lay;
    Button vndr_prfl_save_btn;

    public static ArrayList<HashMap<String, String>> vndr_profilelist;

    BasicComponents components;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vendor_profile,container,false);

        intialization(v);
        assigndata();
        servicecalling();

        return v;

    }

    public void intialization(View v){

        components = new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.profile),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="viewprofile";
        Sidemenu.updatemenu(StoredObjects.pagetype);

        vprfle_prfle_img = (CircularImageView)v.findViewById(R.id.vprfle_prfle_img);

        vprfle_nme_tx = (TextView)v.findViewById(R.id.vprfle_nme_tx);
        vprfle_adrs_tx = (TextView)v.findViewById(R.id.vprfle_adrs_tx);
        vprfle_bascinfo_tx = (TextView)v.findViewById(R.id.vprfle_bascinfo_tx);
        vprfle_phnum_tx = (TextView)v.findViewById(R.id.vprfle_phnum_tx);
        vprfle_email_tx = (TextView)v.findViewById(R.id.vprfle_email_tx);
        vprfle_fax_tx = (TextView)v.findViewById(R.id.vprfle_fax_tx);
        vprfle_busnesinfo_tx = (TextView)v.findViewById(R.id.vprfle_busnesinfo_tx);
        vprfle_busnme_tx = (TextView)v.findViewById(R.id.vprfle_busnme_tx);
        vprfle_busadrs_tx = (TextView)v.findViewById(R.id.vprfle_busadrs_tx);
        vprfle_webste_tx = (TextView)v.findViewById(R.id.vprfle_webste_tx);
        vprfle_acuntdetls_tx = (TextView)v.findViewById(R.id.vprfle_acuntdetls_tx);
        vprfle_acuntnme_tx =(TextView)v.findViewById(R.id.vprfle_acuntnme_tx);
        vprfle_acuntnumbr_tx =(TextView)v.findViewById(R.id.vprfle_acuntnumbr_tx);
        vprfle_ifsc_tx =(TextView)v.findViewById(R.id.vprfle_ifsc_tx);

        vprfle_edtble_img =(ImageView)v.findViewById(R.id.vprfle_edtble_img);

        //vndr_prfl_save_btn = (Button)v.findViewById(R.id.vndr_prfl_save_btn);

        vprfle_edtble_lay =(LinearLayout)v.findViewById(R.id.vprfle_edtble_lay);

        vprfle_edtble_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoredObjects.vendorprofilearray.clear();
                fragmentcalling(new EditVendorRegistnOne());
            }
        });

    }

    private void servicecalling() {
        parsing_methods(0, StoredUrls.VendorGetprofile_url,"vendor_id="+"1",Constants.POSTMETHOD);
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
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }

                }

            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }
        };

        StoredObjects.Services_list.get(val).countDown.start();

    }

    private void SetVndrProfileData(){

        components.CustomizeTextview(vprfle_nme_tx,Constants.XXXNormal,R.color.white,vndr_profilelist.get(0).get("name"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{7,0,0,0});
        components.CustomizeTextview(vprfle_email_tx,Constants.Medium,R.color.white,vndr_profilelist.get(0).get("email"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{7,0,0,0});

        components.CustomizeTextview(vprfle_phnum_tx,Constants.Medium,R.color.sport_shoos_txt_clr,vndr_profilelist.get(0).get("phone"),Constants.WrapLeftNormal+Constants.WrapLeftSemiBold, new int[]{15,12,0,0});
        components.CustomizeTextview(vprfle_adrs_tx,Constants.Medium,R.color.serch_nrml_clr,vndr_profilelist.get(0).get("map_address"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,12});

        components.CustomizeTextview(vprfle_busnme_tx,Constants.Medium,R.color.sport_shoos_txt_clr,vndr_profilelist.get(0).get("business_name"),Constants.WrapLeftNormal+Constants.WrapLeftSemiBold, new int[]{15,12,0,0});
        components.CustomizeTextview(vprfle_busadrs_tx,Constants.Medium,R.color.serch_nrml_clr,vndr_profilelist.get(0).get("business_address"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,0});
        components.CustomizeTextview(vprfle_webste_tx,Constants.Medium,R.color.serch_nrml_clr,vndr_profilelist.get(0).get("website"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,0});
        components.CustomizeTextview(vprfle_fax_tx,Constants.Medium,R.color.serch_nrml_clr,vndr_profilelist.get(0).get("fax"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,12});

        components.CustomizeTextview(vprfle_acuntnme_tx,Constants.Medium,R.color.sport_shoos_txt_clr,vndr_profilelist.get(0).get("account_number"),Constants.WrapLeftNormal+Constants.WrapLeftSemiBold, new int[]{15,12,0,0});
        components.CustomizeTextview(vprfle_acuntnumbr_tx,Constants.Medium,R.color.serch_nrml_clr,vndr_profilelist.get(0).get("account_number"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,0});
        components.CustomizeTextview(vprfle_ifsc_tx,Constants.Medium,R.color.serch_nrml_clr,vndr_profilelist.get(0).get("ifsc_code"),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,12});

        Glide.with(getActivity())
                .load(Uri.parse(StoredUrls.MainUrl+"/admin/uploads/"+vndr_profilelist.get(0).get("image")))
                .transform(new StoredObjects.CircleTransform(getActivity()))
                .placeholder(R.drawable.noresultsfound)
                .into(vprfle_prfle_img);

    }

    public void assigndata(){

        //components.CustomizeTextview(vprfle_nme_tx,Constants.Large,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.name),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        //components.CustomizeTextview(vprfle_adrs_tx,Constants.Medium,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.block_c),Constants.WrapLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(vprfle_bascinfo_tx,Constants.XNormal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.bascinfrmatn),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,7,0,7});
        //components.CustomizeTextview(vprfle_phnum_tx,Constants.Medium,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.phnenumbr),Constants.WrapLeftNormal+Constants.WrapLeftSemiBold, new int[]{15,12,0,0});
        //components.CustomizeTextview(vprfle_email_tx,Constants.Medium,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.mailid),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,0});
        //components.CustomizeTextview(vprfle_fax_tx,Constants.Medium,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.faxnmbr),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,12});
        components.CustomizeTextview(vprfle_busnesinfo_tx,Constants.XNormal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.busnesinfrmatn),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,7,0,7});
        //components.CustomizeTextview(vprfle_busnme_tx,Constants.Medium,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.name),Constants.WrapLeftNormal+Constants.WrapLeftSemiBold, new int[]{15,12,0,0});
        //components.CustomizeTextview(vprfle_busadrs_tx,Constants.Medium,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.block_c),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,0});
        //components.CustomizeTextview(vprfle_webste_tx,Constants.Medium,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.urlid),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,12});
        components.CustomizeTextview(vprfle_acuntdetls_tx,Constants.XNormal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.acuntdetails),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,7,0,7});
        //components.CustomizeTextview(vprfle_acuntnme_tx,Constants.Medium,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.name),Constants.WrapLeftNormal+Constants.WrapLeftSemiBold, new int[]{15,12,0,0});
        //components.CustomizeTextview(vprfle_acuntnumbr_tx,Constants.Medium,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.acuntnumbr),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,0});
        //components.CustomizeTextview(vprfle_ifsc_tx,Constants.Medium,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.acuntifsc),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,5,0,12});

        components.CustomizeImageview(vprfle_edtble_img, new int[]{12,12},R.drawable.edit_icon, new int[]{0,0,0,0});

        //components.CustomizeButton(vndr_prfl_save_btn, Constants.XXNormal, R.color.white, getActivity().getApplicationContext().getResources().getString(R.string.save), R.drawable.cntinue_btn_bg, Constants.MatchCenterNormal + Constants.SFUIText, new int[]{0, 45}, new int[]{0, 20, 0, 30});

    }

    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

}
