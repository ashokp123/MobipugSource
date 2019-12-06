package com.o3sa.mobipugapp.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android_2 on 10/31/2018.
 */

public class ForgotPasswordd extends Fragment {

    EditText forgotpw_edtxt;
    TextView forgotpw_txtt;
    Button submit_btnn;
    TextInputLayout forgotpw_lay;
    BasicComponents components;
    String user_type="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forgot_password,container,false);
        components = new BasicComponents(getActivity());

        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v){

        forgotpw_edtxt =(EditText)v.findViewById(R.id.forgotpw_edtxt);
        forgotpw_txtt =(TextView)v.findViewById(R.id.forgotpw_txtt);
        submit_btnn = (Button)v.findViewById(R.id.submit_btnn);
        forgotpw_lay = (TextInputLayout)v.findViewById(R.id.forgotpw_lay);

        forgotpw_edtxt.addTextChangedListener(new MyTextWatcher(forgotpw_edtxt));
        user_type= StoredObjects.UserType;
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
                case R.id.forgotpw_edtxt:
                    validateEmal();
                    break;
            }
        }
    }

    private boolean validateEmal() {
        if (forgotpw_edtxt.getText().toString().trim().isEmpty()) {

            requestFocus(forgotpw_edtxt);
            return false;
        } else {
            forgotpw_lay.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void assigndata(){

        components.CustomizeTextview(forgotpw_txtt, Constants.Medium,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.frgtpswd),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
        components.CustomizeButton(submit_btnn, Constants.XNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,20,0,20});

    }

    private void servicecalling() {

        submit_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());
                String email=forgotpw_edtxt.getText().toString().trim();

                if (StoredObjects.inputValidation(forgotpw_edtxt, getActivity().getResources().getString(R.string.pleaseenteremail),getActivity())) {
                    if(StoredObjects.isValidEmail(email)){
                        if(  user_type.equalsIgnoreCase("vendor")){
                            parsing_methods(0, StoredUrls.VendorForgotpw_url,"email="+email);
                        }else{
                            parsing_methods(0, StoredUrls.CustomerForgotpw_url,"email="+email);
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
                            StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.forgotpw_emailsentmsg),getActivity());
                            StoredObjects.Services_list.get(val).countDown.cancel();
                            getFragmentManager().popBackStack();
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
