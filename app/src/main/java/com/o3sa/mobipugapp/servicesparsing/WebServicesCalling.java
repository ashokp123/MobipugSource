package com.o3sa.mobipugapp.servicesparsing;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;

public class WebServicesCalling {

    Activity this_activity;
    int this_int;

    public WebServicesCalling(Activity activity) {

        if (activity!=null){
            this.this_activity = activity;
        }


    }

    public void calling_webservices(String url,String paramaters,int this_int,String parsing_type){
        StoredObjects.LogMethod("Seconds remaining: "," parameters:---- " + paramaters);
        if (InterNetChecker.isNetworkAvailable(this_activity)) {
            this.this_int = this_int;
            ServicesDetails details_param = new ServicesDetails();

            details_param.url = url;
            details_param.parsing_type=parsing_type;
            if(parsing_type.equalsIgnoreCase("image")){
                details_param.imagepath=paramaters;
            }else{
                details_param.imagepath="";
                String[] param_split = paramaters.split("&", 40);

                for(int i =0;i<param_split.length;i++){

                    String[] param_inner_split = param_split[i].split("=", 2);
                    if(param_inner_split.length == 0){

                    }
                    else if(param_inner_split.length == 1){
                        details_param.nameValuePairs.add(new BasicNameValuePair(param_inner_split[0],""));
                    }else{
                        details_param.nameValuePairs.add(new BasicNameValuePair(param_inner_split[0],param_inner_split[1]));
                    }
                }

            }
            StoredObjects.Services_list.remove(this_int);
            StoredObjects.Services_list.add(this_int,details_param);

            new Calling_service().execute();
        }else{
            Toast.makeText(this_activity, "Please Check Internet Connection.", 0).show();
        }

    }

    public class Calling_service extends AsyncTask<String, String, String> {
        String strResult = "";
        @Override
        protected void onPreExecute() {
            CustomProgressbar.Progressbarshow(this_activity);
        }
        @Override
        protected String doInBackground(String... params) {

            if( StoredObjects.Services_list.get(this_int).parsing_type.equalsIgnoreCase("post")){
                try {

                    strResult = HttpPostClass.PostMethod(StoredUrls.BaseUrl + StoredObjects.Services_list.get(this_int).url , StoredObjects.Services_list.get(this_int).nameValuePairs);

                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SocketTimeoutException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ConnectTimeoutException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else   if( StoredObjects.Services_list.get(this_int).parsing_type.equalsIgnoreCase("get")){
                try {
                    strResult = HttpPostClass.GET(StoredUrls.BaseUrl + StoredObjects.Services_list.get(this_int).url);
                }catch (Exception e){

                }

            }else   if( StoredObjects.Services_list.get(this_int).parsing_type.equalsIgnoreCase("image")){
                strResult = HttpPostClass.uploadFile(StoredObjects.Services_list.get(this_int).imagepath,StoredUrls.BaseUrl + StoredObjects.Services_list.get(this_int).url);
            }


            StoredObjects.Services_list.get(this_int).Result = strResult;

            return strResult;
        }

        protected void onPostExecute(String results) {
            if (results != null) {
                CustomProgressbar.Progressbarcancel(this_activity);
            }

            String status;
            try {
                JSONObject jsonrootObject =  new JSONObject(results);
                status = jsonrootObject.getString("status");
                StoredObjects.LogMethod("status", "status:--"+status);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                StoredObjects.LogMethod("status_res", "status_res:--"+e1);
                e1.printStackTrace();
            }catch (Exception e) {
                StoredObjects.LogMethod("status_res", "status_res:--"+e);
                e.printStackTrace();
            }

        }
    }


}
