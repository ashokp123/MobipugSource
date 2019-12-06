package com.o3sa.mobipugapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

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
 * Created by android_2 on 10/31/2018.
 */

public class SignInn extends Fragment {

    EditText sgin_emal_edtxt,sgin_pswd_edtxt;
    TextView sgin_frgtpswd_txtt,loginvendor_txtt,logincustmr_txtt;
    Button sgin_ctnue_btnn;
    RadioButton logincustmr_radbtnn,loginvendor_radbtnn;
    TextInputLayout sgin_emal_lay,sgin_pswd_lay;

    BasicComponents components;
    Database database;
    String user_type="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_inn,container,false);
        components = new BasicComponents(getActivity());

        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }
    public void intialization(View v) {
        database=new Database(getActivity());
        database.getAllDevice();
        LoginPage.header_lay.setVisibility(View.VISIBLE);
        sgin_emal_edtxt = (EditText) v.findViewById(R.id.sgin_emal_edtxt);
        sgin_pswd_edtxt = (EditText) v.findViewById(R.id.sgin_pswd_edtxt);

        sgin_frgtpswd_txtt = (TextView) v.findViewById(R.id.sgin_frgtpswd_txtt);
        sgin_ctnue_btnn = (Button) v.findViewById(R.id.sgin_ctnue_btnn);
        logincustmr_radbtnn = (RadioButton) v.findViewById(R.id.logincustmr_radbtnn);
        loginvendor_radbtnn = (RadioButton) v.findViewById(R.id.loginvendor_radbtnn);
        logincustmr_txtt = (TextView) v.findViewById(R.id.logincustmr_txtt);
        loginvendor_txtt = (TextView) v.findViewById(R.id.loginvendor_txtt);

        sgin_emal_lay = (TextInputLayout) v.findViewById(R.id.sgin_emal_lay);
        sgin_pswd_lay = (TextInputLayout) v.findViewById(R.id.sgin_pswd_lay);

        logincustmr_radbtnn.setChecked(true);
        loginvendor_radbtnn.setChecked(false);
        user_type="customer";

        sgin_frgtpswd_txtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.UserType=user_type;
                fragmentcalling(new ForgotPasswordd());
            }
        });

        loginvendor_radbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginvendor_radbtnn.isChecked())
                {
                    user_type="vendor";
                    loginvendor_radbtnn.setChecked(true);
                    logincustmr_radbtnn.setChecked(false);
                }
            }
        });

        logincustmr_radbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logincustmr_radbtnn.isChecked())
                {
                    user_type="customer";
                    logincustmr_radbtnn.setChecked(true);
                    loginvendor_radbtnn.setChecked(false);
                }
            }
        });

        sgin_emal_edtxt.addTextChangedListener(new MyTextWatcher(sgin_emal_edtxt));
        sgin_pswd_edtxt.addTextChangedListener(new MyTextWatcher(sgin_pswd_edtxt));


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
                case R.id.sgin_emal_edtxt:
                    validateEmail();
                    break;

                case R.id.sgin_pswd_edtxt:
                    validatePassword();
                    break;
            }
        }
    }


    private boolean validateEmail() {
        if (sgin_emal_edtxt.getText().toString().trim().isEmpty()) {

            requestFocus(sgin_emal_edtxt);
            return false;
        } else {
            sgin_emal_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (sgin_pswd_edtxt.getText().toString().trim().isEmpty()) {
             requestFocus(sgin_pswd_edtxt);
            return false;
        } else {
            sgin_pswd_lay.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void assigndata(){

        //components.TextCustomizeEditview(sgin_emal_edtxt, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.email),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
       // components.TextCustomizeEditview(sgin_pswd_edtxt,Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.paswrd),0,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
        components.CustomizeTextview(sgin_frgtpswd_txtt,Constants.Medium,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.frgtpswd),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
        components.CustomizeButton(sgin_ctnue_btnn, Constants.XNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.signin),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,20,0,0});
        components.CustomizeTextview(logincustmr_txtt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.custmr),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,0,0});
        components.CustomizeTextview(loginvendor_txtt,Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.vendor),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,0,10,0});
    }

    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_fram_lay, fragment).addToBackStack("").commit();

    }

    private void servicecalling() {

        sgin_ctnue_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());
                //Alex@gmail.com/test

                String email=sgin_emal_edtxt.getText().toString().trim();
                String password=sgin_pswd_edtxt.getText().toString().trim();
                if (StoredObjects.inputValidation(sgin_emal_edtxt, getActivity().getResources().getString(R.string.pleaseenteremail),getActivity())) {
                    if(StoredObjects.isValidEmail(email)){
                        if (StoredObjects.inputValidation(sgin_pswd_edtxt, getActivity().getResources().getString(R.string.pleaseenterpw),getActivity())) {

                            parsing_methods(0, StoredUrls.Customerlogin_url,"email="+email+"&password="+password+"&gcm_regid="+StoredObjects.GCMID);

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

                            StoredObjects.UserId=jsonObject.getString("customer_id");
                            StoredObjects.UserType=jsonObject.getString("profile_type");
                            database.UpdateUserId(StoredObjects.UserId);
                            database.UpdateUserType(StoredObjects.UserType);
                            StoredObjects.Services_list.get(val).countDown.cancel();

                            getActivity().finish();
                            startActivity(new Intent(getActivity(),Sidemenu.class));

                        }else{
                            String error = jsonObject.getString("error");

                            StoredObjects.ToastMethod(error,getActivity());
                            StoredObjects.Services_list.get(val).countDown.cancel();

                        }

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
