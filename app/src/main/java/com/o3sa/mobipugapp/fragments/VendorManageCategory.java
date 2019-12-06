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
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.CustomRecyclerview;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 27-10-2018.
 */

public class VendorManageCategory  extends Fragment {

    public static RecyclerView c_categorylist;
    BasicComponents components;

    ImageView ctgy_search_img;
    EditText ctgy_search_tx;
    String[] sbcat_names  = {"Starters","Main Course","Desserts","Combos","Mia Addison","Sophia Lily","Emily Olivia"};
    public ArrayList<HashMap<String, String>> categorylist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customercategorylist,container,false);

        components=new BasicComponents(getActivity());

        intialization(v);
        Assigndata();
        servicecallingmethod();
        return v;
    }

    private void Assigndata() {

        components.CustomizeImageview(ctgy_search_img, new int[]{20,20}, R.drawable.pink_searchicon, new int[]{0,0,0,0});
        components.CustomizeEditview(ctgy_search_tx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.search),0,true,Constants.MatchLeftNormal+Constants.Roboto, new int[]{8,0,0,0});

    }

    public void intialization(View v){
        StoredObjects.pagetype="v_managecategory";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.mngctgry),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        c_categorylist = (RecyclerView)v.findViewById(R.id.c_categorylist);
        ctgy_search_img=(ImageView) v.findViewById(R.id.ctgy_search_img);
        ctgy_search_tx=(EditText) v.findViewById(R.id.ctgy_search_tx);


    }

    private void servicecallingmethod() {
        parsing_methods(0, StoredUrls.GetProductCategories_url,"vendor_id="+StoredObjects.UserId,Constants.POSTMETHOD);

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

                    if(val==0) {
                        try {
                            categorylist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
                            recyclerview.Assigndatatorecyleviewhashmap(c_categorylist,categorylist,"c_mangecategrylist", Constants.Listview,0, Constants.ver_orientation,R.layout.c_managecategoryitem);

                                  } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
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
