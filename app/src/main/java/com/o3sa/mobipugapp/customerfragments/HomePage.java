package com.o3sa.mobipugapp.customerfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.CustomProgressbar;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.CustomRecyclerview;
import com.o3sa.mobipugapp.uicomponents.Customviewpager;
import com.o3sa.mobipugapp.uicomponents.ViewpagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android-4 on 10/16/2018.
 */

public class HomePage extends Fragment {

    BasicComponents components;

    RelativeLayout hm_pg_actionbar_lay;
    LinearLayout hm_pg_menu_btn_lay,hm_pg_sarch_edtlay,hm_pg_cart_img_lay;
    ImageView hm_pg_menu_btn_img,hm_pg_cart_img;
    EditText hm_pg_sarch_edtxt;
    RecyclerView hm_pg_catgr_rvw,hm_pg_poplr_keywrds_rvw;
    TextView hm_pg_ctgries_txtt,hm_pg_show_mre_txt,hm_pg_poplr_keywrds_txt;
    public ArrayList<HashMap<String, String>> prdct_details_list;

    ViewPager hmpg_viewpager;
    ViewpagerAdapter viewpagerAdapter;
    LinearLayout viewPagerCountDots;
    CustomRecyclerview recyclerview,recyclerview1;

    Integer [] hmpg_pg_imgs = {R.drawable.shopimg,R.drawable.shopimgtwo,R.drawable.shopimg,R.drawable.shopimgtwo,R.drawable.shopimg};

    String[] hmpg_ctgries_names  = {"Nike shoes","Shoes","Electronics","Computers","Cellphones","Offices","Shoes","Fashions","Collections"};
    Integer[] hmpg_ctgries_imgs = {R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,
                                   R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg,R.drawable.restarunt_sammpleimg};

    String[] hmpg_keywords_names  = {"heniken","Macbook","Flycam","iPhone"};
    Integer[] hmpg_keywords_imgs = {R.drawable.apple_logo,R.drawable.apple_logo,R.drawable.apple_logo,R.drawable.apple_logo};
    String[] hmpg_keywords_dscrptn  = {"155 Products","1,000 Products","1,000 Products","2,000 Products"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homepage,container,false);

