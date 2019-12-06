package com.o3sa.mobipugapp.storedobjects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.ServicesDetails;
import com.o3sa.mobipugapp.uicomponents.RecylerviewAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Kiran on 22-08-2018.
 */

public class StoredObjects {

     // 0 - URL
    // 1 - parameters
    // 2 - type
    // 3

   //
   Activity activity;

    public static String UserId="1";
    public static String UserType="";
    public static String ProductType="";
    public static String GCMID="127272727";
    public static String Profileupdate="No";
    public static String picuri_path="/mobipugapp/images";
    public static String OfferId="";
    public static String Playstorelink="";
    public static int position=0;
    public static Double maxvalue=100.0;
    public  static String CategoryId="";
    public static ArrayList<ServicesDetails> Services_list = new ArrayList<>();
    public static  ArrayList<HashMap<String, String>> datalist = new ArrayList<>();
    public StoredObjects(Activity m_activity) {
        activity=m_activity;
    }

    public static ArrayList<DumpData> profilearraylist = new ArrayList<>();

    public static ArrayList<DumpData> lndng_pg_list = new ArrayList<>();
    public static ArrayList<DumpData> hmpg_vwpager_list = new ArrayList<>();
    public static ArrayList<DumpData> hmpg_ctgries_names_list = new ArrayList<>();
    public static ArrayList<DumpData> hmpg_poplr_keywrds_list = new ArrayList<>();
    public static ArrayList<DumpData> prdct_pg_list1 = new ArrayList<>();
    public static ArrayList<DumpData> catgry_details_list = new ArrayList<>();
    public static ArrayList<DumpData> mycart_array = new ArrayList<>();

    public static ArrayList<DumpData> cart_rv_ordr_list = new ArrayList<>();
    public static ArrayList<DumpData> price_rnge_list = new ArrayList<>();
    public static ArrayList<DumpData> order_popup_arraylist = new ArrayList<>();
    public static ArrayList<DumpData> attributesarray = new ArrayList<>();

    public static  ArrayList<HashMap<String, String>> offerslist = new ArrayList<>();
    public static ArrayList<DumpData> Cstmrlandnglist = new ArrayList<>();

    public static String[] offerdiscnttype_array = {"Fixed Amount","Percentage"};
    public static String[] offertype_array = {"Public","Private"};

