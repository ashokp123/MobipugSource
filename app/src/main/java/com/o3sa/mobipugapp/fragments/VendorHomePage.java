package com.o3sa.mobipugapp.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.CustomRecyclerview;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kiran on 20-10-2018.
 */

public class VendorHomePage extends Fragment {

    BasicComponents components;
    ImageView hm_search_img;
    EditText hm_sarch_edtxt;
    RecyclerView hm_maincatgy_gv;
    CustomRecyclerview customRecyclerview;

    String[] hmpg_ctgries_names  = {"Offers","Products","Categories","Generate Bill"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vendorhomepage,container,false);


        init(v);
        //servicecalling();
        return v;
    }

    public void init(View b){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="home";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,"Home",Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        hm_search_img = (ImageView) b.findViewById(R.id.hm_search_img);
        hm_sarch_edtxt = (EditText) b.findViewById(R.id.hm_sarch_edtxt);
        hm_maincatgy_gv = (RecyclerView) b.findViewById(R.id.hm_maincatgy_gv);

        StoredObjects.hmpg_ctgries_names_list.clear();
        for (int i = 0;i<hmpg_ctgries_names.length;i++){
            DumpData dumpData = new DumpData();
            dumpData.hmpg_ctgries_names = hmpg_ctgries_names[i];
            StoredObjects.hmpg_ctgries_names_list.add(dumpData);
        }

        customRecyclerview = new CustomRecyclerview(getActivity());
        customRecyclerview.Assigndatatorecyleview(hm_maincatgy_gv, StoredObjects.hmpg_ctgries_names_list,"vendor_homepage",
                Constants.Gridview, 2, Constants.ver_orientation,R.layout.vendorhm_listitem);


        AssignData();


    }



    public void AssignData(){
        components.CustomizeEditview(hm_sarch_edtxt, Constants.Normal,getActivity().getApplicationContext().getResources().getString(R.string.search),0,true,Constants.MatchCenterNormal+Constants.Roboto, new int[]{10,0,0,0});
        components.CustomizeImageview(hm_search_img, new int[]{25,25},R.drawable.pink_searchicon, new int[]{8,0,0,0});

    }




}
