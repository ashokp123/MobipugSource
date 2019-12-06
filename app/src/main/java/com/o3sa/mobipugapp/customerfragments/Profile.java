package com.o3sa.mobipugapp.customerfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.CustomRecyclerview;

/**
 * Created by android_2 on 10/12/2018.
 */

public class Profile extends Fragment {


    RecyclerView prfle_recycleviwe;
    BasicComponents components;

   // String[] prfle_catgrynms  = {"Edit Profile","Change Password","Settings","Legal Information","Send Feedback","Logout"};

    String[] prfle_catgrynms  = {"Edit Profile","Change Password","Send Feedback","Legal Information","Logout"};
    String[] v_prfle_catgrynms  = {"View Profile","Change Password","Send Feedback","Complete Profile","Logout"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile,container,false);

        intialization(v);
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="profile";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.setttngs),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        prfle_recycleviwe = (RecyclerView)v.findViewById(R.id.prfle_recycleviwe);


        StoredObjects.profilearraylist.clear();
        for (int i = 0;i<prfle_catgrynms.length;i++){
            DumpData dumpData = new DumpData();

            if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                dumpData.prflecatgrylist = v_prfle_catgrynms[i];
            }else{
                dumpData.prflecatgrylist = prfle_catgrynms[i];
            }

            StoredObjects.profilearraylist.add(dumpData);
        }

        CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
        recyclerview.Assigndatatorecyleview(prfle_recycleviwe,StoredObjects.profilearraylist,"profile", Constants.Listview,0, Constants.ver_orientation,R.layout.profile_listitems);

    }

}
