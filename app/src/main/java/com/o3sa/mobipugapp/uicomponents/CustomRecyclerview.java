package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.o3sa.mobipugapp.dumpdata.DumpData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 22-09-2018.
 */

public class CustomRecyclerview {

    Activity activity;
    RecylerviewAdapter recylerviewAdapter;
    public static HashMapRecycleviewadapter hashMapRecycleviewadapter;


    public CustomRecyclerview(Activity m_activity) {
        activity=m_activity;
    }

    public void Assigndatatorecyleview(RecyclerView customrecyleview, ArrayList<DumpData> arrayList, String type, String viewtype, int no_of_columns, int orientation, int recylerviewlistitem) {//ArrayList<HashMap<String, String>> arrayList

        if(viewtype.equalsIgnoreCase("Grid")){

            //customrecyleview.setLayoutManager(new GridLayoutManager(activity, Constants.Num_Columns));

               customrecyleview.setLayoutManager(new GridLayoutManager(activity, no_of_columns));

        }else{
            customrecyleview.setLayoutManager(new LinearLayoutManager(activity, orientation, false));
        }

        recylerviewAdapter = new RecylerviewAdapter(activity,arrayList,type,customrecyleview,recylerviewlistitem);//
        customrecyleview.setAdapter(recylerviewAdapter);

    }
    public void Assigndatatorecyleviewhashmap(RecyclerView customrecyleview, ArrayList<HashMap<String, String>> arrayList, String type, String viewtype, int no_of_columns, int orientation, int recylerviewlistitem) {//ArrayList<HashMap<String, String>> arrayList

        if(viewtype.equalsIgnoreCase("Grid")){


            customrecyleview.setLayoutManager(new GridLayoutManager(activity, no_of_columns));

        }else{
            customrecyleview.setLayoutManager(new LinearLayoutManager(activity, orientation, false));
        }

        hashMapRecycleviewadapter = new HashMapRecycleviewadapter(activity,arrayList,type,customrecyleview,recylerviewlistitem);//
        customrecyleview.setAdapter(hashMapRecycleviewadapter);

    }
}
