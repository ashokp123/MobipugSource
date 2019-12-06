package com.o3sa.mobipugapp.servicesparsing;


import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 22-08-2018.
 */

public class JsonParsing {

    public static ArrayList<HashMap<String, String>> GetJsonData(String results) throws JSONException {
        ArrayList<HashMap<String, String>> d_child = new ArrayList<HashMap<String, String>>();
        d_child.clear();
        JSONObject jsonrootObject =  new JSONObject(results);
        String status = jsonrootObject.getString("status");
        StoredObjects.LogMethod("status", "status:--"+status);
        try {
            if(status.equalsIgnoreCase("200")){
                String res = jsonrootObject.getString("results");
                StoredObjects.LogMethod("status_res", "status_res:--"+res);
                JSONArray cast = new JSONArray(res);
                JSONObject jsonObject = cast.getJSONObject(0);
                StoredObjects.LogMethod("tag", "tag >>>" + jsonObject.names());
                JSONArray myStringArray = jsonObject.names();
                for(int i = 0;i<cast.length();i++){
                    JSONObject jsonObject1 = cast.getJSONObject(i);
                    HashMap<String, String> dash_hash = new HashMap<String, String>();
                    for(int j = 0;j<myStringArray.length();j++){
                        dash_hash.put(myStringArray.getString(j), jsonObject1.getString(myStringArray.getString(j)));
                        StoredObjects.LogMethod("tag", "values >>>" +myStringArray.getString(j)+"---"+jsonObject1.getString(myStringArray.getString(j)));
                    }
                    d_child.add(dash_hash);
                }
            }else{
               d_child.clear();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }





        return d_child;
    }

    public static ArrayList<HashMap<String, String>> GetInnerJsonData(String results) throws JSONException {
        ArrayList<HashMap<String, String>> d_child = new ArrayList<HashMap<String, String>>();
        d_child.clear();
        try {
                JSONArray cast = new JSONArray(results);
                JSONObject jsonObject = cast.getJSONObject(0);
                StoredObjects.LogMethod("tag", "tag >>>" + jsonObject.names());
                JSONArray myStringArray = jsonObject.names();
                for(int i = 0;i<cast.length();i++){
                    JSONObject jsonObject1 = cast.getJSONObject(i);
                    HashMap<String, String> dash_hash = new HashMap<String, String>();
                    for(int j = 0;j<myStringArray.length();j++){
                        dash_hash.put(myStringArray.getString(j), jsonObject1.getString(myStringArray.getString(j)));
                        StoredObjects.LogMethod("tag", "values >>>" +myStringArray.getString(j)+"---"+jsonObject1.getString(myStringArray.getString(j)));
                    }
                    d_child.add(dash_hash);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }





        return d_child;
    }


}
