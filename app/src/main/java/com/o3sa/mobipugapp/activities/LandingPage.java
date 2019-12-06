package com.o3sa.mobipugapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;
import com.o3sa.mobipugapp.uicomponents.Customviewpager;
import com.o3sa.mobipugapp.uicomponents.ViewpagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android_2 on 10/11/2018.
 */

public class LandingPage extends AppCompatActivity {

    Button lndg_ctnue_btn,lndg_fb_btn;
    TextView lndg_more_bnfits_txt,lndg_hve_acunt_tx,lndg_trmcndtn_tx;

    public ArrayList<HashMap<String, String>> prdct_details_list;

    ViewPager landg_viewpager;
    ViewpagerAdapter viewpagerAdapter;
    LinearLayout viewPagerCountDots;
    StoredObjects objects;

    Integer [] lndng_pg_imgs = {R.drawable.apple_logo,R.drawable.apple_logo,R.drawable.apple_logo};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingpage);
        objects=new StoredObjects(LandingPage.this);

        intialization();

    }
    private void intialization() {
        StoredObjects.intilization();
        lndg_ctnue_btn =  (Button) findViewById(R.id.lndg_ctnue_btn);
        lndg_fb_btn =  (Button)  findViewById(R.id.lndg_fb_btn);
        lndg_more_bnfits_txt = (TextView) findViewById(R.id.lndg_more_bnfits_txt);
        lndg_hve_acunt_tx = (TextView) findViewById(R.id.lndg_hve_acunt_tx);
        lndg_trmcndtn_tx=  (TextView)findViewById(R.id.lndg_trmcndtn_tx);

        landg_viewpager = (ViewPager)findViewById(R.id.landg_viewpager);
        viewPagerCountDots=(LinearLayout) findViewById(R.id.viewPagerCountDots);

        StoredObjects.lndng_pg_list.clear();
        for (int i = 0;i<lndng_pg_imgs.length;i++){
            DumpData dumpData = new DumpData();
            dumpData.lndng_pg_imgs = lndng_pg_imgs[i];

            StoredObjects.lndng_pg_list.add(dumpData);
        }

        Customviewpager customviewpager=new Customviewpager();
        customviewpager.Customviewpager(LandingPage.this,landg_viewpager,viewpagerAdapter,StoredObjects.lndng_pg_list,prdct_details_list,viewPagerCountDots,"viewpager_img",R.layout.viewpagerlistitem);

        assigndata();

    }

    private void assigndata() {

        BasicComponents components = new BasicComponents(LandingPage.this);

        components.CustomizeTextview(lndg_more_bnfits_txt,Constants.XNormal,R.color.white,getApplicationContext().getResources().getString(R.string.more_bnfits),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,0,0,10});
       // components.CustomizeButton(lndg_ctnue_btn, Constants.XNormal,R.color.white,getApplicationContext().getResources().getString(R.string.cntinue),R.drawable.cntinue_btn_bg,Constants.MatchCenterBold+Constants.Gibson, new int[]{0,42}, new int[]{0,0,0,10});
       // components.CustomizeButton(lndg_fb_btn,Constants.XNormal,R.color.background_color,getApplicationContext().getResources().getString(R.string.cnct_fb),R.drawable.fb_btn_bg,Constants.MatchCenterBold+Constants.Gibson, new int[]{0,42}, new int[]{0,10,0,0});
        components.CustomizeButton(lndg_ctnue_btn, Constants.XNormal,R.color.white,"Customer",R.drawable.cntinue_btn_bg,Constants.MatchCenterBold+Constants.Gibson, new int[]{0,42}, new int[]{0,0,0,10});
        components.CustomizeButton(lndg_fb_btn,Constants.XNormal,R.color.white,"Vendor",R.drawable.cntinue_btn_bg,Constants.MatchCenterBold+Constants.Gibson, new int[]{0,42}, new int[]{0,10,0,0});

        components.CustomizeTextview(lndg_hve_acunt_tx,Constants.Medium,R.color.background_color,getApplicationContext().getResources().getString(R.string.hve_acunt_rgstr),Constants.WrapCenterBold+Constants.Roboto, new int[]{0,7,0,0});
        components.CustomizeTextview(lndg_trmcndtn_tx,Constants.Medium,R.color.background_color,getApplicationContext().getResources().getString(R.string.trms_cndtns),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,30,0,30});

        lndg_trmcndtn_tx.setGravity(Gravity.CENTER);

        lndg_ctnue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoredObjects.UserType="Customer";
                LandingPage.this.finish();
                startActivity(new Intent(LandingPage.this,Sidemenu.class));
            }
        });

        lndg_hve_acunt_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this,LoginPage.class));
            }
        });

        lndg_fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandingPage.this.finish();
                StoredObjects.UserType="Vendor";
                startActivity(new Intent(LandingPage.this,Sidemenu.class));
            }
        });

    }


}
