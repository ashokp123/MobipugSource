package com.o3sa.mobipugapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

/**
 * Created by Kiran on 30-10-2018.
 */

public class UserProfile extends Fragment {


    BasicComponents components;
    EditText vregn_oldpswd_edtx,vregn_newpswd_edtx,vregn_cnfmpswd_edtx;
    Button vregn_sbmt_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vendor_chngepaswrd_popup,container,false);

        intialization(v);
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

        components.CustomizeEditview(vregn_oldpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.oldpswd),R.drawable.shadoweffect, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeEditview(vregn_newpswd_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.newpswd),R.drawable.shadoweffect, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0,10,0,0});
        components.CustomizeEditview(vregn_cnfmpswd_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.confrmpassword),R.drawable.shadoweffect, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{0,10,0,0});
        components.SetPasswordHint(vregn_oldpswd_edtx);
        components.SetPasswordHint(vregn_newpswd_edtx);
        components.SetPasswordHint(vregn_cnfmpswd_edtx);
        components.CustomizeButton(vregn_sbmt_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit_),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,22,0,22});

    }

}




