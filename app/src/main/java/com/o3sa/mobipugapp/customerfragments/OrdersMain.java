package com.o3sa.mobipugapp.customerfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

/**
 * Created by Kiran on 24-10-2018.
 */

public class OrdersMain extends Fragment {

    BasicComponents components;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.createorder_main,container,false);

        intialization(v);
        return v;
    }

    public void intialization(View v){
        components=new BasicComponents(getActivity());
        fragmentcalling(new Mycart());
        components.CustomizeTextview(Sidemenu.menu_title, Constants.XXLarge4,R.color.black,getActivity().getResources().getString(R.string.mycart),Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});


    }



    public void fragmentcalling(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.createodr_innerframe_lay, fragment).commit();

    }

}



