package com.o3sa.mobipugapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;
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
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by android_2 on 10/13/2018.
 */

public class OffersList extends Fragment {

    TextView ofrs_filtrs_tx;
    EditText ofrs_srch_tx;
    ImageView ofrs_menu_img,ofrs_filtr_img;
    RecyclerView ofrs_recycleviwe;
    BasicComponents components;
    private PopupWindow mPopupWindow;

    LinearLayout prdct_lst_fltr_lay;
    ArrayList<HashMap<String, String>> offerslist,categorieslist,subcategorieslist;
    String category_id="",subcategory_id="",product_id="",offertype="";

    private PopupWindow ofrtype_popup,category_popup,subcategory_popup;
    public static String start_date="",end_date="";
    RobotTextView noofrsfound_txt;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.offerslist,container,false);
        components=new BasicComponents(getActivity());

        intialization(v);
        assigndata();
        servicecallingmethod();
        return v;
    }

    public void intialization(View v){
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.offer_list),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="v_offerlist";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        prdct_lst_fltr_lay = (LinearLayout)v.findViewById(R.id.prdct_lst_fltr_lay);
        ofrs_menu_img = (ImageView)v.findViewById(R.id.ofrs_menu_img);
        ofrs_filtr_img = (ImageView)v.findViewById(R.id.ofrs_filtr_img);
        ofrs_srch_tx = (EditText) v.findViewById(R.id.ofrs_srch_tx);
        ofrs_filtrs_tx =(TextView)v.findViewById(R.id.ofrs_filtrs_tx);

        ofrs_recycleviwe = (RecyclerView)v.findViewById(R.id.ofrs_recycleviwe);
        noofrsfound_txt=(RobotTextView) v.findViewById(R.id.noofrsfound_txt);


    }

    public void assigndata(){

        components.CustomizeImageview(ofrs_menu_img, new int[]{20,20}, R.drawable.pink_searchicon, new int[]{0,0,0,0});
        components.CustomizeImageview(ofrs_filtr_img, new int[]{20,20}, R.drawable.filter_icon_new, new int[]{0,0,0,0});
        components.CustomizeEditview(ofrs_srch_tx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.search),0,true,Constants.MatchLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(ofrs_filtrs_tx, Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.filtrs),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,0,0,0});
        prdct_lst_fltr_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offersfilterpopup(prdct_lst_fltr_lay,getActivity());
            }
        });
    }



    static EditText adofr_dsplydate_et,adofr_dsplyenddate_et;
    static String startdate = "";
