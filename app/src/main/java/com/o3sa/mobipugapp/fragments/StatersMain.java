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
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

/**
 * Created by android-4 on 10/17/2018.
 */

public class StatersMain extends Fragment {

    BasicComponents components;

    TextView strtrs_titl_txt;
    LinearLayout strtrs_mng_lay,strtrs_add_lay,strtrs_req_lay;
    TextView strtrs_mng_txt,strtrs_add_txt,strtrs_req_txt;
    View strtrs_mng_vw,strtrs_add_vw,strtrs_req_vw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.starters_main,container,false);

        components = new BasicComponents(getActivity());

        init(v);

        return v;
    }

    public void init(View b){

        strtrs_titl_txt = b.findViewById(R.id.strtrs_titl_txt);

        strtrs_mng_lay = b.findViewById(R.id.strtrs_mng_lay);
        strtrs_add_lay = b.findViewById(R.id.strtrs_add_lay);
        strtrs_req_lay = b.findViewById(R.id.strtrs_req_lay);

        strtrs_mng_txt = b.findViewById(R.id.strtrs_mng_txt);
        strtrs_add_txt = b.findViewById(R.id.strtrs_add_txt);
        strtrs_req_txt = b.findViewById(R.id.strtrs_req_txt);

        strtrs_mng_vw = b.findViewById(R.id.strtrs_mng_vw);
        strtrs_add_vw = b.findViewById(R.id.strtrs_add_vw);
        strtrs_req_vw = b.findViewById(R.id.strtrs_req_vw);

        fragmentcalling(getActivity(),new OffersList());

        buttonchangelaymethod(getActivity(),strtrs_mng_lay,strtrs_mng_txt,strtrs_mng_vw,"0");
        buttonchangelaymethod(getActivity(),strtrs_add_lay,strtrs_add_txt,strtrs_add_vw,"1");
        buttonchangelaymethod(getActivity(),strtrs_req_lay,strtrs_req_txt,strtrs_req_vw,"2");

        assigndata();

    }

    public void buttonchangelaymethod(final Activity activity, final LinearLayout layout1, final TextView text1, final View vw, final String type){

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonchangemethod(activity,layout1,text1,vw,type);

                if (type.equalsIgnoreCase("0")) {
                    fragmentcalling(getActivity(),new OffersList());
                }else if (type.equalsIgnoreCase("1")) {
                    fragmentcalling(getActivity(),new AddOffer());
                }else if (type.equalsIgnoreCase("2")) {
                    fragmentcalling(getActivity(),new OffersList());
                }

            }
        });

    }

    public void buttonchangemethod(Activity activity,LinearLayout layout1,TextView text1,View vw,String type){

        strtrs_mng_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
        strtrs_add_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));
        strtrs_req_lay.setBackgroundColor(activity.getResources().getColor(R.color.white));

        strtrs_mng_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));
        strtrs_add_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));
        strtrs_req_txt.setTextColor(activity.getResources().getColor(R.color.thik_grey));

        strtrs_mng_vw.setBackgroundColor(activity.getResources().getColor(R.color.white));
        strtrs_add_vw.setBackgroundColor(activity.getResources().getColor(R.color.white));
        strtrs_req_vw.setBackgroundColor(activity.getResources().getColor(R.color.white));

        layout1.setBackgroundColor(activity.getResources().getColor(R.color.white));
        text1.setTextColor(activity.getResources().getColor(R.color.txt_clr));
        vw.setBackgroundColor(activity.getResources().getColor(R.color.blue_color));

    }

    public void assigndata(){

        components.CustomizeTextview(strtrs_titl_txt, Constants.XXNormal,R.color.txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.srtrs),Constants.WrapLeftBold+Constants.Roboto, new int[]{15,18,0,18});
        components.CustomizeTextview(strtrs_mng_txt, Constants.Normal,R.color.txt_clr,getActivity().getApplicationContext().getResources().getString(R.string.mng),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,10,0,10});
        components.CustomizeTextview(strtrs_add_txt, Constants.Normal,R.color.thik_grey,getActivity().getApplicationContext().getResources().getString(R.string.add),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,10,0,10});
        components.CustomizeTextview(strtrs_req_txt, Constants.Normal,R.color.thik_grey,getActivity().getApplicationContext().getResources().getString(R.string.req),Constants.WrapCenterNormal+Constants.Roboto, new int[]{0,10,0,10});

    }

    public void fragmentcalling(Activity activity,Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.inner_frame_lay, fragment).commit();

    }


}
