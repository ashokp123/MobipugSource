package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
 * Created by android_2 on 10/22/2018.
 */

public class CustomerProductlist extends Fragment {

    TextView cat_detls_filtrs_tx;
    EditText cat_detls_srch_edtx;
    ImageView cat_detls_menu_img,cat_detls_filtr_img,cat_detls_chngelay_img,cat_detls_chngegrid_img;
    RecyclerView cat_detls_recycleviwe,cat_detls_grd_lstvwe;
    LinearLayout cat_detls_chngelay,pro_filterlay;
    BasicComponents components;

    public ArrayList<HashMap<String, String>> cstmr_productslist;

    int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_details,container,false);

        intialization(v);
        servicecallingmethod();
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        StoredObjects.pagetype="productlist";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.products),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        cat_detls_menu_img = (ImageView)v.findViewById(R.id.cat_detls_menu_img);
        cat_detls_filtr_img = (ImageView)v.findViewById(R.id.cat_detls_filtr_img);
        cat_detls_chngelay_img = (ImageView)v.findViewById(R.id.cat_detls_chngelay_img);
        cat_detls_chngegrid_img =(ImageView)v.findViewById(R.id.cat_detls_chngegrid_img);
        cat_detls_srch_edtx = (EditText) v.findViewById(R.id.cat_detls_srch_edtx);
        cat_detls_filtrs_tx =(TextView)v.findViewById(R.id.cat_detls_filtrs_tx);


        pro_filterlay = (LinearLayout)v.findViewById(R.id.pro_filterlay);
        cat_detls_chngelay = (LinearLayout)v.findViewById(R.id.cat_detls_chngelay);
        cat_detls_recycleviwe = (RecyclerView)v.findViewById(R.id.cat_detls_recycleviwe);
        cat_detls_grd_lstvwe = (RecyclerView)v.findViewById(R.id.cat_detls_grd_lstvwe);

        cat_detls_grd_lstvwe.setVisibility(View.GONE);
        cat_detls_recycleviwe.setVisibility(View.VISIBLE);

        StoredObjects.catgry_details_list.clear();
        assigndata();

        pro_filterlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Managefilterpopup(pro_filterlay);
            }
        });

    }

    private void servicecallingmethod() {
        parsing_methods(0, StoredUrls.GetProducts_url,"customer_id="+StoredObjects.UserId+"&category_id=&sub_category_id=&search_text=&min_price=&max_price=&min_offer=&min_offer",Constants.POSTMETHOD);
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
                            cstmr_productslist = JsonParsing.GetJsonData(StoredObjects.Services_list.get(val).Result);

                            CustomRecyclerview recyclerview = new CustomRecyclerview(getActivity());

                            recyclerview.Assigndatatorecyleviewhashmap(cat_detls_recycleviwe,cstmr_productslist,"catgrydetails",
                                    Constants.Listview, 0, Constants.ver_orientation,R.layout.category_details_lst_frmt);

                            recyclerview.Assigndatatorecyleviewhashmap(cat_detls_grd_lstvwe, cstmr_productslist,"catgrygrddetails",
                                    Constants.Gridview,2, Constants.ver_orientation,R.layout.category_details_grid_frmt);
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

    public void assigndata(){

        components.CustomizeImageview(cat_detls_menu_img, new int[]{20,20}, R.drawable.pink_searchicon, new int[]{0,0,0,0});
        components.CustomizeImageview(cat_detls_filtr_img, new int[]{20,20}, R.drawable.filter_icon_new, new int[]{0,0,0,0});
        components.CustomizeImageview(cat_detls_chngelay_img, new int[]{23,25}, R.drawable.listviewicon, new int[]{0,0,0,0});
        components.ApplyTint(cat_detls_chngelay_img,R.color.gray);
        components.CustomizeImageview(cat_detls_chngegrid_img, new int[]{25,25}, R.drawable.gridviewicon, new int[]{0,0,0,0});
        components.ApplyTint(cat_detls_chngegrid_img,R.color.gray);
        components.CustomizeEditview(cat_detls_srch_edtx, Constants.Medium,getActivity().getApplicationContext().getResources().getString(R.string.search),0,true,Constants.MatchLeftNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeTextview(cat_detls_filtrs_tx, Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.filtrs),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,0,0,0});
        cat_detls_chngelay_img.setVisibility(View.VISIBLE);
        cat_detls_chngegrid_img.setVisibility(View.GONE);

        cat_detls_chngelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              count++;
                if (count==1){
                    cat_detls_grd_lstvwe.setVisibility(View.VISIBLE);
                    cat_detls_recycleviwe.setVisibility(View.GONE);
                    cat_detls_chngelay_img.setVisibility(View.GONE);
                    cat_detls_chngegrid_img.setVisibility(View.VISIBLE);
                }else{
                    count=0;
                    cat_detls_grd_lstvwe.setVisibility(View.GONE);
                    cat_detls_recycleviwe.setVisibility(View.VISIBLE);
                    cat_detls_chngelay_img.setVisibility(View.VISIBLE);
                    cat_detls_chngegrid_img.setVisibility(View.GONE);
                }
            }
        });

    }
    HomePageAdapter adapter;

    private void Managefilterpopup(LinearLayout viewpager_lay){

        LayoutInflater mLayoutInflater=LayoutInflater.from(getActivity());

        View mView = mLayoutInflater.inflate(R.layout.prdct_lst_fltr_dialog_new, null);

        LinearLayout filtr_cncl_img_lay,filtr_reset_lay;
        ImageView filtr_cncl_img;
        TextView filtr_reset_txt;
        Button fltr_done_btn;
        ImageView fltr_uparow_img;
        //CustomRecyclerview recyclerview;

        final TabLayout tabLayout;
        final ViewPager viewPager;

        final PopupWindow mPopupWindow;
        mPopupWindow=new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setContentView(mView);

        filtr_cncl_img_lay = (LinearLayout)mView.findViewById(R.id.filtr_cncl_img_lay);
        filtr_reset_lay = (LinearLayout)mView.findViewById(R.id.filtr_reset_lay);
        filtr_cncl_img = (ImageView)mView.findViewById(R.id.filtr_cncl_img);
        filtr_reset_txt = (TextView)mView.findViewById(R.id.filtr_reset_txt);
        fltr_done_btn = (Button)mView.findViewById(R.id.fltr_done_btn);

        components.CustomizeImageview(filtr_cncl_img, new int[]{10,10}, R.drawable.cancel_img, new int[]{15,25,0,10});
        components.CustomizeTextview(filtr_reset_txt, Constants.Small,R.color.dark_grey_clr,getActivity().getApplicationContext().getResources().getString(R.string.rset),Constants.WrapRightBold+Constants.Roboto, new int[]{0,22,15,10});
        components.CustomizeButton(fltr_done_btn, Constants.Medium,R.color.white,getActivity().getApplicationContext().getResources().getString(R.string.done), R.drawable.cntinue_btn_bg, Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,45}, new int[]{10,0,10,10});
        viewPager = (ViewPager) mView.findViewById(R.id.viewpager_filter);
        adapter = new HomePageAdapter(getActivity(), null);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Drawable d = new ColorDrawable(Color.WHITE);

        d.setAlpha(200);

        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        //mPopupWindow.showAsDropDown(viewpager_lay,0,0, Gravity.CENTER);
        mPopupWindow.showAtLocation(viewpager_lay, Gravity.TOP, 0, 0);

        //getWindow().setBackgroundDrawable(d);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable d = new ColorDrawable(Color.WHITE);
                //getWindow().setBackgroundDrawable(d);
            }
        });


        filtr_cncl_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        filtr_reset_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        fltr_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

    }

    public static RecyclerView price_rnge_rvw;
    public static RecyclerView offr_rnge_rvw;

    String[] namess = {"SORT","PRICE","OFFER"};

    public class HomePageAdapter extends PagerAdapter {

        Activity activity;
        ArrayList<DumpData> datalist;

        LayoutInflater inflater;

        /*public LinearLayout sort_lay;
        public LinearLayout sort_ctgry_lay,sort_sub_ctgry_lay,sort_other_ctgry_lay;
        ImageView sort_ctgry_img,sort_sub_ctgry_img,sort_other_img;
        public TextView sort_ctgry_txt,sort_sub_ctgry_txt,sort_other_txt;*/

        public LinearLayout price_lay;
        public TextView price_rnge_txt;
        //public RecyclerView price_rnge_rvw;

        public LinearLayout offr_lay;
        public TextView offr_rnge_txt;
        //public RecyclerView offr_rnge_rvw;

        public CustomRecyclerview recyclerview,recyclerview1;
        String[] price = {"05 - 10","10 - 15","15 - 20","20 - 25","25 - 30","30 - 35","35 - 40","40 - 45"};

        public HomePageAdapter(Activity context, ArrayList<DumpData> dumpDatas) {

            this.activity = context;
            this.datalist = dumpDatas;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return namess[position];
            //return namess;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
            //return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = null;
            if(position == 0){
                v = inflater.inflate(R.layout.fltr_sort_pg, container, false);
                ((ViewPager) container).addView(v);

                LinearLayout sort_lay = (LinearLayout)v.findViewById(R.id.sort_ctgry_lay);

                LinearLayout sort_ctgry_lay = (LinearLayout)v.findViewById(R.id.sort_ctgry_lay);
                LinearLayout sort_sub_ctgry_lay = (LinearLayout)v.findViewById(R.id.sort_sub_ctgry_lay);
                LinearLayout sort_other_ctgry_lay = (LinearLayout)v.findViewById(R.id.sort_other_ctgry_lay);

                ImageView sort_ctgry_img = (ImageView) v.findViewById(R.id.sort_ctgry_img);
                ImageView sort_sub_ctgry_img = (ImageView)v.findViewById(R.id.sort_sub_ctgry_img);
                ImageView sort_other_img = (ImageView)v.findViewById(R.id.sort_other_img);

                TextView sort_ctgry_txt = (TextView) v.findViewById(R.id.sort_ctgry_txt);
                TextView sort_sub_ctgry_txt = (TextView)v.findViewById(R.id.sort_sub_ctgry_txt);
                TextView sort_other_txt = (TextView)v.findViewById(R.id.sort_other_txt);

                components.CustomizeImageview(sort_ctgry_img, new int[]{10,10}, R.drawable.cancel_img, new int[]{15,0,0,0});
                components.CustomizeImageview(sort_sub_ctgry_img, new int[]{10,10}, R.drawable.cancel_img, new int[]{15,0,0,0});
                components.CustomizeImageview(sort_other_img, new int[]{10,10}, R.drawable.cancel_img, new int[]{15,0,0,0});

                components.CustomizeTextview(sort_ctgry_txt, Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.ctgry),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,-2,0,0});
                components.CustomizeTextview(sort_sub_ctgry_txt, Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.sub_ctgry),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,-2,0,0});
                components.CustomizeTextview(sort_other_txt, Constants.Normal,R.color.sport_shoos_txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.othr),Constants.WrapCenterNormal+Constants.Roboto, new int[]{10,-2,0,0});

            }
            if(position == 1){
                v = inflater.inflate(R.layout.fltr_price_pg, container, false);
                ((ViewPager) container).addView(v);

                price_lay = (LinearLayout)v.findViewById(R.id.price_lay);
                price_rnge_txt = (TextView)v.findViewById(R.id.price_rnge_txt);
                price_rnge_rvw = (RecyclerView)v.findViewById(R.id.price_rnge_rvw);

                components.CustomizeTextview(price_rnge_txt, Constants.Normal,R.color.dark_grey_clr,getActivity().getApplicationContext().getResources().getString(R.string.prce_rnge),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,15,0,0});

                StoredObjects.price_rnge_list.clear();
                for (int i = 0;i<price.length;i++){
                    DumpData dumpData = new DumpData();
                    dumpData.price = price[i];
                    dumpData.layout_highlight="No";
                    StoredObjects.price_rnge_list.add(dumpData);
                }


                recyclerview = new CustomRecyclerview(activity);
                recyclerview.Assigndatatorecyleview(price_rnge_rvw, StoredObjects.price_rnge_list,"price_rnge_rvww", Constants.Gridview,4,
                        Constants.ver_orientation,R.layout.fltr_price_rnge_lstitm);



            }
            if(position == 2){
                v = inflater.inflate(R.layout.fltr_offer_pg, container, false);
                ((ViewPager) container).addView(v);

                offr_lay = (LinearLayout)v.findViewById(R.id.offr_lay);
                offr_rnge_txt = (TextView)v.findViewById(R.id.offr_rnge_txt);
                offr_rnge_rvw = (RecyclerView)v.findViewById(R.id.offr_rnge_rvw);

                components.CustomizeTextview(offr_rnge_txt, Constants.Normal,R.color.dark_grey_clr,getActivity().getApplicationContext().getResources().getString(R.string.offr_rnge),Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,15,0,0});

                StoredObjects.price_rnge_list.clear();
                for (int i = 0;i<price.length;i++){
                    DumpData dumpData = new DumpData();
                    dumpData.price = price[i];
                    dumpData.layout_highlight="No";
                    StoredObjects.price_rnge_list.add(dumpData);
                }

                recyclerview1 = new CustomRecyclerview(activity);
                recyclerview1.Assigndatatorecyleview(offr_rnge_rvw, StoredObjects.price_rnge_list,"offr_rnge_rvww", Constants.Gridview,4,
                        Constants.ver_orientation,R.layout.offr_rnge_lstitm);


            }

            return v;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((LinearLayout) object);

            // ((ViewPager) container).removeAllViews();

        }

    }
}
