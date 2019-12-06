package com.o3sa.mobipugapp.activities;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.database.Database;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android_2 on 10/11/2018.
 */

public class SignIn extends Fragment {

    EditText sgin_emal_edtx,sgin_pswd_edtx;
    TextView sgin_frgtpswd_txt,logincustmr_txt,loginvendor_txt;
    Button sgin_ctnue_btn;
    RadioButton logincustmr_radbtn,loginvendor_radbtn;
    String user_type="";
    Database database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_in,container,false);
        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v){
        database=new Database(getActivity());
        database.getAllDevice();
        LoginPage.header_lay.setVisibility(View.VISIBLE);

        sgin_emal_edtx =(EditText)v.findViewById(R.id.sgin_emal_edtx);
        sgin_pswd_edtx= (EditText)v.findViewById(R.id.sgin_pswd_edtx);
        sgin_frgtpswd_txt =(TextView)v.findViewById(R.id.sgin_frgtpswd_txt);
        sgin_ctnue_btn = (Button)v.findViewById(R.id.sgin_ctnue_btn);
        logincustmr_radbtn = (RadioButton)v.findViewById(R.id.logincustmr_radbtn);
        loginvendor_radbtn = (RadioButton)v.findViewById(R.id.loginvendor_radbtn);
        logincustmr_txt =(TextView)v.findViewById(R.id.logincustmr_txt);
        loginvendor_txt =(TextView)v.findViewById(R.id.loginvendor_txt);

        logincustmr_radbtn.setChecked(true);
        loginvendor_radbtn.setChecked(false);
        user_type="customer";

        sgin_frgtpswd_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.UserType=user_type;
                fragmentcalling(new ForgotPassword());
            }
        });

        loginvendor_radbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginvendor_radbtn.isChecked())
                {
                    user_type="vendor";
                    loginvendor_radbtn.setChecked(true);
                    logincustmr_radbtn.setChecked(false);
                }
            }
        });

        logincustmr_radbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logincustmr_radbtn.isChecked())
                {
                    user_type="customer";
                    logincustmr_radbtn.setChecked(true);
                    loginvendor_radbtn.setChecked(false);
                }
            }
        });




    }

    public void assigndata(){

        BasicComponents components = new BasicComponents(getActivity());

        components.CustomizeEditview(sgin_emal_edtx, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.email),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,25,0,0});
        components.CustomizeEditview(sgin_pswd_edtx,Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.paswrd),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,15,0,0});
        components.CustomizeTextview(sgin_frgtpswd_txt,Constants.Medium,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.frgtpswd),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
        components.CustomizeButton(sgin_ctnue_btn, Constants.XNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.signin),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,20,0,0});
        components.CustomizeTextview(logincustmr_txt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.custmr),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,0,0});
        components.CustomizeTextview(loginvendor_txt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.vendor),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,10,0});

        components.SetPasswordHint(sgin_pswd_edtx);
    }

    private void servicecalling() {

        sgin_ctnue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());
                String email=sgin_emal_edtx.getText().toString().trim();
                String password=sgin_pswd_edtx.getText().toString().trim();
                        if (StoredObjects.inputValidation(sgin_emal_edtx, getActivity().getResources().getString(R.string.pleaseenteremail),getActivity())) {
                            if(StoredObjects.isValidEmail(email)){
                                if (StoredObjects.inputValidation(sgin_pswd_edtx, getActivity().getResources().getString(R.string.pleaseenterpw),getActivity())) {
                                    if(  user_type.equalsIgnoreCase("vendor")){
                                        parsing_methods(0, StoredUrls.Vendorlogin_url,"email="+email+"&password="+password+"&gcm_regid="+StoredObjects.GCMID);
                                    }else{
                                        parsing_methods(0, StoredUrls.Customerlogin_url,"email="+email+"&password="+password+"&gcm_regid="+StoredObjects.GCMID);
                                    }
                                    }

                                }else{
                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidemail),getActivity());
                            }
                }

            }
        });


    }
    public void parsing_methods(final  int val,String url,String parameters){
        WebServicesCalling webServicesCalling = new WebServicesCalling(getActivity());

        webServicesCalling.calling_webservices(url,parameters,val,Constants.POSTMETHOD);

        StoredObjects.Services_list.get(val).countDown = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                StoredObjects.LogMethod("Seconds remaining: "," Seconds remaining: " + millisUntilFinished / 100);

                if(StoredObjects.Services_list.get(val).Result != ""){

                    StoredObjects.LogMethod("Result",StoredObjects.Services_list.get(val).Result);
                    StoredObjects.LogMethod("Service_called",StoredObjects.Services_list.get(val).url);
                    try {

                        JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                        String status = jsonObject.getString("status");
                        if(status.equalsIgnoreCase("200")){

                            if(  user_type.equalsIgnoreCase("vendor")){
                                // {"status":200,"message":"success","vendor_id":6,"telephone":"02992929929"}
                                StoredObjects.UserId= jsonObject.getString("vendor_id");
                                StoredObjects.UserType="Vendor";
                                database.UpdateUserId(StoredObjects.UserId);
                                database.UpdateUserType(StoredObjects.UserType);
                                getActivity().finish();
                                startActivity(new Intent(getActivity(),Sidemenu.class));
                            }else{
                                StoredObjects.UserId=jsonObject.getString("customer_id");
                                StoredObjects.UserType="Customer";
                                database.UpdateUserId(StoredObjects.UserId);
                                database.UpdateUserType(StoredObjects.UserType);
                                getActivity().finish();
                                startActivity(new Intent(getActivity(),Sidemenu.class));
                            }


                        }else{
                            String error = jsonObject.getString("error");

                            StoredObjects.ToastMethod(error,getActivity());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_fram_lay, fragment).addToBackStack("").commit();

    }
}
