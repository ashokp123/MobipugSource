package com.o3sa.mobipugapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

/**
 * Created by Kiran on 23-10-2018.
 */

public class ManageOffersMain extends Fragment {

    LinearLayout ofrmn_ofrs_lay,ofrmn_adofr_lay,ofrmn_mange_lay;
    TextView ofrmn_ofrslist_txt,ofrmn_adofr_txt,ofrmn_mange_txt;
    View ofrmn_ofrs_view,ofrmn_adofr_view,ofrmn_mange_view;
    BasicComponents components;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.offerlists_main,container,false);

        intialization(v);
        return v;
    }

    public void intialization(View v){
        StoredObjects.pagetype="manageoffers";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        components=new BasicComponents(getActivity());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.offers),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        ofrmn_ofrs_lay = (LinearLayout)v.findViewById(R.id.ofrmn_ofrs_lay);
        ofrmn_adofr_lay = (LinearLayout)v.findViewById(R.id.ofrmn_adofr_lay);
        ofrmn_mange_lay = (LinearLayout)v.findViewById(R.id.ofrmn_mange_lay);

        ofrmn_ofrslist_txt = (TextView)v.findViewById(R.id.ofrmn_ofrslist_txt);
        ofrmn_adofr_txt = (TextView)v.findViewById(R.id.ofrmn_adofr_txt);
        ofrmn_mange_txt = (TextView)v.findViewById(R.id.ofrmn_mange_txt);

        ofrmn_ofrs_view = (View)v.findViewById(R.id.ofrmn_ofrs_view);
        ofrmn_adofr_view = (View)v.findViewById(R.id.ofrmn_adofr_view);
        ofrmn_mange_view = (View)v.findViewById(R.id.ofrmn_mange_view);


        if( StoredObjects.viewpos==0) {
            buttonchangemethod(getActivity(),ofrmn_ofrs_lay,ofrmn_ofrslist_txt,ofrmn_ofrs_view,"0");
            fragmentcalling(getActivity(),new OffersList());
        }else if(StoredObjects.viewpos==1){
            buttonchangemethod(getActivity(),ofrmn_adofr_lay,ofrmn_adofr_txt,ofrmn_adofr_view,"1");
            fragmentcalling(getActivity(),new AddOffer());
        }else{
            buttonchangemethod(getActivity(),ofrmn_mange_lay,ofrmn_mange_txt,ofrmn_mange_view,"2");
            fragmentcalling(getActivity(),new ManageOffers());
        }
            components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.offer_list),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
        buttonchangelaymethod(getActivity(),ofrmn_ofrs_lay,ofrmn_ofrslist_txt,ofrmn_ofrs_view,"0");
        buttonchangelaymethod(getActivity(),ofrmn_adofr_lay,ofrmn_adofr_txt,ofrmn_adofr_view,"1");
        buttonchangelaymethod(getActivity(),ofrmn_mange_lay,ofrmn_mange_txt,ofrmn_mange_view,"2");

        assigndata();

    }

    public void buttonchangelaymethod(final Activity activity, final LinearLayout layout1, final TextView text1, final View vw, final String type){

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonchangemethod(activity,layout1,text1,vw,type);
                if (type.equalsIgnoreCase("0")) {
                    StoredObjects.viewpos=0;
                    components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.offer_list),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
                    fragmentcalling(getActivity(),new OffersList());
                }else if (type.equalsIgnoreCase("1")) {
                    StoredObjects.viewpos=1;
                    components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.add_offer),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
                    fragmentcalling(getActivity(),new AddOffer());
                }else if (type.equalsIgnoreCase("2")) {
                    StoredObjects.viewpos=2;
                    components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.manage_offer),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});
                    fragmentcalling(getActivity(),new ManageOffers());
                }
            }
        });

    }

    public void buttonchangemethod(Activity activity,LinearLayout layout1,TextView text1,View vw,String type){

        ofrmn_ofrs_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
        ofrmn_adofr_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
        ofrmn_mange_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));

        ofrmn_ofrslist_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));
        ofrmn_adofr_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));
        ofrmn_mange_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));

        ofrmn_ofrs_view.setBackgroundColor(activity.getResources().getColor(R.color.white));
        ofrmn_adofr_view.setBackgroundColor(activity.getResources().getColor(R.color.white));
        ofrmn_mange_view.setBackgroundColor(activity.getResources().getColor(R.color.white));

        layout1.setBackgroundColor(activity.getResources().getColor(R.color.white));
        text1.setTextColor(activity.getResources().getColor(R.color.txt_clr));
        vw.setBackgroundColor(activity.getResources().getColor(R.color.blue_color));

    }

    public void assigndata(){

        components.CustomizeTextview(ofrmn_ofrslist_txt, Constants.Medium,R.color.txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.ofrs_lst),Constants.WrapCenterBold+Constants.Gibson, new int[]{0,10,0,10});
        components.CustomizeTextview(ofrmn_adofr_txt, Constants.Medium,R.color.thik_grey,getActivity().getApplicationContext().getResources().getString(R.string.ad_ofr),Constants.WrapCenterBold+Constants.Gibson, new int[]{0,10,0,10});
        components.CustomizeTextview(ofrmn_mange_txt, Constants.Medium,R.color.thik_grey,getActivity().getApplicationContext().getResources().getString(R.string.manage),Constants.WrapCenterBold+Constants.Gibson, new int[]{0,10,0,10});

    }

    public void fragmentcalling(Activity activity,Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.inner_frame_lay, fragment).commit();

    }

}

