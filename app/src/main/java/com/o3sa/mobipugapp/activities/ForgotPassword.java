package com.o3sa.mobipugapp.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Created by Kiran on 30-10-2018.
 */

public class ForgotPassword extends Fragment {

    EditText forgotpw_edtx;
    TextView forgotpw_txt;
    Button submit_btn;
    String user_type="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forgotpassword,container,false);
        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }

    public void intialization(View v){
        LoginPage.header_lay.setVisibility(View.GONE);
        forgotpw_edtx =(EditText)v.findViewById(R.id.forgotpw_edtx);
        forgotpw_txt =(TextView)v.findViewById(R.id.forgotpw_txt);
        submit_btn = (Button)v.findViewById(R.id.submit_btn);
        user_type=StoredObjects.UserType;


    }

    public void assigndata(){

        BasicComponents components = new BasicComponents(getActivity());

        components.CustomizeEditview(forgotpw_edtx, Constants.XNormal,getActivity().getApplicationContext().getResources().getString(R.string.email),R.drawable.shadoweffect,true,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,25,0,25});
        components.CustomizeTextview(forgotpw_txt,Constants.Medium,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.frgtpswd),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,20,0,0});
        components.CustomizeButton(submit_btn, Constants.XNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,20,0,20});

    }

    private void servicecalling() {

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredObjects.hide_keyboard_activity(getActivity());
                String email=forgotpw_edtx.getText().toString().trim();

                if (StoredObjects.inputValidation(forgotpw_edtx, getActivity().getResources().getString(R.string.pleaseenteremail),getActivity())) {
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
                            getFragmentManager().popBackStack();
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


}

