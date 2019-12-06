package com.o3sa.mobipugapp.fragments;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.servicesparsing.WebServicesCalling;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 03-11-2018.
 */

public class EditProduct extends Fragment {

    EditText addprdctname_edtx,addprdctdescr_edtx,prdctctgry_edtx,prdctsubctgry_edtx;//adprdctprice_edtx
    TextView addprdctdescr_tx,adprdctimage_tx,adprdctimgpick_tx,camera_tx,gallery_tx,stock_tx;//adprdctdetls_tx
    RadioButton avalblepro_rbtn,notavalblepro_rbtn;
    Button addproduct_btn;
    //ImageView addmsmnt_btn;
    private PopupWindow attributes_popup,subatrbts_popup,category_popup,subcategory_popup;

    BasicComponents components;
    LinearLayout main_msrmnt_lay,addpikedimgs_lay;//addmeasurmntlay
    public ArrayList<HashMap<String, String>> attributeslist,subattributeslist,categorieslist,subcategorieslist;

    LinearLayout adpro_slctctgry_lay,adpro_slctsubctgry_lay;
    String parent_id="",category_id="",subcategory_id="",images_array="";
    String product_status="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addproduct,container,false);

        intialization(v);
        assigndata();
        servicecalling();

        return v;
    }



    public void intialization(View v){
        StoredObjects.pagetype="editproduct";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.edit_product),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});


        addprdctdescr_tx =(TextView) v.findViewById(R.id.addprdctdescr_tx);
        adprdctimage_tx= (TextView)v.findViewById(R.id.adprdctimage_tx);
        adprdctimgpick_tx =(TextView)v.findViewById(R.id.adprdctimgpick_tx);
        camera_tx =(TextView)v.findViewById(R.id.camera_tx);
        gallery_tx =(TextView)v.findViewById(R.id.gallery_tx);
        stock_tx =(TextView)v.findViewById(R.id.stock_tx);
        avalblepro_rbtn = (RadioButton)v.findViewById(R.id.avalblepro_rbtn);
        notavalblepro_rbtn = (RadioButton)v.findViewById(R.id.notavalblepro_rbtn);
        addproduct_btn = (Button)v.findViewById(R.id.addproduct_btn);
        addprdctname_edtx =(EditText)v.findViewById(R.id.addprdctname_edtx);
        addprdctdescr_edtx =(EditText)v.findViewById(R.id.addprdctdescr_edtx);
        prdctctgry_edtx =(EditText)v.findViewById(R.id.prdctctgry_edtx);
        prdctsubctgry_edtx=(EditText)v.findViewById(R.id.prdctsubctgry_edtx);
        main_msrmnt_lay=(LinearLayout) v.findViewById(R.id.main_msrmnt_lay);

        addpikedimgs_lay=(LinearLayout) v.findViewById(R.id.addpikedimgs_lay);
        adpro_slctsubctgry_lay=(LinearLayout) v.findViewById(R.id.adpro_slctsubctgry_lay);
        adpro_slctctgry_lay=(LinearLayout) v.findViewById(R.id.adpro_slctctgry_lay);


        avalblepro_rbtn.setChecked(true);
        notavalblepro_rbtn.setChecked(false);

        product_status="Available";
        avalblepro_rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(avalblepro_rbtn.isChecked())
                {
                    product_status="Available";
                    avalblepro_rbtn.setChecked(true);
                    notavalblepro_rbtn.setChecked(false);
                }
            }
        });

        notavalblepro_rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notavalblepro_rbtn.isChecked())
                {
                    product_status="Out of Stock";
                    notavalblepro_rbtn.setChecked(true);
                    avalblepro_rbtn.setChecked(false);
                }
            }
        });
        prdctctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CategoryFilterPopup(prdctctgry_edtx,adpro_slctctgry_lay);
            }
        });

        prdctsubctgry_edtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  SubCategoryFilterPopup(prdctsubctgry_edtx,adpro_slctsubctgry_lay);
            }
        });

        adpro_slctctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CategoryFilterPopup(prdctsubctgry_edtx,adpro_slctctgry_lay);
            }
        });
        adpro_slctsubctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  SubCategoryFilterPopup(prdctsubctgry_edtx,adpro_slctsubctgry_lay);
            }
        });

    }

    public void assigndata(){

        // components.CustomizeEditview(prdctctgry_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.sel_category),0,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,0});
        //components.CustomizeEditview(prdctsubctgry_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.sel_subcategory),0,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,0,0,0});

        components.CustomizeTextview(addprdctdescr_tx, Constants.Medium,R.color.thik_grey,getActivity().getApplicationContext().getResources().getString(R.string.product_descr),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,0,10});
        components.CustomizeTextview(adprdctimage_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.product_imgs),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,0,10});
        components.CustomizeTextview(adprdctimgpick_tx,Constants.Small,R.color.black,getActivity().getApplicationContext().getResources().getString(R.string.product_pick),Constants.WrapLeftNormal+Constants.Gibson, new int[]{15,0,0,0});
        components.CustomizeTextview(stock_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.stock),Constants.WrapLeftNormal+Constants.Gibson, new int[]{10,10,10,0});
        components.CustomizeButton(addproduct_btn, Constants.XNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.save),R.drawable.list_bottom_bg,Constants.MatchCenterNormal+Constants.Gibson, new int[]{0,50}, new int[]{0,20,0,10});
        components.CustomizeEditview(addprdctname_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.product_name),R.drawable.shadoweffect,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,10,0,10});
        components.CustomizeMultilineEditview(addprdctdescr_edtx,Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.product_descrempty),R.drawable.shadoweffect,true,false,Constants.MatchLeftNormal+Constants.Gibson, new int[]{0,10,0,10},4);

        main_msrmnt_lay.removeAllViews();
        for (int i = 0; i <1; i++) {
            addlayout(main_msrmnt_lay,"Yes");
        }
        addpikedimgs_lay.removeAllViews();
        for (int i = 0; i <6; i++) {
            addimageslayout(addpikedimgs_lay);
        }
    }

    public void addsublayout(final LinearLayout layout,String type){
        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.addmeasrmnt_item, null);
        final EditText mesrmntname_edt=(EditText) v.findViewById(R.id.mesrmntname_edt);
        EditText mesrmntval_edt=(EditText) v.findViewById(R.id.mesrmntval_edt);
        final EditText mesrmntunit_edt=(EditText) v.findViewById(R.id.mesrmntunit_edt);
        ImageView addmoremsmnt_btn=(ImageView) v.findViewById(R.id.addmoremsmnt_btn);
        ImageView removemoremsmnt_btn=(ImageView) v.findViewById(R.id.removemoremsmnt_btn);

        if(type.equalsIgnoreCase("Yes")){
            removemoremsmnt_btn.setVisibility(View.GONE);
            addmoremsmnt_btn.setVisibility(View.VISIBLE);
        }else{
            addmoremsmnt_btn.setVisibility(View.GONE);
            removemoremsmnt_btn.setVisibility(View.VISIBLE);
        }

        removemoremsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeViewAt(layout.getChildCount()-1);
            }
        });
        mesrmntname_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FilterPopup(mesrmntname_edt);
            }
        });
        mesrmntunit_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SubFilterPopup(mesrmntunit_edt);
            }
        });


        addmoremsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i <1; i++) {
                    addsublayout(layout,"No");
                }
            }
        });
        layout.addView(v);
    }
    public void addlayout(final LinearLayout layout, String type){
        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.main_msrmnt_listitem, null);
        EditText adprdctprice_edtx=(EditText) v.findViewById(R.id.adprdctprice_edtx);
        TextView adprdctdetls_tx=(TextView) v.findViewById(R.id.adprdctdetls_tx);
        ImageView addmsmnt_btn=(ImageView) v.findViewById(R.id.addmsmnt_btn);
        ImageView removemsmnt_btn=(ImageView) v.findViewById(R.id.removemsmnt_btn);
        final LinearLayout addmeasurmntlay=(LinearLayout) v.findViewById(R.id.addmeasurmntlay);
        components.CustomizeEditview(adprdctprice_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.amount),R.drawable.shadoweffect,true,Constants.MatchLeftNormal+Constants.Gibson, new int[]{10,10,10,10});
        components.CustomizeTextview(adprdctdetls_tx,Constants.Normal,R.color.blue_color,getActivity().getApplicationContext().getResources().getString(R.string.product_details),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        adprdctprice_edtx.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        if(type.equalsIgnoreCase("Yes")){
            removemsmnt_btn.setVisibility(View.GONE);
            addmsmnt_btn.setVisibility(View.VISIBLE);
        }else{
            addmsmnt_btn.setVisibility(View.GONE);
            removemsmnt_btn.setVisibility(View.VISIBLE);
        }
        removemsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeViewAt(layout.getChildCount()-1);
            }
        });
        addmeasurmntlay.removeAllViews();
        for (int i = 0; i <1; i++) {
            addsublayout(addmeasurmntlay,"Yes");
        }
        addmsmnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <1; i++) {
                    addlayout(main_msrmnt_lay,"No");
                }
            }
        });
        layout.addView(v);
    }
    public void addimageslayout(LinearLayout layout){
        View v	=LayoutInflater.from(getActivity()).inflate(R.layout.addimages_item, null);
        ImageView addpicked_img=(ImageView) v.findViewById(R.id.addpicked_img);
        components.CustomizeImageview(addpicked_img, new int[]{100,100}, R.drawable.restarunt_sammpleimg, new int[]{0,0,0,0});

        layout.addView(v);
    }
    private void servicecalling() {
        // parsing_methods(0, StoredUrls.GetAttributes_url,"",Constants.POSTMETHOD);



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
                            attributeslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);
                            parsing_methods(1, StoredUrls.GetProductCategories_url,"",Constants.POSTMETHOD);
                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

                            e.printStackTrace();
                        }

                    }else if(val==2){
                        //{"status":200,"message":"success","results_count":2,"results":[{"attr_id":2,"attr_name":"Grams"},{"attr_id":5,"attr_name":"kghkhj"}]}
                        try {
                            subattributeslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);


                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);
                            e.printStackTrace();
                        }
                    }else if(val==1){
                        // {"status":200,"message":"success","results_count":2,"results":[{"category_id":1,"category_name":"IT"},{"category_id":2,"category_name":"another"}]}
                        try {
                            categorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(val==3){
                        // {"status":200,"message":"success","results_count":1,"results":[{"category_id":3,"category_name":"IT"}]}
                        try {
                            subcategorieslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                        } catch (JSONException e) {
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

    private void CategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        category_popup=new PopupWindow(mView, width-90, LinearLayout.LayoutParams.WRAP_CONTENT, true);
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
                parsing_methods(3, StoredUrls.GetProductSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);

                category_popup.dismiss();
            }
        });

        layout.addView(v);

    }
    private void FilterPopup(EditText name_txt){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.attributespopup, null);
        attributes_popup=new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        attributes_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< attributeslist.size(); i++){
            addlayoutfilter(additems_lay,attributeslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        attributes_popup.setBackgroundDrawable(new BitmapDrawable());
        attributes_popup.showAsDropDown(name_txt, 0, 0, Gravity.CENTER);


    }


    public void addlayoutfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("attr_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent_id=filter_array.get(position).get("attr_id");
                name_txt.setText( filter_array.get(position).get("attr_name"));
                parsing_methods(2, StoredUrls.GetSubAttributes_url,"parent_attr_id="+parent_id,Constants.POSTMETHOD);

                attributes_popup.dismiss();
            }
        });

        layout.addView(v);

    }
    private void SubFilterPopup(EditText name_txt){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.attributespopup, null);
        subatrbts_popup=new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        subatrbts_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< subattributeslist.size(); i++){
            addlayoutsubfilter(additems_lay,subattributeslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        subatrbts_popup.setBackgroundDrawable(new BitmapDrawable());
        subatrbts_popup.showAsDropDown(name_txt, 0, 0, Gravity.CENTER);


    }

    public void addlayoutsubfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout  filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView   filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("attr_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parent_id=filter_array.get(position).get("attr_id");
                name_txt.setText( filter_array.get(position).get("attr_name"));

                subatrbts_popup.dismiss();
            }
        });

        layout.addView(v);

    }

    private void SubCategoryFilterPopup(EditText name_txt,LinearLayout adpro_slctctgry_lay){
        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());
        View mView=mLayoutInflater.inflate(R.layout.categoryfilterpopup, null);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        subatrbts_popup=new PopupWindow(mView, width-90, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        subatrbts_popup.setContentView(mView);
        LinearLayout additems_lay=(LinearLayout) mView.findViewById(R.id.additems_lay);
        additems_lay.removeAllViews();
        for (int i = 0; i< subcategorieslist.size(); i++){
            addsubcategoryfilter(additems_lay,subcategorieslist,i,name_txt);
        }
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(130);
        subatrbts_popup.setBackgroundDrawable(new BitmapDrawable());
        subatrbts_popup.showAsDropDown(adpro_slctctgry_lay, 0, 0, Gravity.CENTER);


    }


    public void addsubcategoryfilter(LinearLayout layout, final ArrayList<HashMap<String, String>> filter_array, final int position,final EditText name_txt){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.attributespopup_listitem, null);
        LinearLayout   filrt_popup_lay = (LinearLayout)v.findViewById(R.id.filrt_popup_lay);
        RobotTextView  filter_popup_txt = (RobotTextView) v.findViewById(R.id.filter_popup_txt);
        filter_popup_txt.setText( filter_array.get(position).get("category_name"));

        filrt_popup_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_id=filter_array.get(position).get("category_id");
                name_txt.setText( filter_array.get(position).get("category_name"));
                parsing_methods(3, StoredUrls.GetVendorSubCategories_url,"parent_category_id="+category_id,Constants.POSTMETHOD);

                subatrbts_popup.dismiss();
            }
        });

        layout.addView(v);

    }
}


