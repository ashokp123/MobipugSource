package com.o3sa.mobipugapp.fragments;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
 * Created by Kiran on 21-10-2018.
 */

public class ManageProducts extends Fragment {

    TextView ofrs_filtrs_tx;
    EditText ofrs_srch_tx;
    ImageView ofrs_menu_img,ofrs_filtr_img;
    public static RecyclerView ofrs_recycleviwe;
    BasicComponents components;
    private PopupWindow mPopupWindow;
    Integer[] ofrs_catgryimgs = {R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg};
    public ArrayList<HashMap<String, String>> productslist;
    LinearLayout prdct_lst_fltr_lay;

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
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.manageproducts),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        StoredObjects.pagetype="v_manageproductslist";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        ofrs_menu_img = (ImageView)v.findViewById(R.id.ofrs_menu_img);
        ofrs_filtr_img = (ImageView)v.findViewById(R.id.ofrs_filtr_img);
        ofrs_srch_tx = (EditText) v.findViewById(R.id.ofrs_srch_tx);
        ofrs_filtrs_tx =(TextView)v.findViewById(R.id.ofrs_filtrs_tx);
        prdct_lst_fltr_lay = (LinearLayout)v.findViewById(R.id.prdct_lst_fltr_lay);
        ofrs_recycleviwe = (RecyclerView)v.findViewById(R.id.ofrs_recycleviwe);


    }

    public void assigndata(){

        components.CustomizeImageview(ofrs_menu_img, new int[]{20,20}, R.drawable.pink_searchicon, new int[]{0,0,0,0});
        components.CustomizeImageview(ofrs_filtr_img, new int[]{20,20}, R.drawable.filter_icon_new, new int[]{0,0,0,0});
        components.CustomizeEditview(ofrs_srch_tx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.search),0,true,Constants.MatchLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(ofrs_filtrs_tx, Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.filtrs),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,0,0,0});

        prdct_lst_fltr_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Productfilterpopup(prdct_lst_fltr_lay);
            }
        });
    }

    private void Productfilterpopup(LinearLayout viewpager_lay){

        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());

        View mView=mLayoutInflater.inflate(R.layout.productfilterpopup, null);

        mPopupWindow=new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setContentView(mView);

        ImageView mange_uparow_img = (ImageView)mView.findViewById(R.id.mange_uparow_img);

        LinearLayout select_ctgry_lay = (LinearLayout)mView.findViewById(R.id.select_ctgry_lay);
        LinearLayout select_subctgry_lay = (LinearLayout)mView.findViewById(R.id.select_subctgry_lay);
        EditText subctgry_edtx = (EditText)mView.findViewById(R.id.subctgry_edtx);
        EditText ctgry_edtx = (EditText)mView.findViewById(R.id.ctgry_edtx);

        Button filter_done_btn = (Button)mView.findViewById(R.id.filter_done_btn);

        components.CustomizeEditview(subctgry_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.sel_subcategory),0,false,Constants.WrapCenterBold+Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeEditview(ctgry_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.seclt_catgry),0,false,Constants.WrapCenterBold+Constants.Gibson, new int[]{0,0,0,0});

        components.CustomizeButton(filter_done_btn, Constants.XXNormal,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.searchproduct),R.drawable.list_bottom_bg,Constants.MatchCenterBold+Constants.Gibson, new int[]{Constants.matchParent,42}, new int[]{0,10,0,5});

        Drawable d = new ColorDrawable(Color.WHITE);

        d.setAlpha(200);

        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.showAsDropDown(viewpager_lay,0,0, Gravity.CENTER);

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

            }
        });

        select_subctgry_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        filter_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });


    }

    private void servicecallingmethod() {
        parsing_methods(0, StoredUrls.GetProducts_url,"vendor_id="+StoredObjects.UserId+"&category_id=&sub_category_id=&search_text=",Constants.POSTMETHOD);

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
                            productslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());
                            recyclerview.Assigndatatorecyleviewhashmap(ofrs_recycleviwe,productslist,"manageproducts",
                                    Constants.Listview, 0, Constants.ver_orientation,R.layout.manageproduct_listitem);

                        } catch (JSONException e) {
                            StoredObjects.LogMethod("Service_called","Execption"+e);

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
}

