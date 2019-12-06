package com.o3sa.mobipugapp.customerfragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by android_2 on 11/12/2018.
 */

public class CstmrLandingPage extends Fragment {

    EditText clandng_srch_edtx,clandng_srchcatgry_edtx;
    ImageView clandng_mapicon_img;
    RecyclerView clandng_recycleviwe;
    BasicComponents components;

    Integer [] clandng_list_img = {R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg};
    public ArrayList<HashMap<String, String>> offerslist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cstmr_landing_page,container,false);

        intialization(v);
        assigndata();
        servicecallingmethod();
        return v;
    }

    public void intialization(View v) {

        components = new BasicComponents(getActivity());
        StoredObjects.pagetype="home";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,"Home",Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        clandng_recycleviwe = (RecyclerView)v.findViewById(R.id.clandng_recycleviwe);
        clandng_srch_edtx = (EditText) v.findViewById(R.id.clandng_srch_edtx);
        clandng_srchcatgry_edtx = (EditText) v.findViewById(R.id.clandng_srchcatgry_edtx);
        clandng_mapicon_img = (ImageView) v.findViewById(R.id.clandng_mapicon_img);



        clandng_mapicon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentcalling(new HomePageNew());
            }
        });


    }

    public void assigndata() {

        components.CustomizeEditview(clandng_srch_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.srch_catgry), 0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{12, 0, 0, 0});
        components.CustomizeEditview(clandng_srchcatgry_edtx, Constants.Medium, getActivity().getApplicationContext().getResources().getString(R.string.srch_byproduct), 0, true, Constants.MatchCenterNormal + Constants.Gibson, new int[]{12, 0, 0, 0});

    }

    public void fragmentcalling( Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }


    private void servicecallingmethod() {
        parsing_methods(0, StoredUrls.GetOffers_url,"customer_id="+StoredObjects.UserId,Constants.POSTMETHOD);

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
                            offerslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
                            recyclerview.Assigndatatorecyleviewhashmap(clandng_recycleviwe,offerslist,"clandng", Constants.Listview,0,Constants.ver_orientation,R.layout.cstmr_landing_listitems);

                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();

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