    public static void intilization(){

        ServicesDetails call_service = new ServicesDetails();
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);
        Services_list.add(call_service);

    }

    public static int count=0 ;
    public static String pagetype="" ;
    public static String redirecpage="" ;

    public static String Qrcode="" ;
    public static int viewpos=0 ;
    public static int proviewpos=0 ;

    public static String ctgry_id = "";
    public static String sub_ctgry_id = "";
    public static String v_reg_busns_img1 = "";
    public static String v_reg_busns_bg_img2 = "";
    public static String v_reg_idproof_img1 = "";
    public static String v_reg_panimg2 = "";

    public static ArrayList<String> vendorprofilearray = new ArrayList<String>();

    public static String vndr_idproof = "",vndr_pancard = "",vndr_fullname = "",vndr_ac = "",vndr_ifsc = "",vndr_pckg_type = "";

    public static String vndr_business_img = "",vndr_business_bg_img = "",vndr_business_name = "",vndr_ctgry = "",vndr_sub_ctgry = "",vndr_abt_us = "";

    public static String vndr_lat = "",vndr_lng = "",vndr_telephone = "",vndr_fax = "",vndr_email = "",vndr_bsness_addrss = "",vndr_map_addrss = "",vndr_pincode = "",vndr_website = "";

    public static String get_product_id = "",vndr_adhaar_card = "";

    //Log
    public static void LogMethod(String keyval, String val) {
        Log.i(keyval, val);
    }

    //Displaying toast
    public static void ToastMethod(String meeasge, Context context) {
        Toast.makeText(context, meeasge, 0).show();
    }

    //Display current date
    public  String GetCurrentDate(String displaydateformat){
        String CurrentDate = new SimpleDateFormat(displaydateformat).format(new Date());
        return CurrentDate;
    }

    //Display selected date
    public  static String GetSelectedDate(int year,int month,int day){
        String selectddate="";
        if(month<10&&day>=10){
            selectddate = "0"+month + "/" + day + "/" + year;
        }else if(month>=10&&day<10){
            selectddate = month + "/" +"0"+ day + "/" + year;
        }else if(month<10&&day<10){
            selectddate ="0"+ month + "/" +"0"+ day + "/" + year;
        }else{
            selectddate = month + "/" + day + "/" + year;
        }
        return selectddate;
    }
    //Generate Random string
    public  String GeneraterandomString(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("ss");//yyyy-MM-dd HH:mm:ss.SSS
        String currentDateandTime = sdf.format(new Date());
        System.out.println("randomstring:--"+output+currentDateandTime);
        return output+currentDateandTime;
    }



    //Calculating difference between two dates
    public static boolean DaysDifference(String fromdate, String todate){
        boolean ret =false;
        SimpleDateFormat dfDate  = new SimpleDateFormat("MM/dd/yyyy");
        Date todate_val = null;
        Date fromdate_val = null;
        try {
            todate_val = dfDate.parse(todate);
            fromdate_val = dfDate.parse(fromdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int diffInDays = (int) ((todate_val.getTime() - fromdate_val.getTime())/ (1000 * 60 * 60 * 24));
        if(diffInDays>=0){
            ret =true;
        }else{
            ret=false;
        }
        return ret;
    }

    //Change date format
    public static String ConvertDate(String inputDate,String from_format,String to_format) {

        //EEE, MMM d, ''yy --- Ex: Wed, Jul 4, '01
        //h:mm a  --- Ex: 12:08 PM
        //K:mm a  --- Ex: 0:08 PM
        //yyyy.MMMMM.dd hh:mm aaa --- Ex: 2001.July.04 12:08 PM
        //EEE, d MMM yyyy HH:mm:ss --- Ex: Wed, 4 Jul 2001 12:08:56
        //dd-MM-yyyy --- Ex: 04-07-2001
        //12 hrs to 24 hrs --- hh:mm a ---> kk:mm:ss

        DateFormat theDateFormat = new SimpleDateFormat(from_format);
        Date date = null;

        try {
            date = theDateFormat.parse(inputDate);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch (Exception exception) {
            // Generic catch. Do what you want.
        }
        theDateFormat = new SimpleDateFormat(to_format);//EEEE

        return theDateFormat.format(date);
    }

    //EEE, MMM d, ''yy --- Ex: Wed, Jul 4, '01
    //h:mm a  --- Ex: 12:08 PM
    //K:mm a  --- Ex: 0:08 PM
    //yyyy.MMMMM.dd hh:mm aaa --- Ex: 2001.July.04 12:08 PM
    //EEE, d MMM yyyy HH:mm:ss --- Ex: Wed, 4 Jul 2001 12:08:56
    //dd-MM-yyyy --- Ex: 04-07-2001
    //12 hrs to 24 hrs --- hh:mm a ---> kk:mm:ss


    //Difference between two times

    public long TimeDifference(String fromtime,String totime){

        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(fromtime);
            d2 = format.parse(totime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");
        return diffMinutes;
    }

    //Save shared pref data
    public void savedata(Context context, String name, String value){

        SharedPreferences pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.commit();
    }

    //Get saved shared pref data
    public  String getsaveddata(Context context,String name){

        SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String get_name = prefs.getString(name, null);
        return get_name;
    }

    // Call Intent method
    //Need to add call Permissions in manifest file
    public  void AlertForCall(final Activity activity, final String Mobilenum) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

             builder.setMessage(Mobilenum)
                .setCancelable(false)
                 .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+Mobilenum));
                        activity.startActivity(callIntent);

                }
             })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {

                          dialog.dismiss();
                     }

        });

        //Creating dialog box
        AlertDialog alert = builder.create();
             alert.show();
    }
    //Display alert
    public void DisplayAlertDialog(String string,Activity activity) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);

        builder.setMessage(string)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }



    //Assign data to recycler view
    public void Assigndatatorecyleview(Activity activity, RecyclerView customrecyleview, RecylerviewAdapter recylerviewAdapter, ArrayList<HashMap<String, String>> arrayList, String type, String viewtype, int noofcolumns, int orientation, int recylerviewlistitem) {

        if(viewtype.equalsIgnoreCase("Grid")){
            customrecyleview.setLayoutManager(new GridLayoutManager(activity, noofcolumns));
        }else{
            customrecyleview.setLayoutManager(new LinearLayoutManager(activity, orientation, false));
        }

        //recylerviewAdapter = new RecylerviewAdapter(activity,arrayList,type,customrecyleview,recylerviewlistitem);//
        //customrecyleview.setAdapter(recylerviewAdapter);

    }


    //Convert pixel to dp
    public static int dpFromPx(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    //Convert dp to pixel
    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    //Validation check

    public boolean CheckValidation(Activity activity,EditText edittext, String message) {
        if (edittext.getText().toString().equals("")||edittext.getText().toString().equals(null)) {
            Toast.makeText(activity, message,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Check Email Validation

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Hide keyboard on click
    public static void hide_keyboard_activity(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Hide keyboard on click
    public static void hide_keyboard_context(Context activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((Activity) activity).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

    public static boolean inputValidation(EditText edittext, String message,Activity activity) {
        if (edittext.getText().toString().trim().equals("")||edittext.getText().toString().trim().equals(null)) {
            Toast.makeText(activity, message,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
