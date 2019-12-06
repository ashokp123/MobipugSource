package com.o3sa.mobipugapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kiran on 20-10-2018.
 */

public class Signup extends Fragment {

    EditText cname_txt,cmobile_txt,cemail_txt,cpasswrd_txt,confrmpasswrd_txt;
    Button cregister_btn;
    RadioButton  regvendor_radbtn,regcustmr_radbtn;
    TextView regvendor_txt,regcustmr_txt;
    String user_type="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.signup,container,false);
        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }



    public void intialization(View v){
        LoginPage.header_lay.setVisibility(View.VISIBLE);
        cname_txt =(EditText)v.findViewById(R.id.cname_txt);
        cmobile_txt= (EditText)v.findViewById(R.id.cmobile_txt);
        cemail_txt =(EditText)v.findViewById(R.id.cemail_txt);
        cpasswrd_txt= (EditText)v.findViewById(R.id.cpasswrd_txt);
        confrmpasswrd_txt =(EditText)v.findViewById(R.id.confrmpasswrd_txt);
        cregister_btn=(Button) v.findViewById(R.id.cregister_btn);
        regvendor_txt = (TextView)v.findViewById(R.id.regvendor_txt);
        regcustmr_txt = (TextView)v.findViewById(R.id.regcustmr_txt);

        regvendor_radbtn = (RadioButton)v.findViewById(R.id.regvendor_radbtn);
        regcustmr_radbtn = (RadioButton)v.findViewById(R.id.regcustmr_radbtn);

        regcustmr_radbtn.setChecked(true);
        regvendor_radbtn.setChecked(false);
        user_type="customer";
        cmobile_txt.setInputType(InputType.TYPE_CLASS_NUMBER);

        regvendor_radbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regvendor_radbtn.isChecked())
                {
                    user_type="vendor";
                    regvendor_radbtn.setChecked(true);
                    regcustmr_radbtn.setChecked(false);
                }
            }
        });

        regcustmr_radbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regcustmr_radbtn.isChecked())
                {
                    user_type="customer";
                    regcustmr_radbtn.setChecked(true);
                    regvendor_radbtn.setChecked(false);
                }
            }
        });



    }

    private void servicecalling() {

        cregister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());
                String name=cname_txt.getText().toString().trim();
                String mobile=cmobile_txt.getText().toString().trim();
                String email=cemail_txt.getText().toString().trim();
                String password=cpasswrd_txt.getText().toString().trim();
                String confirmpw=confrmpasswrd_txt.getText().toString().trim();
                if (StoredObjects.inputValidation(cname_txt, getActivity().getResources().getString(R.string.pleaseentername),getActivity())) {
                    if (StoredObjects.inputValidation(cmobile_txt, getActivity().getResources().getString(R.string.pleaseentermobile),getActivity())) {
                        if (StoredObjects.inputValidation(cemail_txt, getActivity().getResources().getString(R.string.pleaseenteremail),getActivity())) {
                            if(StoredObjects.isValidEmail(email)){
                                if (StoredObjects.inputValidation(cpasswrd_txt, getActivity().getResources().getString(R.string.pleaseenterpw),getActivity())) {
                                    if (StoredObjects.inputValidation(confrmpasswrd_txt, getActivity().getResources().getString(R.string.pleaseentervalidpw),getActivity())&&password.equalsIgnoreCase(confirmpw)) {
                                        if(  user_type.equalsIgnoreCase("vendor")){
                                            parsing_methods(0, StoredUrls.Vendorregister_url,"full_name="+name+"&telephone="+mobile+"&email="+email+"&password="+password);
                                        }else{
                                            parsing_methods(0, StoredUrls.Customerregister_url,"name="+name+"&mobile="+mobile+"&email="+email+"&password="+password+StoredUrls.mobiletype);

                                        }
                                    }

                                }
                            }else{
                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidemail),getActivity());
                            }

                        }
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
                            StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.registersuccess),getActivity());
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
    public void assigndata(){

        BasicComponents components = new BasicComponents(getActivity());

        components.CustomizeEditview(cname_txt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.name),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
        components.CustomizeEditview(cmobile_txt,Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.mobile),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,15,0,0});
        components.CustomizeEditview(cemail_txt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.email),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,15,0,0});
        components.CustomizeEditview(cpasswrd_txt,Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.paswrd),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,15,0,0});
        components.SetPasswordHint(cpasswrd_txt);
        components.CustomizeEditview(confrmpasswrd_txt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.confrmpassword),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,15,0,0});
        components.SetPasswordHint(confrmpasswrd_txt);
        components.CustomizeButton(cregister_btn, Constants.Normal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.register),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,20,0,0});

        components.CustomizeTextview(regcustmr_txt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.custmr),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,0,0});
        components.CustomizeTextview(regvendor_txt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.vendor),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,10,0});


    }


}
