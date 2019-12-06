package com.o3sa.mobipugapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

/**
 * Created by android_2 on 10/11/2018.
 */

public class LoginPage extends AppCompatActivity {

    TextView lgn_sgnin_txt,lgn_sgnup_txt;
    LinearLayout lgn_sgnin_lay,lgn_sgnup_lay;
    BasicComponents components;

    View lgn_sgnin_view,lgn_sgnup_view;
    public static LinearLayout header_lay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        components=new BasicComponents(LoginPage.this);

        intialization();
    }

    public void intialization() {
        header_lay=(LinearLayout) findViewById(R.id.header_lay);
        lgn_sgnin_txt = (TextView) findViewById(R.id.lgn_sgnin_txt);
        lgn_sgnup_txt=  (TextView)findViewById(R.id.lgn_sgnup_txt);
        lgn_sgnin_lay = (LinearLayout)findViewById(R.id.lgn_sgnin_lay);
        lgn_sgnup_lay = (LinearLayout)findViewById(R.id.lgn_sgnup_lay);

        lgn_sgnin_view = (View)findViewById(R.id.lgn_sgnin_view);
        lgn_sgnup_view = (View)findViewById(R.id.lgn_sgnup_view);

        fragmentcalling(LoginPage.this,new SignInn());

        buttonchangelaymethod(LoginPage.this, lgn_sgnin_lay,lgn_sgnin_txt,lgn_sgnin_view, "0");
        buttonchangelaymethod(LoginPage.this, lgn_sgnup_lay,lgn_sgnup_txt,lgn_sgnup_view, "1");

        assigndata();

    }

    public void assigndata() {

        components.CustomizeTextview(lgn_sgnin_txt,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.signin),Constants.WrapCenterBold+Constants.Gibson, new int[]{0,10,0,10});
        components.CustomizeTextview(lgn_sgnup_txt,Constants.Normal,R.color.thik_grey,getApplicationContext().getResources().getString(R.string.signup),Constants.WrapCenterBold+Constants.Gibson, new int[]{0,10,0,10});

    }


    public void buttonchangelaymethod(final Activity activity, final LinearLayout layout1, final TextView text1, final View vw, final String type) {

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonchangemethod(activity,layout1,text1,vw,type);

                if (type.equalsIgnoreCase("0")) {
                    fragmentcalling(LoginPage.this,new SignInn());
                } else if (type.equalsIgnoreCase("1")) {
                    fragmentcalling(LoginPage.this,new SignUpp());
                }

            }

        });

    }

    public void buttonchangemethod(Activity activity, LinearLayout layout1, TextView text1,View vw, String type) {

        lgn_sgnin_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
        lgn_sgnup_view.setBackgroundColor(activity.getResources().getColor(R.color.white));

        lgn_sgnin_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));
        lgn_sgnup_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));

        lgn_sgnin_view.setBackgroundColor(activity.getResources().getColor(R.color.white));
        lgn_sgnup_view.setBackgroundColor(activity.getResources().getColor(R.color.white));

        //image.setColorFilter(Sidemenu.this.getResources().getColor(R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
        layout1.setBackgroundColor(activity.getResources().getColor(R.color.white));
        text1.setTextColor(activity.getResources().getColor(R.color.txt_clr));

        vw.setBackgroundColor(activity.getResources().getColor(R.color.blue_color));

    }

    public void fragmentcalling(Activity activity,Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_fram_lay, fragment).commit();

    }

}
