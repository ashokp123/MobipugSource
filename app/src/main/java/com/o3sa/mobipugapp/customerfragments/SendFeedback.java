package com.o3sa.mobipugapp.customerfragments;

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

public class SendFeedback extends Fragment {


    BasicComponents components;
    EditText sendfd_name_etx,sendfd_email_etx,sendfd_message_etx;
    Button sendfb_sbmt_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sendfeedback,container,false);

        intialization(v);
        assigndata();
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="sendfeedback";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.sendfeedback),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});


        sendfd_name_etx=(EditText) v.findViewById(R.id.sendfd_name_etx);
        sendfd_email_etx=(EditText) v.findViewById(R.id.sendfd_email_etx);
        sendfd_message_etx=(EditText) v.findViewById(R.id.sendfd_message_etx);

        sendfb_sbmt_btn =(Button) v.findViewById(R.id.sendfb_sbmt_btn);

    }

    public void assigndata(){

        components.CustomizeEditview(sendfd_name_etx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.name),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeEditview(sendfd_email_etx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.email),0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{10,0,0,0});
        components.CustomizeMultilineEditview(sendfd_message_etx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.entermessage),R.drawable.shadowwithoutradius,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,10},4);

        sendfd_message_etx.setPadding(20,5,20,5);

        components.CustomizeButton(sendfb_sbmt_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit_),R.drawable.cntinue_btn_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,45}, new int[]{0,0,0,10});

    }

}




