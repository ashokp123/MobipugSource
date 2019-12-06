package com.o3sa.mobipugapp.customerfragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by Kiran on 27-10-2018.
 */

public class SubCategoryList extends Fragment {

    RecyclerView subcat_recycleviwe;
    BasicComponents components;

    public ArrayList<HashMap<String, String>> sub_ctgry_list;

    String category_id;
    TextView nodata_txt;
    LinearLayout nodatalay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_category,container,false);

        try {
            Bundle bundle = getArguments();
            Log.i("category_id", "category_id:--"+bundle);
            if (bundle != null) {
                category_id = bundle.getString("category_id");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        intialization(v);
        servicecallingmethod();
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.subcategory_lst),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="subcategorylist";
        Sidemenu.updatemenu(StoredObjects.pagetype);

        subcat_recycleviwe = (RecyclerView)v.findViewById(R.id.subcat_recycleviwe);

        nodata_txt=(TextView) v.findViewById(R.id.nodata_txt);
        nodatalay=(LinearLayout) v.findViewById(R.id.nodatalay);
        components.CustomizeTextview(nodata_txt, Constants.XXXNormal, R.color.black, getActivity().getResources().getString(R.string.no_catgrsfound), Constants.WrapCenterBold+ Constants.Roboto, new int[]{10,10,10,10});


    }

    private void servicecallingmethod() {
        parsing_methods(0, StoredUrls.GetProductSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);
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
                            sub_ctgry_list = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
                            recyclerview.Assigndatatorecyleviewhashmap(subcat_recycleviwe, sub_ctgry_list, "subcategry",
                                    Constants.Listview, 0, Constants.ver_orientation, R.layout.subcategory_listitems);
                            if(sub_ctgry_list.size()==0){
                                subcat_recycleviwe.setVisibility(View.GONE);
                                nodatalay.setVisibility(View.VISIBLE);
                            }else{
                                subcat_recycleviwe.setVisibility(View.VISIBLE);
                                nodatalay.setVisibility(View.GONE);
                            }
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
