package com.o3sa.mobipugapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by android_2 on 10/16/2018.
 */

public class AddOffer extends Fragment {

   private static TextView adofr_ofrtpe_tx,adofr_publc_tx,adofr_private_tx,adofr_brndnme_tx,adofr_prce_tx,adofr_brndprce_tx,adofr_brndscunt_tx,
            adofr_ratngprcnt_tx,adofr_strtdate_tx,adofr_enddate_tx;


    ImageView adofr_catgry_img,adofr_dsplydate_img,adofr_dsplyendate_img;
    RadioButton adofr_radiobutn,adofr_radiobutnone;
    Button adofr_srch_btn;
    LinearLayout start_date_lay,end_date_lay;

    EditText adofr_amount_edtx,adofr_dscntype_edtx, adofr_prdct_edtx,adofr_slctctgry_edtx,adofr_slctsbctgry_edtx;
    LinearLayout adofr_slctctgry_lay,adofr_slctsbctgry_lay,adofr_prdct_lay,adofr_dscntype_lay;
    BasicComponents components;
    public ArrayList<HashMap<String, String>> productlist,categorieslist,subcategorieslist;
    private PopupWindow ofrtype_popup,products_popup,category_popup,subcategory_popup;

    LinearLayout ofr_productlay;
    public static EditText adofr_dsplydate_edtx,adofr_dsplyenddate_edtx;
    String category_id="",subcategory_id="",product_id="",discounttype="",offertype="";
    public static String start_date="",end_date="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_offer,container,false);
        intialization(v);
        assigndata();
        servicecalling();
        return v;
    }


    public void intialization(View v){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="addoffer";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.add_offer),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        adofr_slctctgry_lay = (LinearLayout)v.findViewById(R.id.adofr_slctctgry_lay);
        adofr_slctsbctgry_lay = (LinearLayout)v.findViewById(R.id.adofr_slctsbctgry_lay);
        adofr_prdct_lay = (LinearLayout)v.findViewById(R.id.adofr_prdct_lay);
        adofr_dscntype_lay = (LinearLayout)v.findViewById(R.id.adofr_dscntype_lay);

        adofr_slctctgry_edtx =(EditText) v.findViewById(R.id.adofr_slctctgry_edtx);
        adofr_slctsbctgry_edtx= (EditText) v.findViewById(R.id.adofr_slctsbctgry_edtx);
        adofr_prdct_edtx =(EditText)v.findViewById(R.id.adofr_prdct_edtx);
        adofr_dscntype_edtx =(EditText) v.findViewById(R.id.adofr_dscntype_edtx);
        adofr_amount_edtx=(EditText) v.findViewById(R.id.adofr_amount_edtx);
        adofr_ofrtpe_tx = (TextView)v.findViewById(R.id.adofr_ofrtpe_tx);
        adofr_publc_tx = (TextView)v.findViewById(R.id.adofr_publc_tx);
        adofr_private_tx = (TextView)v.findViewById(R.id.adofr_private_tx);
        adofr_brndnme_tx =(TextView)v.findViewById(R.id.adofr_brndnme_tx);
        adofr_prce_tx =(TextView)v.findViewById(R.id.adofr_prce_tx);
        adofr_brndprce_tx =(TextView)v.findViewById(R.id.adofr_brndprce_tx);
        adofr_brndscunt_tx =(TextView)v.findViewById(R.id.adofr_brndscunt_tx);
        adofr_ratngprcnt_tx =(TextView)v.findViewById(R.id.adofr_ratngprcnt_tx);
        adofr_strtdate_tx =(TextView)v.findViewById(R.id.adofr_strtdate_tx);
        adofr_enddate_tx =(TextView)v.findViewById(R.id.adofr_enddate_tx);

        adofr_dsplydate_edtx =(EditText)v.findViewById(R.id.adofr_dsplydate_edtx);
        adofr_dsplyenddate_edtx =(EditText)v.findViewById(R.id.adofr_dsplyenddate_edtx);

        start_date_lay = (LinearLayout)v.findViewById(R.id.start_date_lay);
        end_date_lay = (LinearLayout)v.findViewById(R.id.end_date_lay);
        adofr_catgry_img = (ImageView)v.findViewById(R.id.adofr_catgry_img);
        adofr_dsplydate_img = (ImageView)v.findViewById(R.id.adofr_dsplydate_img);
        adofr_dsplyendate_img = (ImageView)v.findViewById(R.id.adofr_dsplyendate_img);

        adofr_radiobutn = (RadioButton)v.findViewById(R.id.adofr_radiobutn);
        adofr_radiobutnone = (RadioButton)v.findViewById(R.id.adofr_radiobutnone);

        adofr_srch_btn = (Button)v.findViewById(R.id.adofr_srch_btn);
        ofr_productlay=(LinearLayout) v.findViewById(R.id.ofr_productlay);
        ofr_productlay.setVisibility(View.GONE);
        adofr_radiobutn.setChecked(true);
        adofr_radiobutnone.setChecked(false);
        offertype="Public";
        adofr_radiobutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adofr_radiobutn.isChecked())
                {
                    offertype="Public";
                    adofr_radiobutn.setChecked(true);
                    adofr_radiobutnone.setChecked(false);
                }
            }
        });

        adofr_radiobutnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adofr_radiobutnone.isChecked())
                {
                    offertype="Private";
                    adofr_radiobutnone.setChecked(true);
                    adofr_radiobutn.setChecked(false);
                }
            }
        });
        start_date_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStartDatePicker(getActivity());
            }
        });

        adofr_dsplydate_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStartDatePicker(getActivity());
            }
        });

        adofr_dsplyenddate_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=adofr_dsplydate_edtx.getText().toString();
                if(date.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod("Please select Start date",getActivity());
                }else{
                    ShowEndDatePicker(getActivity());
                }

            }
        });
        end_date_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=adofr_dsplydate_edtx.getText().toString();

                if(date.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod("Please select Start date",getActivity());
                }else{
                    ShowEndDatePicker(getActivity());
                }
            }
        });


        adofr_slctctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(adofr_slctctgry_edtx,adofr_slctctgry_lay);
            }
        });

        adofr_slctsbctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category_id.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.sel_category),getActivity());
                }else{
                    if(subcategorieslist.size()==0){
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.nosubctgres),getActivity());
                    }else{
                        SubCategoryFilterPopup(adofr_slctsbctgry_edtx,adofr_slctsbctgry_lay);
                    }

                }

            }
        });
        adofr_prdct_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subcategory_id.equalsIgnoreCase("")&&category_id.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.sel_subcategory),getActivity());
                }else{

                    if(productlist.size()==0){
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.noproducts),getActivity());
                    }else{
                        ProductFilterPopup(adofr_prdct_edtx,adofr_prdct_lay);
                    }
                }

            }
        });

        adofr_prdct_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subcategory_id.equalsIgnoreCase("")&&category_id.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.sel_subcategory),getActivity());
                }else{

                    if(productlist.size()==0){
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.noproducts),getActivity());
                    }else{
                        ProductFilterPopup(adofr_prdct_edtx,adofr_prdct_lay);
                    }
                }

            }
        });

        adofr_dscntype_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFilterPopup(adofr_dscntype_edtx,adofr_dscntype_lay);
            }
        });

        adofr_slctctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(adofr_slctctgry_edtx,adofr_slctctgry_lay);
            }
        });
        adofr_slctsbctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category_id.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.sel_category),getActivity());
                }else{
                    if(subcategorieslist.size()==0){
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.nosubctgres),getActivity());
                    }else{
                        SubCategoryFilterPopup(adofr_slctsbctgry_edtx,adofr_slctsbctgry_lay);
                    }
                }
            }
        });

        adofr_dscntype_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFilterPopup(adofr_dscntype_edtx,adofr_dscntype_lay);
            }
        });
    }

    public void assigndata(){
        components.CustomizeTextview(adofr_ofrtpe_tx,Constants.Small,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.ofrtype),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,10,0,0});
        components.CustomizeTextview(adofr_publc_tx,Constants.Small,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.publc),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeTextview(adofr_private_tx,Constants.Small,R.color.serch_nrml_clr,getActivity().getApplicationContext().getResources().getString(R.string.prvate),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,10,0});
        components.CustomizeTextview(adofr_strtdate_tx,Constants.XSmall,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.start_date),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,5,0,5});
        components.CustomizeTextview(adofr_enddate_tx,Constants.XSmall,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.end_date),Constants.WrapCenterNormal+Constants.Gibson, new int[]{5,5,0,5});
        components.CustomizeImageview(adofr_dsplydate_img, new int[]{16,18}, R.drawable.calendericon, new int[]{0,0,5,0});
        components.CustomizeImageview(adofr_dsplyendate_img, new int[]{16,18}, R.drawable.calendericon, new int[]{0,0,5,0});
        components.CustomizeButton(adofr_srch_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.submit),R.drawable.list_bottom_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,42}, new int[]{0,20,0,20});

    }




    public void ShowStartDatePicker(Activity activity) {
        DialogFragment newFragment = new StartDatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }

    public static class StartDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
            datePicker.getDatePicker().setMinDate(c.getTimeInMillis() - 1000);

            //Create a new instance of DatePickerDialog and return it
            return datePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            month = month + 1;
            String selecteddate="";

            if(month<10&&day>=10){
                start_date = "0"+month + "/" + day + "/" + year;
                selecteddate=day + "/"  + "0"+month + "/"+ year;
            }else if(month>=10&&day<10){
                start_date = month + "/" +"0"+ day + "/" + year;
                selecteddate="0"+day + "/"  + month + "/"+ year;
            }else if(month<10&&day<10){
                start_date ="0"+ month + "/" +"0"+ day + "/" + year;
                selecteddate="0"+day + "/"  +"0"+  month + "/"+ year;
            }else{
                start_date = month + "/" + day + "/" + year;
                selecteddate=day + "/"+  month + "/"+ year;
            }

            adofr_dsplydate_edtx.setText(selecteddate);

        }

    }

    public void ShowEndDatePicker(Activity activity) {
        DialogFragment newFragment = new EndDatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }

    public static class EndDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            return datePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month + 1;
            String selecteddate="";

            if(month<10&&day>=10){
                end_date = "0"+month + "/" + day + "/" + year;
                selecteddate=day + "/"  + "0"+month + "/"+ year;
            }else if(month>=10&&day<10){
                end_date = month + "/" +"0"+ day + "/" + year;
                selecteddate="0"+day + "/"  + month + "/"+ year;
            }else if(month<10&&day<10){
                end_date ="0"+ month + "/" +"0"+ day + "/" + year;
                selecteddate="0"+day + "/"  +"0"+  month + "/"+ year;
            }else{
                end_date = month + "/" + day + "/" + year;
                selecteddate=day + "/"+  month + "/"+ year;
            }

            String startdate=adofr_dsplydate_edtx.getText().toString();
            String enddate=selecteddate;

            if(StoredObjects.DaysDifference(startdate,enddate)==true) {
                adofr_dsplyenddate_edtx.setText(selecteddate);
            }else{
                StoredObjects.ToastMethod("End date must be after Start date.",getActivity());
            }

        }

    }

    private void servicecalling() {

        parsing_methods(0, StoredUrls.GetProductCategories_url,"",Constants.POSTMETHOD);

        adofr_srch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoredObjects.hide_keyboard_activity(getActivity());
                String amount=adofr_amount_edtx.getText().toString();
                if (StoredObjects.inputValidation(adofr_slctctgry_edtx, getActivity().getResources().getString(R.string.seclt_catgry),getActivity())) {
                    if (StoredObjects.inputValidation(adofr_slctsbctgry_edtx, getActivity().getResources().getString(R.string.seclt_sub_catgry),getActivity())) {
                        if (StoredObjects.inputValidation(adofr_prdct_edtx, getActivity().getResources().getString(R.string.seclt_product),getActivity())) {
                            if (StoredObjects.inputValidation(adofr_dscntype_edtx, getActivity().getResources().getString(R.string.seclt_discounttype),getActivity())) {
                                if (StoredObjects.inputValidation(adofr_dsplydate_edtx, getActivity().getResources().getString(R.string.seclt_ofrstartdate),getActivity())) {
                                    if (StoredObjects.inputValidation(adofr_dsplyenddate_edtx, getActivity().getResources().getString(R.string.seclt_ofrenddate),getActivity())) {
                                        if (StoredObjects.inputValidation(adofr_amount_edtx, getActivity().getResources().getString(R.string.seclt_oframount),getActivity())) {
                                            parsing_methods(3, StoredUrls.AddOffers_url,"vendor_id="+StoredObjects.UserId+"&product_id="+product_id+"&offer_type="+offertype+"&discount_type="+discounttype+"&start_date="+start_date+"&end_date="+end_date+"&amount="+amount,Constants.POSTMETHOD);
                                        }                                    }
                                }
                            }
                            }

                        }
                    }
                }

        });
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
                        //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                        try {
                            categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }

                    }else if(val==1) {
                        //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                        try {
                            subcategorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }

                    }else if(val==2) {
                        //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                        try {
                            productlist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);
                            e.printStackTrace();
                        }
                    }else if(val==3) {
                        try {
                            JSONObject jsonObject =  new JSONObject(StoredObjects.Services_list.get(val).Result);
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("200")){
                                StoredObjects.viewpos=0;
                                StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.ofradded),getActivity());
                                fragmentcalling(new ManageOffersMain());
                            }else{
                                String error = jsonObject.getString("error");

                                StoredObjects.ToastMethod(error,getActivity());
                            }
                        }catch (JSONException e){

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
    public  void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }
    private void CategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        category_popup=new PopupWindow(mView, width-50, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        category_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< categorieslist.size(); i++){
            addcategoryfilter(additems_lay,categorieslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        category_popup.setBackgroundDrawable(new BitmapDrawable());
        category_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }

    public void addcategoryfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_id=filter_array.get(position).get("category_id");
                name_txt.setText( filter_array.get(position).get("category_name"));
                adofr_slctsbctgry_edtx.setHint(getActivity().getResources().getString(R.string.sel_subcategory));
                adofr_prdct_edtx.setHint(getActivity().getResources().getString(R.string.seclt_product));
                parsing_methods(1, StoredUrls.GetProductSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);
                category_popup.dismiss();
            }
        });

        layout.addView(v);

    }


    private void SubCategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        subcategory_popup=new PopupWindow(mView, width-50, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        subcategory_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< subcategorieslist.size(); i++){
            addsubcategoryfilter(additems_lay,subcategorieslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        subcategory_popup.setBackgroundDrawable(new BitmapDrawable());
        subcategory_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }

    public void addsubcategoryfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subcategory_id=filter_array.get(position).get("category_id");
                name_txt.setText( filter_array.get(position).get("category_name"));
                adofr_prdct_edtx.setHint(getActivity().getResources().getString(R.string.seclt_product));
                parsing_methods(2, StoredUrls.GetProducts_url,"vendor_id="+StoredObjects.UserId+"&category_id="+category_id+"&sub_category_id="+subcategory_id,Constants.POSTMETHOD);

                subcategory_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    private void ProductFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){

        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        products_popup=new PopupWindow(mView, width-50, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        products_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< productlist.size(); i++){
            addproductfilter(additems_lay,productlist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        products_popup.setBackgroundDrawable(new BitmapDrawable());
        products_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }

    ArrayList<HashMap<String, String>> imageslist=new ArrayList<>();
    ArrayList<HashMap<String, String>> attrbutslist=new ArrayList<>();
    public void addproductfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("product_name"));



        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!filter_array.get(position).get("images").equalsIgnoreCase("[]")){
                    try {
                        imageslist=JsonParsing.GetInnerJsonData(filter_array.get(position).get("images"));

                    }catch (Exception e){

                    }
                }
                if(!filter_array.get(position).get("attributes").equalsIgnoreCase("[]")){
                    try {
                        attrbutslist=JsonParsing.GetInnerJsonData(filter_array.get(position).get("attributes"));

                    }catch (Exception e){

                    }
                }

                if(imageslist.size()>0){
                    Glide.with(getActivity())
                            .load(Uri.parse(StoredUrls.MainUrl+imageslist.get(0).get("image"))) // add your image url
                            // .transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                            .placeholder(R.drawable.imagenotfound)

                            .into(adofr_catgry_img);

                }else{
                    adofr_catgry_img.setBackgroundResource(R.drawable.imagenotfound);
                }


                product_id=filter_array.get(position).get("product_id");
                name_txt.setText( filter_array.get(position).get("product_name"));
                ofr_productlay.setVisibility(View.VISIBLE);

                 components.CustomizeTextview(adofr_brndnme_tx,Constants.XXNormal,R.color.sport_shoos_txt_clr,filter_array.get(position).get("product_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,0,0,0});
                if(attrbutslist.size()>0){
                    components.CustomizeTextview(adofr_prce_tx,Constants.Normal,R.color.blue_color,"Rs."+attrbutslist.get(0).get("price")+"/"+attrbutslist.get(0).get("sub_attr_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,3,0,0});

                }
                 components.CustomizeTextview(adofr_brndprce_tx,Constants.Small,R.color.nrml_txt_clr,filter_array.get(position).get("category_name"),Constants.WrapLeftSemiBold+Constants.Roboto, new int[]{0,3,0,0});
                if(filter_array.get(position).get("stock_status").equalsIgnoreCase("Available")){
                    components.CustomizeTextview(adofr_brndscunt_tx,Constants.Small,R.color.greencolor,filter_array.get(position).get("stock_status"),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,3,0,0});

                }else{
                    components.CustomizeTextview(adofr_brndscunt_tx,Constants.Small,R.color.orange,filter_array.get(position).get("stock_status"),Constants.WrapLeftBold+Constants.Roboto, new int[]{0,3,0,0});

                }
                //components.CustomizeTextview(adofr_ratngprcnt_tx,Constants.Small,R.color.nrml_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.ratng_cunt),Constants.WrapCenterNormal+Constants.Roboto, new int[]{7,-5,0,0});


                products_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    private void DiscountFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        ofrtype_popup=new PopupWindow(mView, width-50, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        ofrtype_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< StoredObjects.offerdiscnttype_array.length; i++){
            addiscountfilter(additems_lay,StoredObjects.offerdiscnttype_array,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        ofrtype_popup.setBackgroundDrawable(new BitmapDrawable());
        ofrtype_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }


    public void addiscountfilter(LinearLayout layout, final String[] filter_array, final int position, final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array[position]);

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discounttype=filter_array[position];
                name_txt.setText( filter_array[position]);

                ofrtype_popup.dismiss();
            }
        });

        layout.addView(v);

    }

}
