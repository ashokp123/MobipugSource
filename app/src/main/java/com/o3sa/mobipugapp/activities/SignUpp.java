package com.o3sa.mobipugapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android_2 on 10/31/2018.
 */

public class SignUpp extends Fragment {

    EditText cname_txtt,cmobile_txtt,cemail_txtt,cpasswrd_txtt,confrmpasswrd_txtt;
    Button cregister_btnn;
    RadioButton regcustmr_radbtnn,regvendor_radbtnn;
    TextView regcustmr_txtt,regvendor_txtt;
    TextInputLayout cname_lay,cmobile_lay,cemail_lay,cpasswrd_lay,confrmpasswrd_lay;
    String user_type="";
    BasicComponents components;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_upp,container,false);
        components = new BasicComponents(getActivity());

        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v){

        cname_txtt =(EditText)v.findViewById(R.id.cname_txtt);
        cmobile_txtt= (EditText)v.findViewById(R.id.cmobile_txtt);
        cemail_txtt =(EditText)v.findViewById(R.id.cemail_txtt);
        cpasswrd_txtt= (EditText)v.findViewById(R.id.cpasswrd_txtt);
        confrmpasswrd_txtt =(EditText)v.findViewById(R.id.confrmpasswrd_txtt);
        cregister_btnn=(Button) v.findViewById(R.id.cregister_btnn);
        regcustmr_txtt = (TextView)v.findViewById(R.id.regcustmr_txtt);
        regvendor_txtt = (TextView)v.findViewById(R.id.regvendor_txtt);

        regcustmr_radbtnn = (RadioButton)v.findViewById(R.id.regcustmr_radbtnn);
        regvendor_radbtnn = (RadioButton)v.findViewById(R.id.regvendor_radbtnn);

        cname_lay = (TextInputLayout) v.findViewById(R.id.cname_lay);
        cmobile_lay = (TextInputLayout) v.findViewById(R.id.cmobile_lay);
        cemail_lay = (TextInputLayout) v.findViewById(R.id.cemail_lay);
        cpasswrd_lay = (TextInputLayout) v.findViewById(R.id.cpasswrd_lay);
        confrmpasswrd_lay = (TextInputLayout) v.findViewById(R.id.confrmpasswrd_lay);

        regcustmr_radbtnn.setChecked(true);
        regvendor_radbtnn.setChecked(false);
        user_type="customer";
        cmobile_txtt.setInputType(InputType.TYPE_CLASS_NUMBER);

        regvendor_radbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regvendor_radbtnn.isChecked())
                {
                    user_type="vendor";
                    regvendor_radbtnn.setChecked(true);
                    regcustmr_radbtnn.setChecked(false);
                }
            }
        });

        regcustmr_radbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regcustmr_radbtnn.isChecked())
                {
                    user_type="customer";
                    regcustmr_radbtnn.setChecked(true);
                    regvendor_radbtnn.setChecked(false);
                }
            }
        });

        cname_txtt.addTextChangedListener(new MyTextWatcher(cname_txtt));
        cmobile_txtt.addTextChangedListener(new MyTextWatcher(cmobile_txtt));
        cemail_txtt.addTextChangedListener(new MyTextWatcher(cemail_txtt));
        cpasswrd_txtt.addTextChangedListener(new MyTextWatcher(cpasswrd_txtt));
        confrmpasswrd_txtt.addTextChangedListener(new MyTextWatcher(confrmpasswrd_txtt));

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.cname_txtt:
                    validateName();
                    break;

                case R.id.cmobile_txtt:
                    validateMobile();
                    break;
                case R.id.cemail_txtt:
                    validateEmail();
                    break;
                case R.id.cpasswrd_txtt:
                    validatePswrd();
                    break;
                case R.id.confrmpasswrd_txtt:
                    validateCnfmpswd();
                    break;
            }
        }
    }


    private boolean validateName() {
        if (cname_txtt.getText().toString().trim().isEmpty()) {

            requestFocus(cname_txtt);
            return false;
        } else {
            cname_lay.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateMobile() {
        if (cname_txtt.getText().toString().trim().isEmpty()) {

            requestFocus(cname_txtt);
            return false;
        } else {
            cmobile_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        if (cemail_txtt.getText().toString().trim().isEmpty()) {

            requestFocus(cemail_txtt);
            return false;
        } else {
            cemail_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePswrd() {
        if (cpasswrd_txtt.getText().toString().trim().isEmpty()) {

            requestFocus(cpasswrd_txtt);
            return false;
        } else {
            cpasswrd_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCnfmpswd() {
        if (confrmpasswrd_txtt.getText().toString().trim().isEmpty()) {

            requestFocus(confrmpasswrd_txtt);
            return false;
        } else {
            confrmpasswrd_lay.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void assigndata(){

        //components.CustomizeEditview(cname_txtt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.name),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        //components.CustomizeEditview(cmobile_txtt,Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.mobile),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        //components.CustomizeEditview(cemail_txtt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.email),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        //components.CustomizeEditview(cpasswrd_txtt,Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.paswrd),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        //components.CustomizeEditview(confrmpasswrd_txtt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.confrmpassword),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeButton(cregister_btnn, Constants.Normal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.register),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,20,0,0});

        components.CustomizeTextview(regcustmr_txtt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.custmr),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,0,0});
        components.CustomizeTextview(regvendor_txtt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.vendor),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,10,0});

    }
    private void servicecalling() {

        cregister_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());

                String name=cname_txtt.getText().toString().trim();
                String mobile=cmobile_txtt.getText().toString().trim();
                String email=cemail_txtt.getText().toString().trim();
                String password=cpasswrd_txtt.getText().toString().trim();
                String confirmpw=confrmpasswrd_txtt.getText().toString().trim();
                if (StoredObjects.inputValidation(cname_txtt, getActivity().getResources().getString(R.string.pleaseentername),getActivity())) {
                    if (StoredObjects.inputValidation(cmobile_txtt, getActivity().getResources().getString(R.string.pleaseentermobile),getActivity())) {
                        if (StoredObjects.inputValidation(cemail_txtt, getActivity().getResources().getString(R.string.pleaseenteremail),getActivity())) {
                            if(StoredObjects.isValidEmail(email)){
                                if (StoredObjects.inputValidation(cpasswrd_txtt, getActivity().getResources().getString(R.string.pleaseenterpw),getActivity())) {
                                    if (StoredObjects.inputValidation(confrmpasswrd_txtt, getActivity().getResources().getString(R.string.pleaseentervalidpw),getActivity())&&password.equalsIgnoreCase(confirmpw)) {
                                        if(  user_type.equalsIgnoreCase("vendor")){
                                            parsing_methods(0, StoredUrls.Vendorregister_url,"name="+name+"&phone="+mobile+"&email="+email+"&password="+password+StoredUrls.mobiletype);
                                        }else{
                                            parsing_methods(0, StoredUrls.Customerregister_url,"name="+name+"&phone="+mobile+"&email="+email+"&password="+password+StoredUrls.mobiletype);

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
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }
        };

        StoredObjects.Services_list.get(val).countDown.start();


    }
}