       // CustomProgressbar.Progressbarshow(getActivity());
        init(v);
        return v;
    }

    public void init(View b){
        StoredObjects.pagetype="homelist";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,"Home",Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        hm_pg_actionbar_lay = (RelativeLayout)b.findViewById(R.id.hm_pg_actionbar_lay);

        hm_pg_menu_btn_lay = (LinearLayout) b.findViewById(R.id.hm_pg_menu_btn_lay);
        hm_pg_sarch_edtlay = (LinearLayout) b.findViewById(R.id.hm_pg_sarch_edtlay);
        hm_pg_cart_img_lay = (LinearLayout) b.findViewById(R.id.hm_pg_cart_img_lay);

        hm_pg_menu_btn_img = (ImageView) b.findViewById(R.id.hm_pg_menu_btn_img);
        hm_pg_cart_img = (ImageView) b.findViewById(R.id.hm_pg_cart_img);

        hm_pg_sarch_edtxt = (EditText) b.findViewById(R.id.hm_pg_sarch_edtxt);

        hm_pg_catgr_rvw = (RecyclerView) b.findViewById(R.id.hm_pg_catgr_rvw);
        hm_pg_poplr_keywrds_rvw = (RecyclerView) b.findViewById(R.id.hm_pg_poplr_keywrds_rvw);

        hm_pg_ctgries_txtt = (TextView) b.findViewById(R.id.hm_pg_ctgries_txtt);
        hm_pg_show_mre_txt = (TextView) b.findViewById(R.id.hm_pg_show_mre_txt);
        hm_pg_poplr_keywrds_txt = (TextView) b.findViewById(R.id.hm_pg_poplr_keywrds_txt);

        hmpg_viewpager = (ViewPager)b.findViewById(R.id.hmpg_viewpager);
        viewPagerCountDots=(LinearLayout)b.findViewById(R.id.viewPagerCountDots);
        components.SetHeight(hm_pg_catgr_rvw,9,100);
        StoredObjects.hmpg_vwpager_list.clear();
        for (int i = 0;i<hmpg_pg_imgs.length;i++){
            DumpData dumpData = new DumpData();
            dumpData.hmpg_pg_imgs = hmpg_pg_imgs[i];

            StoredObjects.hmpg_vwpager_list.add(dumpData);
        }

        Customviewpager customviewpager=new Customviewpager();
        customviewpager.Customviewpager(getActivity(),hmpg_viewpager,viewpagerAdapter,StoredObjects.hmpg_vwpager_list,prdct_details_list,
                                        viewPagerCountDots,"hmpg_viewpager",R.layout.viewpagerlistitem);





        StoredObjects.hmpg_ctgries_names_list.clear();
        for (int i = 0;i<hmpg_ctgries_names.length;i++){
            DumpData dumpData = new DumpData();
            dumpData.hmpg_ctgries_names = hmpg_ctgries_names[i];
            dumpData.hmpg_ctgries_imgs = hmpg_ctgries_imgs[i];

            StoredObjects.hmpg_ctgries_names_list.add(dumpData);
        }

        recyclerview = new CustomRecyclerview(getActivity());
        recyclerview.Assigndatatorecyleview(hm_pg_catgr_rvw, StoredObjects.hmpg_ctgries_names_list,"hmpg_catgries",
                                             Constants.Gridview, 3, Constants.ver_orientation,R.layout.hmpg_catgries_lstitm);


        StoredObjects.hmpg_poplr_keywrds_list.clear();
        for (int i = 0;i<hmpg_keywords_names.length;i++){
            DumpData dumpData = new DumpData();
            dumpData.hmpg_keywords_names = hmpg_keywords_names[i];
            dumpData.hmpg_keywords_imgs = hmpg_keywords_imgs[i];
            dumpData.hmpg_keywords_dscrptn = hmpg_keywords_dscrptn[i];

            StoredObjects.hmpg_poplr_keywrds_list.add(dumpData);
        }

        recyclerview1 = new CustomRecyclerview(getActivity());
        recyclerview1.Assigndatatorecyleview(hm_pg_poplr_keywrds_rvw, StoredObjects.hmpg_poplr_keywrds_list,"hmpg_keywords",
                                             Constants.Gridview, 2, Constants.ver_orientation,R.layout.hmpg_poplrkeywords_lstitm);

        AssignData();

        hm_pg_show_mre_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentcalling(getActivity(),new CategoryList());
            }
        });

    }
    public void fragmentcalling(Activity activity, Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }
    public void AssignData(){

        components.CustomizeTextview(hm_pg_ctgries_txtt, Constants.XNormal, R.color.ctgries_clr, getActivity().getResources().getString(R.string.catgries), Constants.WrapLeftNormal+Constants.Roboto, new int[]{15,0,0,0});
        components.CustomizeTextview(hm_pg_show_mre_txt, Constants.Medium, R.color.blue_color,  getActivity().getResources().getString(R.string.show), Constants.WrapRightNormal+Constants.Roboto, new int[]{0,0,15,0});
        components.CustomizeTextview(hm_pg_poplr_keywrds_txt, Constants.XNormal, R.color.ctgries_clr,  getActivity().getResources().getString(R.string.poplr_keywords), Constants.MatchLeftNormal+Constants.Roboto, new int[]{15,10,0,10});

        components.CustomizeEditview(hm_pg_sarch_edtxt, Constants.Normal,getActivity().getApplicationContext().getResources().getString(R.string.search),0,true,Constants.MatchCenterNormal+Constants.Roboto, new int[]{0,0,0,0});
        components.CustomizeImageview(hm_pg_menu_btn_img, new int[]{24,24},R.drawable.pink_searchicon, new int[]{0,0,0,0});
        components.CustomizeImageview(hm_pg_cart_img, new int[]{24,24},R.drawable.cart_icon, new int[]{0,0,0,0});

    }


}
