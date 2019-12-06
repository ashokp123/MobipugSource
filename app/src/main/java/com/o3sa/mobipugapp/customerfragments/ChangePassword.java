package com.o3sa.mobipugapp.customerfragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o3sa.mobipugapp.R;

import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kiran on 30-10-2018.
 */

public class ChangePassword extends Fragment {

    BasicComponents components;
    EditText vregn_oldpswd_edtx,vregn_newpswd_edtx,vregn_cnfmpswd_edtx;
    Button vregn_sbmt_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vendor_chngepaswrd_popup,container,false);

        intialization(v);
        //callingService();
        assigndata();
        return v;

    }

    public void intialization(View v){

        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="changepassword";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.changepswd),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        vregn_oldpswd_edtx=(EditText) v.findViewById(R.id.vregn_oldpswd_edtx);
        vregn_newpswd_edtx=(EditText) v.findViewById(R.id.vregn_newpswd_edtx);
        vregn_cnfmpswd_edtx=(EditText) v.findViewById(R.id.vregn_cnfmpswd_edtx);

        vregn_sbmt_btn =(Button) v.findViewById(R.id.vregn_sbmt_btn);

    }

    public void assigndata(){

        components.CustomizeEditview(vregn_oldpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.oldpswd),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vregn_newpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.newpswd),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(vregn_cnfmpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.confrmpassword),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.SetPasswordHint(vregn_oldpswd_edtx);
        components.SetPasswordHint(vregn_newpswd_edtx);
        components.SetPasswordHint(vregn_cnfmpswd_edtx);
        components.CustomizeButton(vregn_sbmt_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit_),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,22,0,22});

    }

    private void callingService(){

        vregn_sbmt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoredObjects.hide_keyboard_activity(getActivity());

                    String old_password = vregn_oldpswd_edtx.getText().toString().trim();
                String new_password = vregn_newpswd_edtx.getText().toString().trim();
                String cnfrm_password = vregn_cnfmpswd_edtx.getText().toString().trim();

                if (StoredObjects.inputValidation(vregn_oldpswd_edtx, getActivity().getResources().getString(R.string.pls_ntr_oldpwd), getActivity())) {
                    if (StoredObjects.inputValidation(vregn_newpswd_edtx, getActivity().getResources().getString(R.string.pls_ntr_nwpwd), getActivity())) {
                        if (StoredObjects.inputValidation(vregn_cnfmpswd_edtx, getActivity().getResources().getString(R.string.pls_ntr_cnfrmpwd), getActivity())) {

                            if (new_password.equals(cnfrm_password)) {

                                parsing_methods(0, StoredUrls.CstmrChangePassword_url,"customer_id="+StoredObjects.UserId+"&old_password="+old_password+"&new_password="+new_password,Constants.POSTMETHOD);

                            }else {
                                Toast.makeText(getActivity(), "Please select all fields", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.invalidata), getActivity());

                        }

                    }

                }

            }

        });

    }

    public void parsing_methods(final  int val,String url,String parameters,String parsing_type){

        WebServicesCalling webServicesCalling = new WebServicesCalling(getActivity());
        webServicesCalling.calling_webservices(url,parameters,val,parsing_type);

        StoredObjects.Services_list.get(val).countDown = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                StoredObjects.LogMethod("Seconds remaining: "," Seconds remaining: " + millisUntilFinished / 100);

                if(StoredObjects.Services_list.get(val).Result != ""){

                    StoredObjects.LogMethod("Result",StoredObjects.Services_list.get(val).Result);
                    StoredObjects.LogMethod("Service_called",StoredObjects.Services_list.get(val).url);

                    if(val==0){
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.cnge_pwd_msg),getActivity());
                            }else{
                                String error = jsonObject.getString("error");
                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){
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

}



