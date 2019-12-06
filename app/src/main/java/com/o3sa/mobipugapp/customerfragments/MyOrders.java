package com.o3sa.mobipugapp.customerfragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android_2 on 10/12/2018.
 */

public class MyOrders extends Fragment {

    RecyclerView myodrs_recycleviwe;
    BasicComponents components;
    public ArrayList<HashMap<String, String>> orderslist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.myorders,container,false);

        intialization(v);
        servicecallingmethod();
        return v;
    }

    public void intialization(View v){

        StoredObjects.pagetype="myorders";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.myorders),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        myodrs_recycleviwe = (RecyclerView)v.findViewById(R.id.myodrs_recycleviwe);

    }

    private void servicecallingmethod() {
        parsing_methods(0, StoredUrls.Myorders_url,"customer_id="+StoredObjects.UserId,Constants.POSTMETHOD);

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
                            orderslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
                            recyclerview.Assigndatatorecyleviewhashmap(myodrs_recycleviwe,orderslist,"myorders", Constants.Listview,0,Constants.ver_orientation,R.layout.myorders_listitems);

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