//    static String enddate = "";
EditText subctgry_edtx;

    private void Offersfilterpopup(LinearLayout viewpager_lay, final Activity activity) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(activity);

        View mView = mLayoutInflater.inflate(R.layout.offersfilterpopup, null);

        mPopupWindow = new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setContentView(mView);


        ImageView mange_uparow_img = (ImageView) mView.findViewById(R.id.mange_uparow_img);

       final LinearLayout select_ctgry_lay = (LinearLayout) mView.findViewById(R.id.select_ctgry_lay);
        final LinearLayout select_subctgry_lay = (LinearLayout) mView.findViewById(R.id.select_subctgry_lay);
        LinearLayout start_date_lay = (LinearLayout) mView.findViewById(R.id.start_date_lay);
        LinearLayout end_date_lay = (LinearLayout) mView.findViewById(R.id.end_date_lay);
        final  LinearLayout select_discount_lay = (LinearLayout) mView.findViewById(R.id.select_discount_lay);
        final  LinearLayout select_ofrstatus_lay = (LinearLayout) mView.findViewById(R.id.select_ofrstatus_lay);

         subctgry_edtx = (EditText) mView.findViewById(R.id.subctgry_edtx);
        final  EditText ctgry_edtx = (EditText) mView.findViewById(R.id.ctgry_edtx);
        final EditText discount_edtx = (EditText) mView.findViewById(R.id.discount_edtx);
        final EditText seloffer_edtx = (EditText) mView.findViewById(R.id.seloffer_edtx);
        adofr_dsplydate_et = (EditText) mView.findViewById(R.id.adofr_dsplydate_et);
        adofr_dsplyenddate_et = (EditText) mView.findViewById(R.id.adofr_dsplyenddate_et);

        TextView adofr_strtdate_tx = (TextView) mView.findViewById(R.id.adofr_strtdate_tx);
        TextView adofr_enddate_tx = (TextView) mView.findViewById(R.id.adofr_enddate_tx);
        Button filter_done_btn = (Button) mView.findViewById(R.id.filter_done_btn);
        ImageView adofr_dsplydate_img = (ImageView) mView.findViewById(R.id.adofr_dsplydate_img);
        ImageView adofr_dsplyendate_img = (ImageView) mView.findViewById(R.id.adofr_dsplyendate_img);

        select_discount_lay.setVisibility(View.GONE);
         components.CustomizeTextview(adofr_strtdate_tx, Constants.XSmall, R.color.sport_shoos_txt_clr, getActivity().getApplicationContext().getResources().getString(R.string.start_date), Constants.WrapCenterBold + Constants.Gibson, new int[]{5, 6, 5, 6});
        components.CustomizeTextview(adofr_enddate_tx, Constants.XSmall, R.color.sport_shoos_txt_clr, getActivity().getApplicationContext().getResources().getString(R.string.end_date), Constants.WrapCenterBold + Constants.Gibson, new int[]{5, 6, 5, 6});
        components.CustomizeImageview(adofr_dsplydate_img, new int[]{14, 16}, R.drawable.calendericon, new int[]{0, 0, 8, 0});
        components.CustomizeImageview(adofr_dsplyendate_img, new int[]{14, 16}, R.drawable.calendericon, new int[]{0, 0, 8, 0});

        components.CustomizeButton(filter_done_btn, Constants.XXNormal, R.color.white, getActivity().getApplicationContext().getResources().getString(R.string.srch), R.drawable.list_bottom_bg, Constants.MatchCenterBold + Constants.Gibson, new int[]{Constants.matchParent, 42}, new int[]{0, 10, 0, 5});

        select_ofrstatus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFilterPopup(seloffer_edtx,select_ofrstatus_lay);
            }
        });
        seloffer_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFilterPopup(seloffer_edtx,select_ofrstatus_lay);
            }
        });
        Drawable d = new ColorDrawable(Color.WHITE);

        d.setAlpha(200);

        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.showAsDropDown(viewpager_lay, 0, 0, Gravity.CENTER);

        //getWindow().setBackgroundDrawable(d);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable d = new ColorDrawable(Color.WHITE);
                //getWindow().setBackgroundDrawable(d);
            }
        });


        select_ctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(ctgry_edtx,select_ctgry_lay,activity);
            }
        });
        ctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFilterPopup(ctgry_edtx,select_ctgry_lay,activity);
            }
        });

        select_subctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category_id.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.sel_category),getActivity());
                }else{
                    if(subcategorieslist.size()==0){
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.nosubctgres),getActivity());
                    }else{
                        SubCategoryFilterPopup(subctgry_edtx,select_subctgry_lay);
                    }
                }
            }
        });

        subctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category_id.equalsIgnoreCase("")){
                    StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.sel_category),getActivity());
                }else{
                    if(subcategorieslist.size()==0){
                        StoredObjects.ToastMethod(getActivity().getResources().getString(R.string.nosubctgres),getActivity());
                    }else{
                        SubCategoryFilterPopup(subctgry_edtx,select_subctgry_lay);
                    }
                }
            }
        });


        filter_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parsing_methods(3, StoredUrls.GetOffers_url,"vendor_id="+StoredObjects.UserId+"&category_id="+category_id+"&sub_category_id="+subcategory_id+"&offer_type="+offertype+"&start_date="+start_date+"&end_date="+end_date,Constants.POSTMETHOD);

                mPopupWindow.dismiss();
            }
        });

        start_date_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(adofr_dsplydate_et);
            }
        });

        end_date_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPickerDialog(adofr_dsplyenddate_et);
            }
        });

    }



    public void showPickerDialog(View v) {
        DialogFragment newFragment = null;
        String fragmentTag = null;
        switch (v.getId()) {
            case R.id.adofr_dsplydate_et:
                startdate = "start";
                newFragment = new DatePickerFragment();
                fragmentTag = "my_date_picker";
                break;
            case R.id.start_date_lay:
                startdate = "start";
                newFragment = new DatePickerFragment();
                fragmentTag = "my_date_picker";
                break;

            case R.id.adofr_dsplyenddate_et:
                startdate = "end";
                newFragment = new DatePickerFragment();
                fragmentTag = "my_date_picker";
            case R.id.end_date_lay:
                startdate = "end";
                newFragment = new DatePickerFragment();
                fragmentTag = "my_date_picker";

                break;

        }

        newFragment.show(getFragmentManager(), fragmentTag);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
            datePicker.getDatePicker().setMinDate(cal.getTimeInMillis()-1000);

            return datePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month = month + 1;
            String selecteddate="";
            if(startdate.equalsIgnoreCase("start")){
/*
                if (month == 12) {
                    adofr_dsplydate_et.setText(day + "-" + 1 + "-" + (year + 1));
                } else
                    adofr_dsplydate_et.setText(day + "-" + (month + 1) + "-" + year);*/

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

                adofr_dsplydate_et.setText(selecteddate);
            }else{
               /* if (month == 12) {
                    adofr_dsplyenddate_et.setText(day + "-" + 1 + "-" + (year + 1));
                } else {
                    adofr_dsplyenddate_et.setText(day + "-" + (month + 1) + "-" + year);
                }*/

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
                String startdate=adofr_dsplydate_et.getText().toString();
                String enddate=selecteddate;

                if(StoredObjects.DaysDifference(startdate,enddate)==true) {
                    adofr_dsplyenddate_et.setText(selecteddate);
                }else{
                    StoredObjects.ToastMethod("End date must be after Start date.",getActivity());
                }
            }
        }

    }
    private void servicecallingmethod() {

        parsing_methods(0, StoredUrls.GetOffers_url,"vendor_id="+StoredObjects.UserId,Constants.POSTMETHOD);

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
                            recyclerview.Assigndatatorecyleviewhashmap(ofrs_recycleviwe,offerslist,"offerslist",
                                    Constants.Listview, 0, Constants.ver_orientation,R.layout.offers_listitems);

                            if(offerslist.size()==0){
                                noofrsfound_txt.setVisibility(View.VISIBLE);
                                ofrs_recycleviwe.setVisibility(View.GONE);
                            }else{
                                noofrsfound_txt.setVisibility(View.GONE);
                                ofrs_recycleviwe.setVisibility(View.VISIBLE);
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }
                        parsing_methods(1, StoredUrls.GetProductCategories_url,"vendor_id="+StoredObjects.UserId,Constants.POSTMETHOD);

                    }else if(val==1){
                        try {
                            categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                            StoredObjects.LogMethod("Service_called","Execption"+categorieslist.size());
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();
                    }else if(val==2) {
                        //{"status":200,"message":"success","results_count":3,"results":[{"attr_id":1,"attr_name":"Weight"},{"attr_id":3,"attr_name":"length"},{"attr_id":4,"attr_name":"dfghdfghdfg"}]}
                        try {
                            subcategorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }
                        StoredObjects.Services_list.get(val).countDown.cancel();

                    }else if(val==3) {
                        try {
                            offerslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
                            recyclerview.Assigndatatorecyleviewhashmap(ofrs_recycleviwe,offerslist,"offerslist",
                                    Constants.Listview, 0, Constants.ver_orientation,R.layout.offers_listitems);
                            if(offerslist.size()==0){
                                noofrsfound_txt.setVisibility(View.VISIBLE);
                                ofrs_recycleviwe.setVisibility(View.GONE);
                            }else{
                                noofrsfound_txt.setVisibility(View.GONE);
                                ofrs_recycleviwe.setVisibility(View.VISIBLE);
                            }
                            StoredObjects.Services_list.get(val).countDown.cancel();
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }

                    }



                }

            }

            public void onFinish() {
                StoredObjects.LogMethod("Finished ","Finished");
            }
        };

        StoredObjects.Services_list.get(val).countDown.start();


    }


    private void DiscountFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        ofrtype_popup=new PopupWindow(mView, width-60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        ofrtype_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< StoredObjects.offertype_array.length; i++){
            addiscountfilter(additems_lay,StoredObjects.offertype_array,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        ofrtype_popup.setBackgroundDrawable(new BitmapDrawable());
        ofrtype_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);

    }


    public void addiscountfilter(LinearLayout layout, final String[] filter_array, final int position, final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array[position]);

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offertype=filter_array[position];
                name_txt.setText( filter_array[position]);

                ofrtype_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    private void CategoryFilterPopup(EditText name_txt, LinearLayout adpro_slctctgry_lay, Activity activity){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        category_popup=new PopupWindow(mView, width-60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
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
                subctgry_edtx.setHint(getActivity().getResources().getString(R.string.sel_subcategory));
                parsing_methods(2, StoredUrls.GetProductSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);
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
        subcategory_popup=new PopupWindow(mView, width-60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
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

                subcategory_popup.dismiss();
            }
        });

        layout.addView(v);

    }



}
